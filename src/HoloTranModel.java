import java.awt.*;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Arrays;

public class HoloTranModel {
    private ArrayList<String> locationFile = new ArrayList<String>();
    private ArrayList<Integer> allLastSec = new ArrayList();
    private int min;

    public void convertVideoHoloTran(String set, String location, String out, String name) {

        ImageFromVideo tempVideo = ImageFromVideo.getInstance();
        tempVideo.nextVideo("up", location, out);
        allLastSec.add(tempVideo.getMaxSec());

        tempVideo.nextVideo("down", location, out);
        allLastSec.add(tempVideo.getMaxSec());

        tempVideo.nextVideo("left", location, out);
        allLastSec.add(tempVideo.getMaxSec());

        tempVideo.nextVideo("right", location, out);
        allLastSec.add(tempVideo.getMaxSec());


        min = minSec(allLastSec.stream().mapToInt(i -> i).toArray());

        ImagePosition thePosition = new ImagePosition(set, out, min);

        new VideoFromImage(name, thePosition.getLocationProcess(), out, min);
    }

    public int minSec(int[] input) {
        int result;
        IntSummaryStatistics stat = Arrays.stream(input).summaryStatistics();
        result = stat.getMin();
        return result;
    }
}