/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.FridgeUI;
import models.FridgeModel;
import com.google.gson.Gson;


/**
 *
 * @author x14380181
 */

/*
 *
 * @reference Dominic Carr 
 * https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 */

/*
 *http://www.vogella.com/tutorials/JavaLibrary-Gson/article.html
 *Vogella
 */

public class FridgeClient extends Client {
    //Actions the fridge can do
    private String increaseTemp  = "increaseTempreature";
    private String decreaseTemp  = "decreaseTempreature";
    private String turnLightsOn  = "turnLightsOn";
    private String turnLightsOff = "turnLightsOff";
    private String dispenseIce = "dispenseIce";
    private String lockIce     = "lockIce";
    private boolean isWarming    = false;
    
    
    /*
    * Constructor
    * Sets services as UDP
    */
    
    public FridgeClient(){
        super();
        serviceType = "_fridge._udp.local.";
        ui = new FridgeUI(this);
        name = "Fridge";
    }
    
    //Json Conversion
    public void increaseTemp(){
        String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.increaseTemp));
        String message = sendMessage(json);
        FridgeModel fridge = new Gson().fromJson(message,FridgeModel.class);
        System.out.println("Client Found " +json);
        
        if (fridge.getAction() == FridgeModel.serviceAction.increaseTemp){
            isWarming = fridge.getValue();
            ui.updateArea(fridge.getMessage());
        }
    }
    
    public void decreaseTemp(){
        String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.decreaseTemp));
        String message = sendMessage(json);
        FridgeModel fridge = new Gson().fromJson(message,FridgeModel.class);
        System.out.println("Client Found " +json);
        
        if (fridge.getAction() == FridgeModel.serviceAction.decreaseTemp){
            isWarming = fridge.getValue();
            ui.updateArea(fridge.getMessage());
        }
    }
    
    public void turnLightsOff(){
        String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.turnLightsOff));
        String message = sendMessage(json);
        FridgeModel fridge = new Gson().fromJson(message, FridgeModel.class);
        System.out.println("Client Recieved " + json);
        
        if(fridge.getAction() == FridgeModel.serviceAction.turnLightsOff){
            isWarming = fridge.getValue();
            ui.updateArea(fridge.getMessage());
        }
    }
    
    public void turnLightsOn(){
        String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.turnLightsOn));
        String message = sendMessage(json);
        FridgeModel fridge = new Gson().fromJson(message, FridgeModel.class);
        System.out.println("Client Recieved " + json);
        
        if(fridge.getAction() == FridgeModel.serviceAction.turnLightsOn){
            isWarming = fridge.getValue();
            ui.updateArea(fridge.getMessage());
        }
    }
    
     public void dispenseIce(){
        String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.dispenseIce));
        String message = sendMessage(json);
        FridgeModel fridge = new Gson().fromJson(message, FridgeModel.class);
        System.out.println("Client Recieved " + json);
        
        if(fridge.getAction() == FridgeModel.serviceAction.dispenseIce){
            isWarming = fridge.getValue();
            ui.updateArea(fridge.getMessage());
        }
    }
     
    public void lockIce(){
        String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.lockIce));
        String message = sendMessage(json);
        FridgeModel fridge = new Gson().fromJson(message, FridgeModel.class);
        System.out.println("Client Recieved " + json);
        
        if(fridge.getAction() == FridgeModel.serviceAction.lockIce){
            isWarming = fridge.getValue();
            ui.updateArea(fridge.getMessage());
        }
        
    }
    
    public void unlockIce(){
        String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.unlockIce));
        String message = sendMessage(json);
        FridgeModel fridge = new Gson().fromJson(message, FridgeModel.class);
        System.out.println("Client Recieved " + json);
        
        if(fridge.getAction() == FridgeModel.serviceAction.unlockIce){
            isWarming = fridge.getValue();
            ui.updateArea(fridge.getMessage());
        }
        
    }
    
    @Override
    public void updatePoll(String message) {
        if (message.equals("Fridge is 100% warmed.")) {
            isWarming = false;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new FridgeUI(this);
        isWarming = false;
    }
    
    
}
