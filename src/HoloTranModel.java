import java.awt.*;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Arrays;

public class HoloTranModel {
    private ArrayList<String> locationFile = new ArrayList<String>();
    private ArrayList<Integer> allLastSec = new ArrayList();
    private int min;

    public void convertVideoHoloTran(String set, String location, String out) {
//        ImageFromVideo upVideo = new ImageFromVideo("up", location, out);
//        ImageFromVideo rightVideo = new ImageFromVideo("right", location, out);
//        allLastSec.add(upVideo.getMaxSec());


        ImageFromVideo tempVideo = ImageFromVideo.getInstance();
        tempVideo.nextVideo(set, location, out);
        allLastSec.add(tempVideo.getMaxSec());
//
//        ImageFromVideo rightVideo = new ImageFromVideo("right", location, out);
//        allLastSec.add(rightVideo.getMaxSec());
//
//        ImageFromVideo downVideo = new ImageFromVideo("down", location, out);
//        allLastSec.add(downVideo.getMaxSec());

        min = minSec(allLastSec.stream().mapToInt(i -> i).toArray());
        ImagePosition thePosition = new ImagePosition(set, out, min);
        new VideoFromImage("x311", thePosition.getLocationPocess(), out, min);
//        System.out.println("" + min);
    }

    public int minSec(int[] input) {
        int result;
        IntSummaryStatistics stat = Arrays.stream(input).summaryStatistics();
        result = stat.getMin();
        return result;
    }
}