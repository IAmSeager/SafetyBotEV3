/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package safetytruck;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import safetytruck.threads.InputHandler;

/**
 *
 * @author Seager
 */
public class FXMLDocumentController implements Initializable {
    
    public static FXMLDocumentController main;
    
    @FXML
    private Label label;
    
    @FXML
    private ChoiceBox controllerList, steeringAxis, accelAxis, calibrationButtonList;
    
    @FXML
    private TextField calibNum;
    
    @FXML
    private Button startButton, calibButton;
    
    InputHandler ih = new InputHandler();
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        main = this;
        controllerList.setOnAction(e -> onChanged());
        calibButton.setOnAction(e -> onCallibRequest());
        ih.start();
        
        
    }

    public ChoiceBox getControllerList(){
        return controllerList;
    }
     public ChoiceBox getSteeringAxis(){
        return steeringAxis;
    }
      public ChoiceBox getAccelAxis(){
        return accelAxis;
    }
    public ChoiceBox getCallibButton(){
        return calibrationButtonList;
    }  
    public TextField getCallibNum(){
        return calibNum;
    }

    private void onChanged() {
        Controller[] controllersList = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for (Controller c : controllersList){
            
            if(c.getName().equalsIgnoreCase((String) controllerList.getValue())){
                
                ArrayList<String> AxisList = new ArrayList<String>();
                ArrayList<String> ButtonList = new ArrayList<String>();
                
                for(Component comp : c.getComponents()){
                    if(comp.isAnalog()){
                    AxisList.add(comp.getName());
                    }
                    else{
                    ButtonList.add(comp.getName());
                    }
                }
                ObservableList<String> obsAxisList = FXCollections.observableArrayList(AxisList);
                ObservableList<String> obsButtonList = FXCollections.observableArrayList(ButtonList);
                steeringAxis.setItems(obsAxisList);
                accelAxis.setItems(obsAxisList);
                calibrationButtonList.setItems(obsButtonList);
            }
        }
    }
    
    private void onCallibRequest() {
        try {
            ih.callibrate(Float.parseFloat(calibNum.getText()));
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
