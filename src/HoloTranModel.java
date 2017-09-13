import java.util.ArrayList;

public class HoloTranModel {
    private ArrayList<String> locationFile = new ArrayList<String>();

    public void convertVideoHoloTran(String set, String location, String out){
        new ImageFromVideo("up", location, out);
        new ImageFromVideo("left", location, out);
        new ImageFromVideo("right", location, out);
        new ImageFromVideo("down", location, out);
    }

    public void firstStep(String set, String location, String out) {
//        new ImageFromVideo(set, location, out); //Image from video and cut green screen
    }
    
    public void lastStep(String location, String out) {
        //Image position and create video from images
    }
}