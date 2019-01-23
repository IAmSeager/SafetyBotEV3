/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package safetytruck.threads;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;  
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 *
 * @author Seager
 */
public class InputHandler extends Thread {
    
    public InputHandler(){
        super("InputThread");
    }
    
    @Override
    public void run(){
      while(true) {	
          Event event = new Event();

	  Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
          for (Controller controller : controllers) {
              controller.poll();
              EventQueue queue = controller.getEventQueue();
              while (queue.getNextEvent(event)) {
                  Component comp = event.getComponent();
                  
                  //TODO
                  
              }
          }
		}              
    } 
    
}
