package client;

import clientui.ToasterUI;
import models.ToasterModel;
import com.google.gson.Gson;

/**
 *
 * @author andre
 */
public class ToasterClient extends Client {
    //turnOn
    //turnOff
    //toastBread
    //popUpBread
    //increaseTemp
    //decreaseTemp
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
