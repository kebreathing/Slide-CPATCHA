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

public class CustomedImage {

  public static boolean cutImage(String imgPath){
    try{
      File img = new File(imgPath);
      BufferedImage base = ImageIO.read(img);
      if(base.getWidth() < Config.picWidth
        || base.getHeight() < Config.picHeight)
        return false;

      BufferedImage cuted = base.getSubimage(0, 0, Config.picWidth, Config.picHeight);
      ImageIO.write(cuted, Config.picType, img);
    } catch(IOException e){
      e.printStackTrace();
    }

    return true;
  }

  public static void main(String[] args){
    cutImage("F:/git4elearning/Slide-CPATCHA/code/imgs/pic-00000400.jpg");
  }
}
