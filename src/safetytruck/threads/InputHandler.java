/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package safetytruck.threads;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public void run() {
        
            Controller[] controllersList = ControllerEnvironment.getDefaultEnvironment().getControllers();
            
            ObservableList<Controller> oList = FXCollections.observableArrayList(controllersList);
            for(Controller c : controllersList){
                FXMLDocumentController.main.getControllerList().getItems().add(c.getName());
            }
            
            
       
        while (true) {
            Event event = new Event();

            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
            
            for (Controller controller : controllers) {
                controller.poll();
                EventQueue queue = controller.getEventQueue();
                while (queue.getNextEvent(event)) {
                    Component comp = event.getComponent();
                    
                }
            }
        }
    }

}
