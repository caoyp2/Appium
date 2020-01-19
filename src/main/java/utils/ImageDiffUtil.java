package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

public class ImageDiffUtil {

    //不同的像素标记为红色
    public static final int RGB_RED = 16711680;

    //允许的Red，Green，Blue单个维度的像素差值
    public static final int DIFF_ALLOW_RANGE = 5;

    //不同像素点统计值
    public static int diffPointCount = 0;

    //从rgb值中抽取red
    public static int getRed(int rgbValue){
        return rgbValue & 0xff0000 >> 16;
    }

    //从rgb值中抽取green
    public static int getGreen(int rgbValue){
        return rgbValue & 0xff00 >> 8;
    }

    //从rgb值中抽取blue
    public static int getBlue(int rgbValue){
        return rgbValue & 0xff;
    }

    /**
     * 比较两图片，并用红色标出不同的像素点，然后保存差异图片到本地，打印匹配率
     * @param srcImgPath
     * @param targetImgPath
     */
    public static void compareImages(String srcImgPath,String targetImgPath){
        try {
            BufferedImage srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage targetImg = ImageIO.read(new File(targetImgPath));

            diffPointCount = 0;
            BufferedImage diffImg = srcImg;

            int srcHeight = srcImg.getHeight();
            int srcWidth = srcImg.getWidth();

            //修改待比较图片的尺寸以适应源图片的尺寸
            targetImg = changeImageSize(targetImg,srcHeight,srcWidth);

            int srcRgb;
            int targetRgb;

            for(int h = 0;h<srcHeight;h++){
                for(int w=0;w<srcWidth;w++){
                    srcRgb = srcImg.getRGB(w,h);
                    targetRgb = targetImg.getRGB(w,h);
                    if( Math.abs(getRed(srcRgb) - getRed(targetRgb))>DIFF_ALLOW_RANGE ||
                            Math.abs(getGreen(srcRgb) - getGreen(targetRgb))>DIFF_ALLOW_RANGE||
                            Math.abs(getBlue(srcRgb) - getBlue(targetRgb))>DIFF_ALLOW_RANGE){
                        diffImg.setRGB(w,h, RGB_RED);
                        diffPointCount++;
                    }
                }
            }

            //保存差异图片
            ImageIO.write(diffImg,"png",new File("diffImg.png"));
            System.out.println("保存差异图片成功！");

            //计算相似度(保留小数点后四位）
            int totalPixel = srcHeight*srcWidth;
            DecimalFormat decimalFormat = new DecimalFormat("#.####");
            double matchRate = (totalPixel-diffPointCount)/(totalPixel*1.0);

            System.out.println("图片相似度为： "+decimalFormat.format(matchRate)+"%");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * 修改BufferedImage中的图片尺寸，以便和源图片进行比较
     * @param image
     * @param newHeight
     * @param newWidth
     * @return
     */
    public static BufferedImage changeImageSize(BufferedImage image,int newHeight,int newWidth){
        Image img = image.getScaledInstance(newWidth,newHeight,Image.SCALE_SMOOTH);
        int width = img.getWidth(null);
        int height = img.getHeight(null);

        //获取新图片的BufferedImage实例
        BufferedImage newBufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = newBufferedImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return newBufferedImage;
    }

    public static void main(String[] args){

        compareImages("1.png","2.png");

    }
}