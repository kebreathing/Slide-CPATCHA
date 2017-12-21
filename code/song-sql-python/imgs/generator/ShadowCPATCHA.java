import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import java.util.Random;

public class ShadowCPATCHA {


	public static void shadowGeneratorDir(String dirPathIn,
															String filePathOutShadow,
															String filePathOutPiece,
															String filePathcomponent) throws IOException{
		// read the files of the directory
		// If the input is null, set to default
		if(dirPathIn == null || dirPathIn.length() == 0) {
			System.out.println("First Parameter [dirPathIn] cannot be null");
			return;
		}

		File directory = new File(dirPathIn);
		if(directory.isDirectory()){
			for(File f : directory.listFiles()){
				String fileName = f.getName();
				new Thread(new Runnable(){
					@Override
					public void run(){
						System.err.println("Running Thread...");
						try{
							shadowGenerator(dirPathIn + fileName, filePathOutShadow, filePathOutPiece, filePathcomponent);
						} catch(IOException e){
							e.printStackTrace();
						}
					}
				}).start();
			}
		} else {
			shadowGenerator(dirPathIn, filePathOutShadow, filePathOutPiece, filePathcomponent);
		}
	}

	/**
	* @PARAM: filePathIn: the [file] path of input image, called Origin Image
	* @PARAM: filePathOutShadow: the [directory] path of storing the shadow Images
	* @PARAM: filePathOutPiece: the [directory] path of storing the piece images
	*/
	public static void shadowGenerator(String filePathIn,
													String filePathOutShadow,
													String filePathOutPiece,
													String filePathcomponent) throws IOException{
		// if the path is null
		if(filePathOutShadow == null || filePathOutShadow.length() == 0)
			filePathOutShadow = Config.piecePath;
		if(filePathOutPiece == null || filePathOutPiece.length() == 0)
			filePathOutPiece = Config.shadowPath;
		if(filePathcomponent == null || filePathcomponent.length() == 0)
			filePathcomponent = Config.componentPath;

		// if the last letter is not /, append a /
		filePathOutShadow += (filePathOutShadow.charAt(filePathOutShadow.length()-1) == '/') ? "" : "/";
		filePathOutPiece += (filePathOutPiece.charAt(filePathOutPiece.length()-1) == '/') ? "" : "/";

		// Image Name without suffix
		String imgName = Config.getImageName(filePathIn);
		// Origin Image
		File baseFile = new File(filePathIn);
		// Shadow Image
		File shadowFile = new File(filePathOutShadow + imgName + "." + Config.picType);
		// Piece
	  File pieceFile = new File(filePathOutPiece + imgName + "-00." + Config.picPieceType);
		// Rotated Piece
	  File pieceRotatedFile = new File(filePathOutPiece + imgName + "-01." + Config.picPieceType);
		// Record File
		File componentFile = new File(filePathcomponent + imgName + ".config" );
		// Read the Image file
	  BufferedImage baseImg = ImageIO.read(baseFile);

		// Generate all the shapes
		// Generate 3 Shapes: Origin/ Rotated/ False
		double ratio = 1;
		Random r=new Random();
		double sh_x=r.nextDouble()*(baseImg.getWidth()-200*ratio)+50*ratio;
		double sh_y = r.nextDouble()*(baseImg.getHeight()-200*ratio)+50*ratio;
		int spin = r.nextInt(2);

		Shape[] shapes = CustomedShape.drawShapes(sh_x, sh_y, baseImg.getWidth(), baseImg.getHeight(), spin, ratio);
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

		// Record the information
		componentFile.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(componentFile));
		writer.write("Origin:" + baseFile.getPath() + "\n");
		writer.write("Shadow:" + shadowFile.getPath() + "\n");
		writer.write("Piece:" + pieceFile.getPath() + "\n");
		writer.write("PieceRotated:" + pieceRotatedFile.getPath() + "\n");
		writer.flush();
		writer.close();
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
	private static BufferedImage modifyPieceSize(Rectangle rect, String imgSuffix, File imgSavFile) throws IOException{
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

		// String a ="F:/git4elearning/Slide-CPATCHA/code/imgs/pics/pic-00000300.jpg";
		try {
			// new ShadowCPATCHA().shadowGenerator(a,b,c);
			ShadowCPATCHA.shadowGeneratorDir(Config.picPath, null, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
