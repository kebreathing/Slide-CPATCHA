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

public class ShadowShape {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			new ShadowShape();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private Shape shape;
	private Shape falseShape;
	private int rule;
	private float alpha;
	private double sh_x;   //阴影区x坐标
	private double sh_y;   //阴影区y坐标
	private double f_x;
	private double f_y;
	private String baseFilePathIn="D:\\javaprojects\\pictures\\8761.jpg"; //图片读入路径
	private String baseFilePathOutShadow="D:\\javaprojects\\pictures\\shadow/b."; //带阴影图片输出路径及名称
	private String baseFilePathOutPiece="D:\\javaprojects\\pictures\\pieces/c."; //碎片图片输出路径及名称
	private String getpiecePathPic="D:\\javaprojects\\pictures\\pieces\\c.";
	private double pic_x=700; //图片长宽设置
	private double pic_y=500; //图片长宽设置
	private int spin;   //旋转标识，0或1
	final String picType = "jpg";  //输入输出图片格式
	final String picPieceType = "png";//图片碎片的格式只能是png
	
	public ShadowShape() throws IOException{
		
		File baseFile = new File(baseFilePathIn);
		File shadowFile = new File(baseFilePathOutShadow + picType);
	    File pieceFile = new File(baseFilePathOutPiece + picPieceType);
	    File getPieceFile = new File(getpiecePathPic + picPieceType);
	    
	    //图片读入
	    BufferedImage SImg = null;
		SImg = ImageIO.read(baseFile);
		pic_x=SImg.getWidth();
		pic_y=SImg.getHeight();
			
		//画出阴影区碎片形状，并生成随机坐标x,y以及随机旋转标识0或1，最后结果为得到shape
		Random r=new Random();
		sh_x=r.nextDouble()*(pic_x-100)+50;//范围是50——pic_x-50
		sh_y=r.nextDouble()*(pic_y-100)+50;//范围是50——pic_y-50
		spin=r.nextInt(2);
		
		if(sh_x>pic_x/2) {  //设立假值
			f_x=r.nextDouble()*(pic_x/2-70)+50;//若阴影区在图右边，假阴影区在图左边，范围是50——pic_x/2-20
		}
		else {
			f_x=r.nextDouble()*(pic_x/2-70)+pic_x/2+20;//同上
		}
		
		if(sh_y>pic_y/2) {  //设立假值
			f_y=r.nextDouble()*(pic_y/2-70)+50;//若阴影区在图下边，假阴影区在图上边，范围是50——pic_y/2-20
		}
		else {
			f_y=r.nextDouble()*(pic_y/2-70)+pic_y/2+20;//同上
		}
		
		//仅测试用
		//System.out.println(spin);
		
		Shape shape1 = new RoundRectangle2D.Double(15+sh_x,15+sh_y,80,80,20,20);
		Area a=new Area(shape1);
		Area shape2 = new Area(new Ellipse2D.Double(25+sh_x, 0+sh_y, 30, 30));//上
		Area shape3 = new Area(new Ellipse2D.Double(80+sh_x, 25+sh_y, 30, 30));//右
		Area shape4 = new Area(new Ellipse2D.Double(0+sh_x, 55+sh_y, 30, 30));//左
		Area shape5 = new Area(new Ellipse2D.Double(55+sh_x, 80+sh_y, 30, 30));//下
		switch(spin) {
		case 0:
			a.subtract(shape2);
			a.add(shape3);
			a.add(shape4);
			a.subtract(shape5);
			break;
		case 1:
			a.add(shape2);
			a.subtract(shape3);
			a.subtract(shape4);
			a.add(shape5);
			break;
		default:
		}
		shape=(Shape)a;
		
		//画出假的阴影区形状,结果为falseShape	
		Shape shapef1 = new RoundRectangle2D.Double(15+f_x,15+f_y,80,80,20,20);
		Area b=new Area(shapef1);
		Area shapef2 = new Area(new Ellipse2D.Double(25+f_x, 0+f_y, 30, 30));//上
		Area shapef3 = new Area(new Ellipse2D.Double(80+f_x, 25+f_y, 30, 30));//右
		Area shapef4 = new Area(new Ellipse2D.Double(0+f_x, 55+f_y, 30, 30));//左
		Area shapef5 = new Area(new Ellipse2D.Double(55+f_x, 80+f_y, 30, 30));//下
		spin=(spin==1)?0:1;
		switch(spin) {
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
		
		
		//读取图片，并打上阴影区，图片读入 见前面开始
		Graphics2D g2 = SImg.createGraphics();
		
		rule=AlphaComposite.SRC_OVER;
		alpha= 0.7f;  //阴影区透明度设置
		AlphaComposite composite=AlphaComposite.getInstance(rule, alpha);
		g2.setComposite(composite);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);   //使用 setRenderingHint 设置抗锯齿
		g2.setPaint(Color.WHITE);//阴影区颜色设置
		g2.fill(shape);
		
		//打上假的阴影区
		g2.fill(falseShape);

		g2.dispose();
		ImageIO.write(SImg, picType, shadowFile);
		        
		//将剪裁图形，shape形状之外的部分设置为透明
		//shape=(Shape)a;
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
		
		//读取图片，并进行大致的剪裁，减为一个小矩形
		FileInputStream fis = null ;  
        ImageInputStream iis =null ; 
        
		fis = new FileInputStream(getPieceFile); //读取图片文件 
		Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(picPieceType);    
		ImageReader reader = (ImageReader) it.next();   
         
		iis = ImageIO.createImageInputStream(fis);//获取图片流      
		reader.setInput(iis,true) ;  
		ImageReadParam param = reader.getDefaultReadParam();   

		int R_x=(int)sh_x;
		int R_y=(int)sh_y;
		
		Rectangle rect = new Rectangle(R_x, R_y, 120, 120);//定义一个矩形    
		param.setSourceRegion(rect);  //进行剪裁
		BufferedImage bi = reader.read(0,param);//提供一个 BufferedImage，将其用作解码像素数据的目标。   
		
		//输出剪裁后的图片碎片
		ImageIO.write(bi, picPieceType, pieceFile);
	}
}

