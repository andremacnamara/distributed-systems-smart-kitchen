package client;

import clientui.ToasterUI;
import models.ToasterModel;
import com.google.gson.Gson;

/**
 *
 * @author andre
 */

/*
 *
 * @reference Dominic Carr 
 * https://moodle.ncirl.ie/mod/resource/view.php?id=54977
 *
 */

/*
 *http://www.vogella.com/tutorials/JavaLibrary-Gson/article.html
 *Vogella
 */

public class ToasterClient extends Client {
    private String turnToasterOn = "turnToasterOn";
    private String turnToasterOff = "turnToasterOff";
    private String putBreadInToaster = "putBreadInToaster";
    private boolean isToasting = false;
    
    public ToasterClient(){
        super();
        serviceType = "_toaster._udp.local.";
        ui = new ToasterUI(this);
        name = "Toaster";
    }
    
    
    //Json
    public void turnToasterOn(){
        String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.turnToasterOn));
        String message = sendMessage(json);
        ToasterModel toaster = new Gson().fromJson(message, ToasterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(toaster.getAction() == ToasterModel.serviceAction.turnToasterOn){
            isToasting = toaster.getValue();
            ui.updateArea(toaster.getMessage());
        }
    }
    
    public void turnToasterOff(){
        String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.turnToasterOff));
        String message = sendMessage(json);
        ToasterModel toaster = new Gson().fromJson(message, ToasterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(toaster.getAction() == ToasterModel.serviceAction.turnToasterOff){
            isToasting = toaster.getValue();
            ui.updateArea(toaster.getMessage());
        }
    }
    
    public void putBreadInToaster(){
        String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.putBreadInToaster));
        String message = sendMessage(json);
        ToasterModel toaster = new Gson().fromJson(message, ToasterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(toaster.getAction() == ToasterModel.serviceAction.putBreadInToaster){
            isToasting = toaster.getValue();
            ui.updateArea(toaster.getMessage());
        }
    }
    
    public void Toasting(){
        String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.toasting));
        String message = sendMessage(json);
        ToasterModel toaster = new Gson().fromJson(message, ToasterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(toaster.getAction() == ToasterModel.serviceAction.toasting){
            isToasting = toaster.getValue();
            ui.updateArea(toaster.getMessage());
        }
    }
    
    public void finishToasting(){
        String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.finishToasting));
        String message = sendMessage(json);
        ToasterModel toaster = new Gson().fromJson(message, ToasterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(toaster.getAction() == ToasterModel.serviceAction.finishToasting){
            isToasting = toaster.getValue();
            ui.updateArea(toaster.getMessage());
        }
    }
    
    
    
    @Override
    public void updatePoll(String message){
        if(message.equals("100% Toasted")){
            isToasting = false;
        }
    }
    
    @Override
    public void disable(){
        super.disable();
        ui = new ToasterUI(this);
        isToasting = false;
    }
}
