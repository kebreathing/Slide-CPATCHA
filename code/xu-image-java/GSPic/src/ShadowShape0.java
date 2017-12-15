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

public class ShadowShape0 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String a="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/PICS/pic-00001000.jpg";
		String b="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/SHADOW/pic-000010011.";
		String c="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/pic-00001000-11.";
		try {
			new ShadowShape0().MShadowShape(a,b,c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Shape shape;
	private Shape shapeSwitch;
	private Shape falseShape;
	private int rule;
	private float alpha;
	private double sh_x;
	private double sh_y;
	private double ratio;
	private String getpiecePathPic;
	private double pic_x;
	private double pic_y;
	private int spin;
	final String picType = "jpeg";
	final String picPieceType = "png";

	public void MShadowShape(String baseFilePathIn,String baseFilePathOutShadow,String baseFilePathOutPiece) throws IOException{

		File baseFile = new File(baseFilePathIn);
		File shadowFile = new File(baseFilePathOutShadow + picType);
	  File pieceFile = new File(baseFilePathOutPiece + picPieceType);
	  String tmp=baseFilePathOutPiece.replace("-1", "-2");
	  File pieceFileSwitch = new File(tmp + picPieceType);
	  getpiecePathPic=baseFilePathOutPiece.replace("/", "\\");
	  File getPieceFile = new File(getpiecePathPic + picPieceType);
	  File getPieceFileSwitch = new File(tmp.replace("/", "\\") + picPieceType);
	  ratio = 1;

	  BufferedImage SImg = null;
		SImg = ImageIO.read(baseFile);
		pic_x=SImg.getWidth();
		pic_y=SImg.getHeight();

		Random r=new Random();
		sh_x=r.nextDouble()*(pic_x-200*ratio)+50*ratio;
		sh_y=r.nextDouble()*(pic_y-200*ratio)+50*ratio;
		spin=r.nextInt(2);

		DShape fS=new DShape();
		fS.drawShape(sh_x, sh_y, spin,ratio);
		shape=fS.getRightShape();
		shapeSwitch=fS.getFalseShape();

		falseShape = fS.falseShape(sh_x, sh_y, pic_x, pic_y,ratio);

		BufferedImage bi1 = ImageIO.read(new File(baseFilePathIn));
		BufferedImage buffImg = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
		Graphics2D g3 = buffImg.createGraphics();
		g3.setComposite(AlphaComposite.Clear);
		g3.fill(new Rectangle(buffImg.getWidth(),buffImg.getHeight()));
		int ru=AlphaComposite.SRC;
		float alp= 1.0f;
		AlphaComposite composite1=AlphaComposite.getInstance(ru, alp);
		g3.setComposite(composite1);
		g3.setClip(shape);
		g3.drawImage(bi1,0,0,null);
		g3.dispose();
		ImageIO.write(buffImg, picPieceType, pieceFile);

		BufferedImage buffImgf = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
		Graphics2D g4 = buffImgf.createGraphics();
		g4.setComposite(AlphaComposite.Clear);
		g4.fill(new Rectangle(buffImgf.getWidth(),buffImgf.getHeight()));
		g4.setComposite(composite1);
		g4.setClip(shapeSwitch);
		g4.drawImage(bi1,0,0,null);
		g4.dispose();
		ImageIO.write(buffImgf, picPieceType, pieceFileSwitch);

		FileInputStream fis = null ;
    ImageInputStream iis =null ;

		fis = new FileInputStream(getPieceFile);
		Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(picPieceType);
		ImageReader reader = (ImageReader) it.next();

		iis = ImageIO.createImageInputStream(fis);
		reader.setInput(iis,true) ;
		ImageReadParam param = reader.getDefaultReadParam();

		int R_x=(int)(sh_x-15*ratio);
		int R_y=(int)(sh_y-15*ratio);

		Rectangle rect = new Rectangle(R_x, R_y, (int)(120*ratio), (int)(120*ratio));
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0,param);

		ImageIO.write(bi, picPieceType, pieceFile);

		fis = null ;
    iis =null ;

		fis = new FileInputStream(getPieceFileSwitch);
		Iterator<ImageReader> itf = ImageIO.getImageReadersByFormatName(picPieceType);
		ImageReader readerf = (ImageReader) itf.next();

		iis = ImageIO.createImageInputStream(fis);
		readerf.setInput(iis,true) ;
		ImageReadParam paramf = readerf.getDefaultReadParam();

		paramf.setSourceRegion(rect);
		BufferedImage bif = readerf.read(0,paramf);
		ImageIO.write(bif, picPieceType, pieceFileSwitch);

		Graphics2D g2 = SImg.createGraphics();
		rule=AlphaComposite.SRC_OVER;
		alpha= 0.7f;
		AlphaComposite composite=AlphaComposite.getInstance(rule, alpha);
		g2.setComposite(composite);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		ImageIO.write(bi, picPieceType, pieceFile);

		int res=0;
		res = bi.getRGB((int)(70*ratio), (int)(70*ratio)) & 0xFFFFFF;

		int[] rgb = new int [3];
    rgb[0] = (res & 0xff0000) >> 16;
    rgb[1] = (res & 0xff00) >> 8;
    rgb[2] = (res & 0xff);

    if(rgb[0]<0) {
      rgb[0]=0;
    }
    else if(rgb[0]>255) {
      rgb[0]=255;
    }
    if(rgb[1]<0) {
    	rgb[1]=0;
    }
    else if(rgb[1]>255) {
    	rgb[1]=255;
    }
    if(rgb[2]<0) {
    	rgb[0]=0;
    }
    else if(rgb[0]>255) {
    	rgb[0]=255;
    }

    Color color=new Color(rgb[0],rgb[1],rgb[2]);
		g2.setPaint(color);
		g2.fill(shape);

		g2.fill(falseShape);
		g2.dispose();
		ImageIO.write(SImg, picType, shadowFile);
	}

	class DShape{
		private double f_x;
		private double f_y;
		private Shape shapef;
		private Shape shaper;
		public void drawShape(double sh_x,double sh_y,int spin,double ratio) {
			sh_x-=15*ratio;
			sh_y-=15*ratio;

			Shape shape1 = new RoundRectangle2D.Double(15*ratio+sh_x,15*ratio+sh_y,80*ratio,80*ratio,20*ratio,20*ratio);
			Area a=new Area(shape1);
			Area b=new Area(shape1);
			Area shape2 = new Area(new Ellipse2D.Double(25*ratio+sh_x, 0+sh_y, 30*ratio, 30*ratio));
			Area shape3 = new Area(new Ellipse2D.Double(80*ratio+sh_x, 25*ratio+sh_y, 30*ratio, 30*ratio));
			Area shape4 = new Area(new Ellipse2D.Double(0+sh_x, 55*ratio+sh_y, 30*ratio, 30*ratio));
			Area shape5 = new Area(new Ellipse2D.Double(55*ratio+sh_x, 80*ratio+sh_y, 30*ratio, 30*ratio));

			switch(spin) {
			case 0:
				a.subtract(shape2);
				a.add(shape3);
				a.add(shape4);
				a.subtract(shape5);
				shaper=(Shape)a;
				b.add(shape2);
				b.subtract(shape3);
				b.subtract(shape4);
				b.add(shape5);
				shapef=(Shape)b;
				break;
			case 1:
				a.add(shape2);
				a.subtract(shape3);
				a.subtract(shape4);
				a.add(shape5);
				shaper=(Shape)a;
				b.subtract(shape2);
				b.add(shape3);
				b.add(shape4);
				b.subtract(shape5);
				shapef=(Shape)b;
				break;
			default:
			}
		}

		public Shape getRightShape() {
			return shaper;
		}

		public Shape getFalseShape() {
			return shapef;
		}

		public Shape falseShape(double sh_x,double sh_y,double pic_x,double pic_y,double ratio) {

			Random r=new Random();
			f_x=r.nextDouble()*(pic_x-200*ratio)+50*ratio;
			f_y=r.nextDouble()*(pic_y-200*ratio)+50*ratio;
			int spinf=r.nextInt(2);

			while((f_x<(sh_x+150*ratio))&&(f_x>(sh_y-150*ratio))&&(f_y<(sh_y+150*ratio))&&(f_y>(sh_y-150*ratio))) {
				f_x=r.nextDouble()*(pic_x-200*ratio)+50*ratio;
				f_y=r.nextDouble()*(pic_y-200*ratio)+50*ratio;
			}
			f_x-=15*ratio;
			f_y-=15*ratio;
			Shape shapef1 = new RoundRectangle2D.Double(15*ratio+f_x,15*ratio+f_y,80*ratio,80*ratio,20*ratio,20*ratio);
			Area b=new Area(shapef1);
			Area shapef2 = new Area(new Ellipse2D.Double(25*ratio+f_x, 0+f_y, 30*ratio, 30*ratio));
			Area shapef3 = new Area(new Ellipse2D.Double(80*ratio+f_x, 25*ratio+f_y, 30*ratio, 30*ratio));
			Area shapef4 = new Area(new Ellipse2D.Double(0+f_x, 55*ratio+f_y, 30*ratio, 30*ratio));
			Area shapef5 = new Area(new Ellipse2D.Double(55*ratio+f_x, 80*ratio+f_y, 30*ratio, 30*ratio));
			spinf=(spinf==1)?0:1;
			switch(spinf) {
			case 0:
				b.subtract(shapef2);
				b.add(shapef3);
				b.add(shapef4);
				b.subtract(shapef5);
				break;
			case 1:
				b.add(shapef2);
				b.subtract(shapef3);
				b.subtract(shapef4);
				b.add(shapef5);
				break;
			default:
			}
			falseShape=(Shape)b;
			return falseShape;
		}
	}
}
