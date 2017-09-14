import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;

public class HoloTranView extends JFrame {

    private static String path;

    HoloTranView() {
        super("Convert Video to Hologram");
//        setVisible(true);
        setSize(450,300);
        setLocation(getWidth(), getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JTextField textLocation = new JTextField();
        JButton btnButton = new JButton("Choose video mp4");
        btnButton.setBounds(160, 94, 120, 25);
        btnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter(".mp4", "mp4");
                fileOpen.addChoosableFileFilter(filter);

                int ret = fileOpen.showDialog(null, "Select");

                if(ret == JFileChooser.APPROVE_OPTION){
                    path = fileOpen.getSelectedFile().toString();
//                    textLocation. = path;
                }

            }
        });
        getContentPane().add(btnButton);
    }

    public String getUpVideoLocation() {
        return path;
    }

    public String getDownVideoLocation() {
        return "";
    }

    public String getLeftVideoLocation() {
        return "";
    }

    public String getRigthVideoLocation() {
        return "";
    }

    public String getOutputVideoLocation() {
        return "";
    }

    void addHoloTranListener(ActionListener listenForHoloTran) {
//        HoloTranStartButton.addActionListener(listenForHoloTran);
    }
    
}