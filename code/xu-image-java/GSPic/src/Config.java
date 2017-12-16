/**
* Author: chris
* Created at: 2017/12/16
*/
public class Config {

  // 图片长宽
  public static int picWidth = 800;
  public static int picHeight = 600;

  // 图片类型
  public static String picType = "jpeg";
  public static String picPieceType = "png";

  // 默认地址
  public static String picPath = "./img/pics/";
  public static String piecePath = "./img/pieces/";
  public static String shadowPath = "./img/shadows/";


  public static String getPicPath(String picName){
    return picPath + picName;
  }

  public static String getPiecePath(String pieceName){
    return piecePath + pieceName;
  }

  public static String getShadowPath(String shadowName){
    return shadowPath + shadowName;
  }

  public static String getImageName(String path){
    if(path == null || path.length() == 0){
      return "pic-default-000000";
    }

    String[] dirs = path.split("/");
    String filename = dirs[dirs.length-1];
    String filetype = filename.split("\\.")[1];
    return filename.substring(0, filename.length() - filetype.length() - 1);
  }
}
