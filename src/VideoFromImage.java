import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public class VideoFromImage {

    private static Dimension screenBounds;
    public static int indexVideo;
    private static String OUTPUT_FILE;
    private static String INPUT_LOCATION;
    private static String OUT_LOCATION;
    private static int maxSec;

    public VideoFromImage(String outputName, String InputLocationName, String out, int num) {
        this.INPUT_LOCATION = InputLocationName;
        this.OUT_LOCATION = out;
        this.OUTPUT_FILE = OUT_LOCATION + outputName + ".mp4";
        this.maxSec = num;
        this.indexVideo = 0;
        mainFunction();
    }
    
    public void mainFunction() {
        final IMediaWriter writer = ToolFactory.makeWriter(OUTPUT_FILE);
        screenBounds = Toolkit.getDefaultToolkit().getScreenSize();
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4,
                screenBounds.width , screenBounds.height );
        long startTime = System.nanoTime();

        for (int index = 0; index < maxSec; index++) {
                BufferedImage bgrScreen = getVideoImage();
                System.out.println("time stamp = " + (System.nanoTime() - startTime));
                bgrScreen = convertToType(bgrScreen, BufferedImage.TYPE_3BYTE_BGR);
                writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime,
                        TimeUnit.NANOSECONDS);
            try {
                Thread.sleep((long) (100));
            } catch (InterruptedException e) {
                // ignore
            }
        }
        writer.close();
    }

    private static BufferedImage getVideoImage() {

        File imgLoc = new File(INPUT_LOCATION + indexVideo + ".png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(imgLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexVideo++;
        System.out.println(imgLoc.getName());
        return img;
    }

    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {

        BufferedImage image;

        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        } else {
            image = new BufferedImage(sourceImage.getWidth(),
                    sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;

    }

}