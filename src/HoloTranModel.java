import java.util.ArrayList;

public class HoloTranModel {
    private ArrayList<String> locationFile = new ArrayList<>();
    private ArrayList<Integer> upSec;
    private ArrayList<Integer> downSec;
    private ArrayList<Integer> leftSec;
    private ArrayList<Integer> rightSec;
    private int min;

    public void convertVideoHoloTran() {
        min = 0;
        ImageFromVideo tempVideo = ImageFromVideo.getInstance();
        tempVideo.nextVideo("up", locationFile.get(0), locationFile.get(4));
        upSec = new ArrayList<>(tempVideo.getAllSec());

        tempVideo.nextVideo("down", locationFile.get(1), locationFile.get(4));
        downSec = new ArrayList<>(tempVideo.getAllSec());

        tempVideo.nextVideo("left", locationFile.get(2), locationFile.get(4));
        leftSec = new ArrayList<>(tempVideo.getAllSec());

        tempVideo.nextVideo("right", locationFile.get(3), locationFile.get(4));
        rightSec = new ArrayList<>(tempVideo.getAllSec());

        min = minSize();

        ImagePosition thePosition = new ImagePosition(locationFile.get(4), min, upSec, downSec, leftSec, rightSec);
        new VideoFromImage("HoloTran", thePosition.getLocationProcess(), locationFile.get(4), min);
    }

    public int minSize() {
        int result = upSec.size();
        if(result > downSec.size()){
            result = downSec.size();
        }
        if(result > leftSec.size()) {
            result = leftSec.size();
        }
        if (result > rightSec.size()) {
            result = rightSec.size();
        }
        return result;
    }

    public void addPath(ArrayList<String> input) {
        locationFile = new ArrayList<>(input);
    }
}