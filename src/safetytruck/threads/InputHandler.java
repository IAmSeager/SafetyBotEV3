/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package safetytruck.threads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import safetytruck.FXMLDocumentController;

/**
 *
 * @author Seager
 */
public class InputHandler extends Thread {

    public ArrayList<String> steerAxis = new ArrayList<String>();
    
    public InputHandler() {
        super("InputThread");
    }

    public void callibrate(float num) throws IOException, InterruptedException{
        BufferedWriter callibrateWriter = new BufferedWriter(new FileWriter("Calibrate.txt"));
        callibrateWriter.write(num + "");
        callibrateWriter.close();
        Thread.sleep(500);
        callibrateWriter = new BufferedWriter(new FileWriter("Calibrate.txt"));
        callibrateWriter.write("");
        callibrateWriter.close();
    }
    
    @Override
    public void run() {
        
            Controller[] controllersList = ControllerEnvironment.getDefaultEnvironment().getControllers();
            
            ObservableList<Controller> oList = FXCollections.observableArrayList(controllersList);
            for(Controller c : controllersList){
                FXMLDocumentController.main.getControllerList().getItems().add(c.getName());
            }
            
            
       try{
        while (true) {
            Event event = new Event();

            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
            
            for (Controller controller : controllers) {
                controller.poll();
                EventQueue queue = controller.getEventQueue();
                while (queue.getNextEvent(event)) {
                    Component comp = event.getComponent();
                    if(comp.getName().equals(FXMLDocumentController.main.getSteeringAxis().getValue())){
                        BufferedWriter SteeringWriter = new BufferedWriter(new FileWriter("XAxis.txt"));
                        SteeringWriter.write(comp.getPollData() + "");
                        SteeringWriter.close();
                    }
                    
                    if(comp.getName().equals(FXMLDocumentController.main.getAccelAxis().getValue())){
                        BufferedWriter SteeringWriter = new BufferedWriter(new FileWriter("YAxis.txt"));
                        SteeringWriter.write(comp.getPollData() + "");
                        SteeringWriter.close();
                    }
                    if(comp.getName().equals(FXMLDocumentController.main.getCallibButton().getValue())){
                        try {
                            callibrate(Float.parseFloat(FXMLDocumentController.main.getCallibNum().getText()));
                        } catch (InterruptedException ex) {
                            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }
            }
        }
       }
       catch(IOException e){
           e.printStackTrace();
       }
    }

}
