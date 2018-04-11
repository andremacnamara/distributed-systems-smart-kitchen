/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import models.FridgeModel;
import servicesui.ServiceUI;

/**
 *
 * @author x14380181
 */
public class FridgeService extends Service {
    //Fridge Service Branch

    private int maxTemp;
    private int minTemp;
    private int fridgeTemp;
    private int power;
    private int iceLevel;
    private int maxIceLevel;
    private static boolean tempIncreaseing, tempDecreasing, on, off, iceDispensing;

    public FridgeService(String serviceName) {
        super(serviceName, "_fridge._udp.local.");
        maxTemp = 20;
        minTemp = -10;
        fridgeTemp = 5;
        power = 0;
        tempIncreaseing = false;
        tempDecreasing = false;
        on = false;
        off = true;
        iceDispensing=false;
        iceLevel = 0;
        maxIceLevel = 3;
        ui = new ServiceUI(this, serviceName);
    }

    @Override
    protected void performAction(String a) {
        System.out.println("Connected with service: " + a);
        FridgeModel fridge = new Gson().fromJson(a, FridgeModel.class);

        if (fridge.getAction() == FridgeModel.serviceAction.STATUS) {
            String message = getStatus();
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.STATUS, message));
            sendBack(json);
        } else if (fridge.getAction() == FridgeModel.serviceAction.increaseTemp) {
            increaseTemp();
            String message = (tempIncreaseing) ? "The Fridge tempreature is increasing by 1c!" : "Sorry you cannot increase the tempreature. Fridge is at Max tempreature!";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.increaseTemp, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (tempIncreaseing) ? "The fridge is increasing by 1c!" : "Sorry: Fridge is at the max temp";
            ui.updateArea(serviceMessage);
        } else if (fridge.getAction() == FridgeModel.serviceAction.decreaseTemp) {
            decreaseTemp();
            String message = (tempDecreasing) ? "The Room is cooling down by 1c!" : "Sorry you cannot decrease the tempreature, Fridge is at the minimum tempreature!";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.decreaseTemp, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (tempDecreasing) ? "The fridge is decreasing by 1c!" : "Sorry: Fridge is at the max temp";
            ui.updateArea(serviceMessage);
        }
        
         else if (fridge.getAction() == FridgeModel.serviceAction.turnLightsOff) {
            turnLightsOff();
            String message = (off) ? "The Fridge has been turned off" : "The Fridge is currently off";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.turnLightsOff, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (off) ? "Fridge turned off" : "Fridge is off";
            ui.updateArea(serviceMessage);
        }
         
        else if (fridge.getAction() == FridgeModel.serviceAction.turnLightsOn) {
            turnLightsOn();
            String message = (on) ? "The Fridge has been turned on" : "The fridge is on";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.turnLightsOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (on) ? "The fridge has turned on" : "Fridge is on";
            ui.updateArea(serviceMessage);
        }
        
        else if (fridge.getAction() == FridgeModel.serviceAction.dispenseIce) {
            dispenseIce();
            String message = (on) ? "The ice is dispensing" : "The ice has finished dispensing";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.dispenseIce, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (on) ? "The ice is dispensing" : "The ice has finished dispensing";
            ui.updateArea(serviceMessage);
        }
        else {
            sendBack(BAD_COMMAND + " - " +a);
        }
    }
    
    public void increaseTemp(){
        if(fridgeTemp != maxTemp){
            tempIncreaseing = true;
            iceDispensing = false;
            fridgeTemp += 1;
        } else {
            tempIncreaseing = false;
        }
    }
    
    public void decreaseTemp(){
        if(fridgeTemp != minTemp){
            tempDecreasing = true;
            fridgeTemp -= 1;
            iceDispensing = false;
        } else {
            tempDecreasing = false;
        }
    }
    
    public void turnLightsOff() {
        if (power >= 0) {
            power = 0;
            iceDispensing = false;
            System.out.println("Power Off. Fridge is off");
        }
    }

    public void turnLightsOn() {
        if (power <= 0) {
            power += 100;
            iceDispensing = false;
            System.out.println("The power level is" +power + ". The Fridge is on.");
        }
    }
    
    public void dispenseIce(){
        if(iceLevel <= 0){
            iceDispensing = true;
            iceLevel +=1;
        } else {
            iceDispensing = false;
        }
    }
    

    @Override
    public String getStatus() {
        String message = "";
        if(iceDispensing == true){
            message = "Ice currently Dispensing";
        } else if (iceDispensing = false) {
            message = "You have finished dispensing ice";
        }
        
        message = "The current tempreature is" + fridgeTemp;
        return message;
    }
    
    public static void main(String[] args){
        new FridgeService("Fridge Service");
    }

}
