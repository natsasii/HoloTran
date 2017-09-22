import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ImagePosition {
    private static int width  = 360;
    private static int height = 244;

    private static String outputPrefix;
    private static String inputLocation;
    private static ArrayList<Integer> upAllSec;
    private static ArrayList<Integer> downAllSec;
    private static ArrayList<Integer> leftAllSec;
    private static ArrayList<Integer> rightAllSec;
    private static int maxNumber;

    private static BufferedImage imageBase;

    public ImagePosition(String output, int number, ArrayList up, ArrayList down, ArrayList left, ArrayList right) {
        this.inputLocation = output;
        this.outputPrefix = output + "holoTran";
        this.maxNumber = number;
        upAllSec = new ArrayList<>(up);
        downAllSec = new ArrayList<>(down);
        leftAllSec = new ArrayList<>(left);
        rightAllSec = new ArrayList<>(right);
        mainProcess();
    }

    public String getLocationProcess() {
        return outputPrefix;
    }

    public void mainProcess() {
        try {
            for (int i = 0; i < maxNumber; i++) {
                imageBase = new BufferedImage(1920,1020,BufferedImage.TYPE_INT_RGB);
                BufferedImage imageUp = ImageIO.read(new File(inputLocation + "up" + upAllSec.get(i) + ".png"));
                BufferedImage imageDown = ImageIO.read(new File(inputLocation + "down" + downAllSec.get(i) + ".png"));
                BufferedImage imageLeft = ImageIO.read(new File(inputLocation + "left" + leftAllSec.get(i) + ".png"));
                BufferedImage imageRight = ImageIO.read(new File(inputLocation + "right" + rightAllSec.get(i) + ".png"));

                setPosition(imageUp, imageDown, imageLeft, imageRight, imageBase);

                ImageIO.write(imageBase, "png", new File(outputPrefix + i + ".png"));
            }
        } catch (IOException e) {

        }
    }


    public void setPosition(BufferedImage imageUp, BufferedImage imageDown, BufferedImage imageLeft, BufferedImage imageRight, BufferedImage imageBase){
        Graphics2D g = imageBase.createGraphics();        
        
        // set image up
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians (0), imageUp.getWidth() / 2, imageUp.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//        g.drawImage(op.filter(imageUp, null), (imageBase.getWidth() - width) / 2, imageBase.getHeight() / 8, width, height, null);
        g.drawImage(op.filter(imageUp, null), (imageBase.getWidth() - width) / 2, imageBase.getHeight() / 8, width, height, null);
        
        //set image down
        tx = AffineTransform.getRotateInstance(Math.toRadians (180), imageDown.getWidth() / 2, imageDown.getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//        g.drawImage(op.filter(imageDown, null), (imageBase.getWidth() - width) / 2, (imageBase.getHeight() * 5) / 8, width, height, null);
        g.drawImage(op.filter(imageDown, null), (imageBase.getWidth() - width) / 2, (imageBase.getHeight() * 5) / 8, width, height, null);

        //set image left
        tx = AffineTransform.getRotateInstance(Math.toRadians (270), imageLeft.getWidth() / 2, imageLeft.getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(imageLeft,  null), (imageBase.getWidth() * 2) / 8, (imageBase.getHeight()- width) / 2, width, height, null);
//        g.drawImage(op.filter(imageLeft,  null), (imageBase.getWidth() / 2) - ((height * 5) / 2), (imageBase.getHeight()- width) / 2, height*3/2, width, null);
        
        //set image right
        tx = AffineTransform.getRotateInstance(Math.toRadians (90), imageRight.getWidth() / 2 , imageRight.getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(imageRight, null), (imageBase.getWidth() * 5 ) / 8, (imageBase.getHeight()- width) / 2, width, height, null);
//        g.drawImage(op.filter(imageRight, null), (imageBase.getWidth() / 2) + ((height / 3) * 2), (imageBase.getHeight()- width) / 2, height*3/2, width, null);

        g.dispose();
    }
    
}
