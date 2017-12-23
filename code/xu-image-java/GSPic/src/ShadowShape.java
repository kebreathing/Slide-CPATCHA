import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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

public class ShadowShape {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String a="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/PICS/pic-00001000.jpg";
		String b="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/SHADOW/pic-000010011.";
		String c="F:/git4elearning/Slide-CPATCHA/code/xu-image-java/slidePic/pic-00001000-11.";
		try {
			new ShadowShape().MShadowShape(a,b,c);
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
	private double sh_x;   //阴影区左上角x坐标
	private double sh_y;   //阴影区左上角y坐标
	private double ru_x;   //阴影区右上角x坐标
	private double ru_y;   //阴影区右上角y坐标
	private double ld_x;   //阴影区左下角x坐标
	private double ld_y;   //阴影区左下角y坐标
	private double rd_x;   //阴影区右下角x坐标
	private double rd_y;   //阴影区右下角y坐标
	private double ratio;  //比例参数，对不同图片，尤其是高清图片可以调整阴影区的大小
	//private String baseFilePathIn="D:\\javaprojects\\pictures\\8761.jpg"; //图片读入路径
	//private String baseFilePathOutShadow="D:\\javaprojects\\pictures\\shadow/b."; //带阴影图片输出路径及名称
	//private String baseFilePathOutPiece="D:\\javaprojects\\pictures\\pieces/c."; //碎片图片输出路径及名称
	private String getpiecePathPic;  //example:getpiecePathPic="D:\\javaprojects\\pictures\\pieces\\c.";
	private double pic_x; //图片长宽设置
	private double pic_y; //图片长宽设置
	private int spin;   //旋转标识，0或1
	final String picType = "jpeg";  //输入输出图片格式
	final String picPieceType = "png";//图片碎片的格式只能是png
	
	public void MShadowShape(String baseFilePathIn,String baseFilePathOutShadow,String baseFilePathOutPiece) throws IOException{
		
		File baseFile = new File(baseFilePathIn);
		File shadowFile = new File(baseFilePathOutShadow + picType);
	    File pieceFile = new File(baseFilePathOutPiece + picPieceType); 
	    String tmp=baseFilePathOutPiece.replace("-1", "-2");
	    File pieceFileSwitch = new File(tmp + picPieceType);
	    getpiecePathPic=baseFilePathOutPiece.replace("/", "\\");
	    File getPieceFile = new File(getpiecePathPic + picPieceType);
	    File getPieceFileSwitch = new File(tmp.replace("/", "\\") + picPieceType);
	    ratio = 1;  //比例参数，更改调整阴影区大小
	    
	    //图片读入
	    BufferedImage bi1 = ImageIO.read(baseFile);
		pic_x=bi1.getWidth();
		pic_y=bi1.getHeight();
			
		//画出阴影区碎片形状，并生成随机坐标x,y以及随机旋转标识spin=0或1，最后结果为得到shape
		Random r=new Random();
		sh_x=r.nextDouble()*(pic_x-200*ratio)+50*ratio;//scale is 50——pic_x-150
		sh_y=r.nextDouble()*(pic_y-200*ratio)+50*ratio;//scale is 50——pic_y-150
		spin=r.nextInt(2);
		
		ru_x = sh_x+110*ratio;   //阴影区右上角x坐标
		ru_y = sh_y;   //阴影区右上角y坐标
		ld_x = sh_x;   //阴影区左下角x坐标
		ld_y = sh_y+110*ratio;   //阴影区左下角y坐标
		rd_x = sh_x+110*ratio;   //阴影区右下角x坐标
		rd_y = sh_y+110*ratio;   //阴影区右下角y坐标
		
		//System.out.println(spin);//only for testing
		DShape fS=new DShape();
		fS.drawShape(sh_x, sh_y, spin,ratio);
		shape=fS.getRightShape();
		shapeSwitch=fS.getFalseShape();
		Shape shapes=fS.getSplitShape();
		
		//画出假的阴影区形状,结果为falseShape	
		falseShape = fS.falseShape(sh_x, sh_y, pic_x, pic_y,ratio);
		Shape shapesf = fS.getSplitfShape();
		
		//将剪裁图形，shape形状之外的部分设置为透明
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
		
		//剪切旋转后的图片碎片
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
		
		//读取图片，并进行大致的剪裁，减为一个小矩形
		FileInputStream fis = null ;  
        ImageInputStream iis =null ; 
        
		fis = new FileInputStream(getPieceFile); //读取图片文件 
		Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(picPieceType);    
		ImageReader reader = (ImageReader) it.next();   
         
		iis = ImageIO.createImageInputStream(fis);//获取图片流      
		reader.setInput(iis,true) ;  
		ImageReadParam param = reader.getDefaultReadParam();   

		int R_x=(int)(sh_x-15*ratio);
		int R_y=(int)(sh_y-15*ratio);
		
		Rectangle rect = new Rectangle(R_x, R_y, (int)(110*ratio), (int)(110*ratio));//定义一个矩形    
		param.setSourceRegion(rect);  //进行剪裁
		BufferedImage bi = reader.read(0,param);//提供一个 BufferedImage，将其用作解码像素数据的目标。   
		
		//输出剪裁后的图片碎片
		ImageIO.write(bi, picPieceType, pieceFile);
		
		//剪裁旋转后的图片为一个矩形
		fis = null ;  
        iis =null ; 
        
		fis = new FileInputStream(getPieceFileSwitch); //读取图片文件   
		Iterator<ImageReader> itf = ImageIO.getImageReadersByFormatName(picPieceType);    
		ImageReader readerf = (ImageReader) itf.next();   
         
		iis = ImageIO.createImageInputStream(fis);//获取图片流      
		readerf.setInput(iis,true) ;  
		ImageReadParam paramf = readerf.getDefaultReadParam();   
		 
		paramf.setSourceRegion(rect);  //进行剪裁
		BufferedImage bif = readerf.read(0,paramf);//提供一个 BufferedImage，将其用作解码像素数据的目标。   
		ImageIO.write(bif, picPieceType, pieceFileSwitch);
		
		//读取图片，并打上阴影区，图片读入 见前
		Graphics2D g2 = bi1.createGraphics();
		rule=AlphaComposite.SRC_OVER;
		alpha= 0.7f;  //阴影区透明度设置
		AlphaComposite composite=AlphaComposite.getInstance(rule, alpha);
		g2.setComposite(composite);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);   //使用 setRenderingHint 设置抗锯齿
		
		ImageIO.write(bi, picPieceType, pieceFile);
		
		//设置阴影颜色
		int res=0;
		int sp_x=r.nextInt(80)+15;//scale is 50——pic_x-150
		int sp_y=r.nextInt(80)+15;
		res = bi.getRGB((int)(sp_x*ratio), (int)(sp_y*ratio)) & 0xFFFFFF;  //change ARGB into RGB,& 0xFFFFFF
		//System.out.println(res);
        Color color=setColors(res);
		g2.setPaint(color);//阴影区颜色设置
		g2.fill(shape);
		//打上假的阴影区
		g2.fill(falseShape);
		g2.dispose();
		
		//将shape进行拆分
		//15*ratio+sh_x,15*ratio+sh_y,80*ratio,80*ratio
		sp_x=r.nextInt(80)+15;//scale is 50——pic_x-150
		sp_y=r.nextInt(80)+15;
		res = bi.getRGB((int)(sp_x*ratio), (int)(sp_y*ratio)) & 0xFFFFFF;  //change ARGB into RGB,& 0xFFFFFF
		Color colors=setColors(res);
		//System.out.println(res);
		Graphics2D gs = bi1.createGraphics();
		float alpha1=0.4f;
		AlphaComposite cSplit=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha1);
		gs.setComposite(cSplit);
		gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);   //使用 setRenderingHint 设置抗锯齿
		gs.setPaint(colors);
		gs.fill(shapes);
		gs.fill(shapesf);
		gs.dispose();
		
		ImageIO.write(bi1, picType, shadowFile);
	}
	
	private Color setColors(int res) {
		int[] rgb = new int [3];
		//System.out.println(res);
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
        Color colors=new Color(rgb[0],rgb[1],rgb[2]);
        return colors;
	}
	
	class DShape{
		private double f_x;
		private double f_y;
		private Shape shapef;
		private Shape shaper;
		private Shape shapeSplit;
		private Shape shapeSplitf;
		public void drawShape(double sh_x,double sh_y,int spin,double ratio) {
			sh_x-=15*ratio;
			sh_y-=15*ratio;
			
			Shape shape1 = new RoundRectangle2D.Double(15*ratio+sh_x,15*ratio+sh_y,80*ratio,80*ratio,20*ratio,20*ratio);
			Area a=new Area(shape1);
			Area b=new Area(shape1);
			Area shape2 = new Area(new Ellipse2D.Double(25*ratio+sh_x, 0+sh_y, 30*ratio, 30*ratio));//上
			Area shape3 = new Area(new Ellipse2D.Double(80*ratio+sh_x, 25*ratio+sh_y, 30*ratio, 30*ratio));//右
			Area shape4 = new Area(new Ellipse2D.Double(0+sh_x, 55*ratio+sh_y, 30*ratio, 30*ratio));//左
			Area shape5 = new Area(new Ellipse2D.Double(55*ratio+sh_x, 80*ratio+sh_y, 30*ratio, 30*ratio));//下
			
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
			shapeSplit=drawSplit(sh_x,sh_y,spin,ratio);
		}
		
		public Shape getRightShape() {
			return shaper;
		}
		
		public Shape getFalseShape() {
			return shapef;
		}
		
		public Shape getSplitShape() {
			return shapeSplit;
		}
		
		public Shape getSplitfShape() {
			return shapeSplitf;
		}
		
		public Shape drawSplit(double sh_x,double sh_y,int spin,double ratio) {
			Shape shapesp1 = new Rectangle2D.Double(15*ratio+sh_x, 40*ratio+sh_y, 80*ratio, 30*ratio);//中间的拆分形状，横着
			Shape shapesp2 = new Rectangle2D.Double(40*ratio+sh_x, 15*ratio+sh_y, 30*ratio, 80*ratio);//中间的拆分形状，竖着
			Area shapes2 = new Area(new Arc2D.Double(25*ratio+sh_x, 0+sh_y, 30*ratio, 30*ratio,0,90,Arc2D.PIE));
			Area shapes3 = new Area(new Arc2D.Double(80*ratio+sh_x, 25*ratio+sh_y, 30*ratio, 30*ratio, 270, 90,Arc2D.PIE));
			Area shapes4 = new Area(new Arc2D.Double(0+sh_x, 55*ratio+sh_y, 30*ratio, 30*ratio, 90, 90,Arc2D.PIE));//左
			Area shapes5 = new Area(new Arc2D.Double(55*ratio+sh_x, 80*ratio+sh_y, 30*ratio, 30*ratio, 180, 90,Arc2D.PIE));//下
			Area c;
			Shape shapes = null;
			switch(spin) {
			case 0:
				c=new Area(shapesp1);
				c.add(shapes3);
				c.add(shapes4);
				shapes=(Shape)c;
				break;
			case 1:
				c=new Area(shapesp2);
				c.add(shapes2);
				c.add(shapes5);
				shapes=(Shape)c;
				break;
			default:
			}
			
			return shapes;
			
		}
		
		public Shape falseShape(double sh_x,double sh_y,double pic_x,double pic_y,double ratio) {
			
			Random r=new Random();//设立假值
			f_x=r.nextDouble()*(pic_x-200*ratio)+50*ratio;
			f_y=r.nextDouble()*(pic_y-200*ratio)+50*ratio;
			int spinf=r.nextInt(2);//假值的旋转参数
			
			while((f_x<(sh_x+150*ratio))&&(f_x>(sh_x-150*ratio))&&(f_y<(sh_y+150*ratio))&&(f_y>(sh_y-150*ratio))) {
				f_x=r.nextDouble()*(pic_x-200*ratio)+50*ratio;
				f_y=r.nextDouble()*(pic_y-200*ratio)+50*ratio;
			}
			f_x-=15*ratio;
			f_y-=15*ratio;	
			Shape shapef1 = new RoundRectangle2D.Double(15*ratio+f_x,15*ratio+f_y,80*ratio,80*ratio,20*ratio,20*ratio);
			Area b=new Area(shapef1);
			Area shapef2 = new Area(new Ellipse2D.Double(25*ratio+f_x, 0+f_y, 30*ratio, 30*ratio));//上
			Area shapef3 = new Area(new Ellipse2D.Double(80*ratio+f_x, 25*ratio+f_y, 30*ratio, 30*ratio));//右
			Area shapef4 = new Area(new Ellipse2D.Double(0+f_x, 55*ratio+f_y, 30*ratio, 30*ratio));//左
			Area shapef5 = new Area(new Ellipse2D.Double(55*ratio+f_x, 80*ratio+f_y, 30*ratio, 30*ratio));//下
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
			shapeSplitf=drawSplit(f_x,f_y,spinf,ratio);
			return falseShape;
		}
	}
}

