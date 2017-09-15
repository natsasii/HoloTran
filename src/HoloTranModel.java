import java.awt.*;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Arrays;

public class HoloTranModel {
    private ArrayList<String> locationFile = new ArrayList<String>();
    private ArrayList<Integer> allLastSec = new ArrayList();
    private int min;

    public void convertVideoHoloTran() {
//        min = 0;
        ImageFromVideo tempVideo = ImageFromVideo.getInstance();
        tempVideo.nextVideo("up", locationFile.get(0), locationFile.get(4));
        allLastSec.add(tempVideo.getMaxSec());

        tempVideo.nextVideo("down", locationFile.get(1), locationFile.get(4));
        allLastSec.add(tempVideo.getMaxSec());

        tempVideo.nextVideo("left", locationFile.get(2), locationFile.get(4));
        allLastSec.add(tempVideo.getMaxSec());

        tempVideo.nextVideo("right", locationFile.get(3), locationFile.get(4));
        allLastSec.add(tempVideo.getMaxSec());


        min = minSec(allLastSec.stream().mapToInt(i -> i).toArray());
//        System.out.println(min);
//        allLastSec.clear();
        ImagePosition thePosition = new ImagePosition(locationFile.get(4), min);

        new VideoFromImage("HoloTran", thePosition.getLocationProcess(), locationFile.get(4), min);
//        locationFile.clear();
    }

    public int minSec(int[] input) {
        int result;
        IntSummaryStatistics stat = Arrays.stream(input).summaryStatistics();
        result = stat.getMin();
        return result;
    }

    public void addPath(ArrayList<String> input) {
        locationFile = new ArrayList<String>(input);
    }
}