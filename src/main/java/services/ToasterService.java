package services;

import com.google.gson.Gson;
import models.ToasterModel;
import servicesui.ServiceUI;

/**
 *
 * @author andre
 */
public class ToasterService extends Service{
    
    private int power;
    private int breadLevel;
    private static boolean turnToasterOn, turnToasterOff, breadInToaster, toasting;
    
    public ToasterService(String serviceName){
        super(serviceName, "_toaster._udp.local.");
        //Toaster Starts Off
            turnToasterOn = false;
            turnToasterOff = true; 
            breadInToaster = false;
            breadLevel = 0;
            toasting = false;
            
        //
        ui = new ServiceUI(this, serviceName);
    }

    @Override
    protected void performAction(String a) {
        System.out.println("Connected with service: " + a);
        ToasterModel toaster = new Gson().fromJson(a, ToasterModel.class);
        
        
        if (toaster.getAction() == ToasterModel.serviceAction.STATUS) {
            String message = getStatus();
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.STATUS, message));
            sendBack(json);
        }
        else if (toaster.getAction() == ToasterModel.serviceAction.turnToasterOn) {
            turnToasterOn();
            String message = (turnToasterOn) ? "The Toaster has been turned On" : "The Toaster is currently On";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.turnToasterOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (turnToasterOn) ? "Fridge turned On" : "Fridge is On";
            ui.updateArea(serviceMessage);
        }
         
        else if (toaster.getAction() == ToasterModel.serviceAction.turnToasterOff) {
            turnToasterOff();
            String message = (turnToasterOff) ? "The Toaster has been turned Off" : "The Toaster is currently Off";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.turnToasterOff, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (turnToasterOff) ? "Toaster turned Off" : "Toaster is Off";
            ui.updateArea(serviceMessage);
        }
        
        else if (toaster.getAction() == ToasterModel.serviceAction.putBreadInToaster) {
            putBreadInToaster();
            String message = (breadInToaster) ? "The bread is in the toaster" : "There is currently bread in the toaster";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.putBreadInToaster, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (breadInToaster) ? "The bread is in the toaster" : "There is currently bread in the toaster";
            ui.updateArea(serviceMessage);
        }
        
        else if (toaster.getAction() == ToasterModel.serviceAction.toasting){
            Toasting();
            String message = (toasting) ? "The bread is toasting" : "The bread is currently toasting";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.toasting, message));
            System.out.println(json);
            sendBack(json);
            
            String serviceMessage = (toasting) ? "The bread is toasting" : "The bread is currently toasting";
            ui.updateArea(serviceMessage);
        }
        
        else {
            sendBack(BAD_COMMAND + " - " +a);
        }
    }
    
    public void turnToasterOn(){
        if(power <= 0){
            power += 100;
            System.out.println("The toaster is powered on");
        }
    }
    
    public void turnToasterOff(){
        if(power >= 0){
            power = 0;
            System.out.println("The toaster is powered off");
        }
    }
    
    public void putBreadInToaster(){
        if(breadLevel <= 0){
            breadInToaster = true;
            breadLevel = 4;
        } else {
            breadInToaster = false;
        }
    }
    
    public void Toasting(){
        if(breadLevel >= 0){
            toasting = true;
        } else {
            toasting = false;
        }
        System.out.println(breadLevel);
    }

    @Override
    public String getStatus() {
        return "The current power level is " + power; 
    }
    
    public static void main(String[] args){
        new ToasterService("ToasterService");
    }

}
