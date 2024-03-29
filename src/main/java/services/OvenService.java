/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import models.OvenModel;
import servicesui.ServiceUI;

/**
 *
 * @author x14484252
 */
public class OvenService extends Service {
    //Oven Service Branch

    private int maxTemp;
    private int minTemp;
    private int ovenTemp;
    private int power;
    private int FoodLevel;
    private static boolean tempIncreaseing, tempDecreasing, isCooking, cooking, TurnOvenOn, TurnOvenOff,TurnFanOn, foodInOven;

    public OvenService(String serviceName) {
        super(serviceName, "_oven._udp.local.");
        maxTemp = 200;
        minTemp = 0;
        ovenTemp = 0;
        power = 0;
        isCooking = false;
        cooking = false;
        foodInOven = false;
        tempIncreaseing = false;
        tempDecreasing = false;
        TurnOvenOn = false;
        TurnOvenOff = true;
        TurnFanOn = false;
        FoodLevel = 0;
        ui = new ServiceUI(this, serviceName);
    }

    @Override
    protected void performAction(String a) {
        System.out.println("Connected with service: " + a);
        OvenModel oven = new Gson().fromJson(a, OvenModel.class);

        if (oven.getAction() == OvenModel.serviceAction.STATUS) {
            String message = getStatus();
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.STATUS, message));
            sendBack(json);
            
            //increase temperature
        } else if (oven.getAction() == OvenModel.serviceAction.increaseTemp) {
            increaseTemp();
            String message = (tempIncreaseing) ? "The oven tempreature is increasing by 30c!" : "Sorry you cannot increase the tempreature. Oven is at Max tempreature!";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.increaseTemp, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (tempIncreaseing) ? "The oven is increasing by 30c!" : "Sorry: oven is at the max temp";
            ui.updateArea(serviceMessage);
            //decrease temp
        } else if (oven.getAction() == OvenModel.serviceAction.decreaseTemp) {
            decreaseTemp();
            String message = (tempDecreasing) ? "The Oven is cooling down by 30c!" : "Sorry you cannot decrease the tempreature, Oven is at the minimum tempreature!";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.decreaseTemp, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (tempDecreasing) ? "The oven is decreasing by 50c!" : "Sorry: Oven is at the max temp";
            ui.updateArea(serviceMessage);
        }
        //put food in oven
         else if (oven.getAction() == OvenModel.serviceAction.putFoodInOven) {
            putFoodInOven();
            String message = (foodInOven) ? "The food is in the oven" : "There is currently food in the oven";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.putFoodInOven, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (foodInOven) ? "The food is in the oven" : "There is currently food in the oven";
            ui.updateArea(serviceMessage);
         }
      // turn oven on
          else if (oven.getAction() == OvenModel.serviceAction.turnOvenOn) {
            turnOvenOn();
            String message = (TurnOvenOn) ? "The oven has been turned On" : "The oven is currently On";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.turnOvenOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (TurnOvenOn) ? "Oven is turned On" : "Oven is On";
            ui.updateArea(serviceMessage);
        }
          //turnovenoff
           else if (oven.getAction() == OvenModel.serviceAction.turnOvenOff) {
            turnOvenOff();
            String message = (TurnOvenOff) ? "The Oven has been turned off" : "The Oven is currently off";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.turnOvenOff, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (TurnOvenOff) ? "Oven turned off" : "Oven is off";
            ui.updateArea(serviceMessage);
            //turn fan on
        }
           else if (oven.getAction() == OvenModel.serviceAction.turnFanOn) {
            turnFanOn();
            String message = (TurnFanOn) ? "The Fan has turned on" : "The oven is cooling down";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.turnFanOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (TurnFanOn) ? "Fan Has Turned On" : "Fan is on";
            ui.updateArea(serviceMessage);
        }
           //food is cooking
           else if (oven.getAction() == OvenModel.serviceAction.cooking) {
            //Instance of time
            String message = (cooking) ? "The food is cooking" : "The food is currenlty cooking";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.cooking, message, cooking));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (cooking) ? "The food is cook" : "The food is currently cooking";
            ui.updateArea(serviceMessage);
        }
           //finished cooking
           else if (oven.getAction() == OvenModel.serviceAction.finishCooking) {
            finishCooking();
            String message = (cooking) ? "The Food has finished Cooking" : "The oven is cooling down";
            String json = new Gson().toJson(new OvenModel(OvenModel.serviceAction.finishCooking, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (cooking) ? "The is Food has finished cooking" : "Fan is on";
            ui.updateArea(serviceMessage);
        }
        else {
            sendBack(BAD_COMMAND + " - " +a);
        }
    }
    
    public void increaseTemp(){
        if(ovenTemp != maxTemp){
            tempIncreaseing = true;
            ovenTemp += 30;
        } else {
            tempIncreaseing = false;
        }
        if(ovenTemp == 200){
            TurnFanOn = true;
        }
    }
    
    public void decreaseTemp(){
        if(ovenTemp != minTemp){
            tempDecreasing = true;
            ovenTemp -= 30;
        } else {
            tempDecreasing = false;
        }
    }
    
       public void putFoodInOven(){
        if(FoodLevel <= 0){
            foodInOven = true;
            FoodLevel = 4;
        } else {
            foodInOven= false;
        }
    }

    public void turnOvenOff() {
        if (power >= 0) {
            power = 0;
            System.out.println("Power Off. Oven is off");
        }
    }

    public void turnOvenOn() {
        if (power <= 0) {
            power += 100;
            System.out.println("The power level is" +power + ". The Oven is on.");
        }
    }
    
      public void turnFanOn(){
            if(ovenTemp != minTemp){
            tempDecreasing = true;
            ovenTemp -= 10;
        } else {
            tempDecreasing = false;
        }
    }
      public void finishCooking() {
        cooking = false;
        isCooking = false;
    }

      
      /*
      {
        if(ovenTemp != minTemp){
            tempDecreasing = true;
            ovenTemp -= 20;
            TurnFanOn = true;
        } else {
            tempDecreasing = false;
            TurnFanOn = false;
        }
    }
    
*/
    @Override
    public String getStatus() {
        String message = "";
         if (FoodLevel > 0) {
            message = "Food is ready to be Cook!";
        } 

       else if (ovenTemp == 200){
            message = "The fan is on";
        }
        message = "The current tempreature is " + ovenTemp; 
        return message;
    }
   

      
    public static void main(String[] args){
        new OvenService("Oven Service");
    }

}
 