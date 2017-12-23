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

class CustomedShape{
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
