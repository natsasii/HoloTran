/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author me
 */
public class HoloTran {

    public static void main(String[] args) {
        HoloTranView theView = new HoloTranView();
        HoloTranModel theModel = new HoloTranModel();
        HoloTranController theController = new HoloTranController(theView, theModel);
        theView.setVisible(true);
    }
    
}
