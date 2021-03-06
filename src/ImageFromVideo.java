import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

public class ImageFromVideo extends MediaListenerAdapter {
    
    public static final double SECONDS_BETWEEN_FRAMES = 20;
    
    private static String inputFile;
    private static String outputFilePrefix;
    private static String setProcess;
    
    private static int mVideoStreamIndex = -1;
    
    private static long mLastPtsWrite = Global.NO_PTS;
    public static final long MICRO_SECONDS_BETWEEN_FRAMES = (long)(Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
    
    public ImageFromVideo(String set, String input, String output){
        this.inputFile = input;
        this.outputFilePrefix = output;
        this.setProcess = set;
        mainFunction();
    }
    
    public void mainFunction() {
        IMediaReader mediaReader = ToolFactory.makeReader(inputFile);
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new ImageSnapListener());
//        mediaReader.addListener(new ImageSnapListener(outputFilePrefix, setProcess));
        while (mediaReader.readPacket() == null);
    }
    
    private static class ImageSnapListener extends MediaListenerAdapter{

        
        public void onVideoPicture(IVideoPictureEvent event){
            if(event.getStreamIndex() != mVideoStreamIndex){
                if(mVideoStreamIndex == -1){
                    mVideoStreamIndex = event.getStreamIndex();
                }else{
                    return;
                }
            }
            
            if(mLastPtsWrite == Global.NO_PTS){
                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
            }
            
            if(event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES){
                double seconds = ((double) event.getTimeStamp()) / Global.DEFAULT_PTS_PER_SECOND;
                String outputFilename = dumpImageToFile(event.getImage(), (int)seconds);
                new GreenScreenProcess(outputFilename);
                System.out.printf("Elapsed time of %6.3f seconds wrote: %s \n", seconds, outputFilename);
                
                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
            }
        }
        
        private String dumpImageToFile(BufferedImage image, int sec){
            try{
                String outputFilename = outputFilePrefix + setProcess + sec + ".png";
                ImageIO.write(image, "png", new File(outputFilename));
                return outputFilename;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        
    }
}