import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import java.util.Random;

public class ShadowCPATCHA {

	/**
	* @PARAM: filePathIn: the [file] path of input image, called Origin Image
	* @PARAM: filePathOutShadow: the [directory] path of storing the shadow Images
	* @PARAM: filePathOutPiece: the [directory] path of storing the piece images
	*/
	public void shadowGenerator(String filePathIn,
													String filePathOutShadow,
													String filePathOutPiece) throws IOException{
		// If the input is null, set to default
		if(filePathIn == null || filePathIn.length() == 0)
			filePathIn = Config.picPath;
		if(filePathOutShadow == null || filePathOutShadow.length() == 0)
			filePathOutShadow = Config.piecePath;
		if(filePathOutPiece == null || filePathOutPiece.length() == 0)
			filePathOutPiece = Config.shadowPath;

		// if the last letter is not /, append a /
		filePathOutShadow += (filePathOutShadow.charAt(filePathOutShadow.length()-1) == '/') ? "" : "/";
		filePathOutPiece += (filePathOutPiece.charAt(filePathOutPiece.length()-1) == '/') ? "" : "/";

		// Image Name without suffix
		String imgName = Config.getImageName(filePathIn);
		// Origin Image
		File baseFile = new File(filePathIn);
		// Shadow Image
		File shadowFile = new File(filePathOutShadow + imgName + "-shadow." + Config.picType);
		// Piece
	  File pieceFile = new File(filePathOutPiece + imgName + "." + Config.picPieceType);
		// Rotated Piece
	  File pieceRotatedFile = new File(filePathOutPiece + imgName + "-rotated." + Config.picPieceType);

		// Read the Image file
	  BufferedImage baseImg = ImageIO.read(baseFile);

		// Generate all the shapes
		// Generate 3 Shapes: Origin/ Rotated/ False
		double ratio = 1;
		Random r=new Random();
		double sh_x=r.nextDouble()*(baseImg.getWidth()-200*ratio)+50*ratio;
		double sh_y = r.nextDouble()*(baseImg.getHeight()-200*ratio)+50*ratio;
		int spin = r.nextInt(2);

		Shape[] shapes = DShape.drawShapes(sh_x, sh_y, baseImg.getWidth(), baseImg.getHeight(), spin, ratio);
		Shape shape = shapes[0];
		Shape shapeRotated = shapes[1];
		Shape shapeFalse = shapes[2];

		// Cut the Image, set the image outside the shape to be clear
		int ru = AlphaComposite.SRC;
		float alp = 1.0f;
		generatePiece(baseImg, ru, alp, shape, Config.picPieceType, pieceFile);
		generatePiece(baseImg, ru, alp, shapeRotated, Config.picPieceType, pieceRotatedFile);

		// Get the path of the piece img
		// Read the piece and cut it to rectangle and then save.
		int R_x=(int)(sh_x-15*ratio);
		int R_y=(int)(sh_y-15*ratio);
		Rectangle rect = new Rectangle(R_x, R_y, (int)(120*ratio), (int)(120*ratio));
		BufferedImage bi = modifyPieceSize(rect, Config.picPieceType, pieceFile);
		BufferedImage bif= modifyPieceSize(rect, Config.picPieceType, pieceRotatedFile);

		// Read the img, fill the shape
		int rule = AlphaComposite.SRC_OVER;
		float alpha = 0.7f;
		Graphics2D g = baseImg.createGraphics();
		g.setComposite(AlphaComposite.getInstance(rule, alpha));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		// TODO: need some explanation
		int res = bi.getRGB((int)(70 * ratio), (int)(70 * ratio)) & 0xFFFFFF;
		int[] rgb = new int [3];
    rgb[0] = (res & 0xff0000) >> 16;
    rgb[1] = (res & 0xff00) >> 8;
    rgb[2] = (res & 0xff);

    if(rgb[0] < 0) 			  rgb[0] = 0;
    else if(rgb[0] > 255) rgb[0] = 255;

		if(rgb[1] < 0) 			  rgb[1] = 0;
		else if(rgb[1] > 255) rgb[1] = 255;

		if(rgb[2] < 0) 			  rgb[2] = 0;
    else if(rgb[2] > 255) rgb[2] = 255;

		// Create the Color object
    Color color = new Color(rgb[0],rgb[1],rgb[2]);
		g.setPaint(color);
		g.fill(shape);
		g.fill(shapeFalse);
		g.dispose();
		ImageIO.write(baseImg, Config.picType, shadowFile);
		System.out.println("Image [ " + filePathIn + " ]: All the work has been done.");
	}

	/**
	* @PARAM: img: Origin Image
	* @PARAM: ru int
	* @PARAM: alp int
	* @PARAM: imgSuffix String
	* @PARAM: imgSaveFile File!
	*/
	public static void generatePiece(BufferedImage img,
														int ru, float alp, Shape shape,
														String imgSuffix, File imgSavFile)  throws IOException {
		BufferedImage buffImg = new BufferedImage(img.getWidth(), img.getHeight(),
								BufferedImage.TYPE_INT_ARGB);
		// Create the tool to draw the graph: clear
		Graphics2D g = buffImg.createGraphics();
		g.setComposite(AlphaComposite.Clear);
		g.fill(new Rectangle(buffImg.getWidth(), buffImg.getHeight()));
		g.setComposite(AlphaComposite.getInstance(ru, alp));
		g.setClip(shape);
		g.drawImage(img,0,0,null);
		g.dispose();
		// save ./pieceFile
		System.out.println("PieceFile: " + imgSavFile);
		try{
			ImageIO.write(buffImg, imgSuffix, imgSavFile);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	* Cut the image to fit the rect size
	*
	*/
	public static BufferedImage modifyPieceSize(Rectangle rect, String imgSuffix, File imgSavFile) throws IOException{
		FileInputStream fis = new FileInputStream(imgSavFile);
		Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(imgSuffix);
		ImageReader reader = (ImageReader) it.next();

		reader.setInput(ImageIO.createImageInputStream(fis),true) ;
		ImageReadParam param = reader.getDefaultReadParam();
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0,param);

		System.out.println("PieceFile(Resized): " + imgSavFile);
		ImageIO.write(bi, imgSuffix, imgSavFile);
		return bi;
	}

	public static void main(String[] args) {

		String a="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/PICS/pic-00000300.jpg";
		String b="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/";
		String c="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/";
		try {
			new ShadowCPATCHA().shadowGenerator(a,b,c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class DShape{
	/**
	* Generate all the shapes
	* including: originShape/ rotatedShape/ falseShape
	*/
	public static Shape[] drawShapes(double sh_x,double sh_y, double pic_x, double pic_y, int spin,double ratio){
		if(spin == -1){
			spin = new Random().nextInt(2);
		}
		Shape[] shapes = new Shape[3];
		shapes[0] = generateShape(sh_x - 15 * ratio, sh_y - 15 * ratio, ratio, spin);
		shapes[1] = generateShape(sh_x - 15 * ratio, sh_y - 15 * ratio, ratio, 1 - spin);
		shapes[2] = generateFalseShape(sh_x, sh_y, pic_x, pic_y, ratio);
		return shapes;
	}

	/**
	* @Param: sh_x cordinate of X
	* @Param: sh_y cordinate of X
	* @Param: spin rotate or not
	* @Param: ratio
	* @Description: generate a rectangle and 4 circles
	*/
	public static Shape generateFalseShape(double sh_x,double sh_y,double pic_x,double pic_y,double ratio) {

		Random r = new Random();
		double f_x = r.nextDouble() * (pic_x-200*ratio) + 50 * ratio;
		double f_y = r.nextDouble() * (pic_y-200*ratio) + 50 * ratio;
		int spin = r.nextInt(2);
		spin = (spin == 1) ? 0 : 1;

		// generate the position of False Shape
		while((f_x<(sh_x+150*ratio))&&(f_x>(sh_y-150*ratio))&&(f_y<(sh_y+150*ratio))&&(f_y>(sh_y-150*ratio))) {
			f_x=r.nextDouble()*(pic_x-200*ratio)+50*ratio;
			f_y=r.nextDouble()*(pic_y-200*ratio)+50*ratio;
		}
		f_x-=15*ratio;
		f_y-=15*ratio;

		return generateShape(f_x, f_y, ratio, spin);
	}


/**
* 生成 Shape
*/
private static Shape generateShape(double x,double y,double ratio, int spin){
		Shape rectangle = new RoundRectangle2D.Double(15*ratio+x,15*ratio+y,80*ratio,80*ratio,20*ratio,20*ratio);

		Area a=new Area(rectangle);
		Area up = new Area(new Ellipse2D.Double(25*ratio+x, 0+y, 30*ratio, 30*ratio));
		Area right = new Area(new Ellipse2D.Double(80*ratio+x, 25*ratio+y, 30*ratio, 30*ratio));
		Area left = new Area(new Ellipse2D.Double(0+x, 55*ratio+y, 30*ratio, 30*ratio));
		Area down = new Area(new Ellipse2D.Double(55*ratio+x, 80*ratio+y, 30*ratio, 30*ratio));

		// TODO: Other way to do this
		if(spin == 0){
			a.subtract(up);	a.add(right);	a.add(left);	a.subtract(down);
		} else {
			a.add(up); a.subtract(right);	a.subtract(left);	a.add(down);
		}
		return (Shape)a;
	}
}
