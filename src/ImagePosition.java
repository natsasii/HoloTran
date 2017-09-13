import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagePosition {
    private static int width = 360;
    private static int height = 244;

    private static String outputPrefix;
    private static String inputLocation;
    private static int maxNumber;

    private static BufferedImage imageBase;

    public ImagePosition(String set, String output, int number) {
        this.inputLocation = output + set;
        this.outputPrefix = output + "holoTran";
        this.maxNumber = number;
        mainProcess();
    }

    public String getLocationPocess() {
        return outputPrefix;
    }

    public void mainProcess() {
        try {
            for (int i = 0; i <= maxNumber; i += 2) {
                imageBase = new BufferedImage(1920,1020,BufferedImage.TYPE_INT_RGB);
                BufferedImage imageUp = ImageIO.read(new File(inputLocation + i + ".png"));
                BufferedImage imageDown = ImageIO.read(new File(inputLocation + i + ".png"));
                BufferedImage imageLeft = ImageIO.read(new File(inputLocation + i + ".png"));
                BufferedImage imageRight = ImageIO.read(new File(inputLocation + i + ".png"));

                setPosition(imageUp, imageDown, imageLeft, imageRight, imageBase);

                ImageIO.write(imageBase, "png", new File(outputPrefix + i + ".png"));
            }
        } catch (IOException e) {

        }


    }

//    public ImagePosition(){
//        try{
//        BufferedImage imageUp = ImageIO.read(new File("C:/Users/BestDark/Desktop/a.jpg"));
//        BufferedImage imageDown = ImageIO.read(new File("C:/Users/BestDark/Desktop/b.jpg"));
//        BufferedImage imageLeft = ImageIO.read(new File("C:/Users/BestDark/Desktop/b.jpg"));
//        BufferedImage imageRigth = ImageIO.read(new File("C:/Users/BestDark/Desktop/b.jpg"));
//
//        // setPosition(imageUp, imageDown, imageBase);
//        setPosition(imageUp, imageDown, imageLeft, imageRigth, imageBase);
//
//        ImageIO.write(imageBase, "PNG", new File("C:/Users/BestDark/Desktop/output.PNG"));
//        }catch (IOException e){
//
//        }
//
//    }
//
//    public static void main(String[] args){
//        new ImagePosition();
//    }

    public void setPosition(BufferedImage imageUp, BufferedImage imageDown, BufferedImage imageLeft, BufferedImage imageRight, BufferedImage imageBase){
        Graphics2D g = imageBase.createGraphics();        
        
        // set image up
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians (180), imageUp.getWidth() / 2, imageUp.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(imageUp, null), (imageBase.getWidth() - width) / 2, imageBase.getHeight() / 8, width, height, null);
        
        //set image down
        tx = AffineTransform.getRotateInstance(Math.toRadians (0), imageDown.getWidth() / 2, imageDown.getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(imageDown, null), (imageBase.getWidth() - width) / 2, (imageBase.getHeight() * 5) / 8, width, height, null);
        
        //set image left
        tx = AffineTransform.getRotateInstance(Math.toRadians (90), imageLeft.getWidth() / 2, imageLeft.getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(imageLeft, null), (imageBase.getWidth() / 2) - ((height * 5) / 2), (imageBase.getHeight()- width) / 2, height*3/2, width, null);
        
        //set image right
        tx = AffineTransform.getRotateInstance(Math.toRadians (270), imageRight.getWidth() / 2 , imageRight.getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(imageRight, null), (imageBase.getWidth() / 2) + ((height / 3) * 2), (imageBase.getHeight()- width) / 2, height*3/2, width, null);

        g.dispose();
    }
    
}
