
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
 *
 *
 * /*
 *
 * @reference Dominic Carr
 * https://moodle.ncirl.ie/mod/resource/view.php?id=54977	/example.java
 *
 */

/*
 *http://www.vogella.com/tutorials/JavaLibrary-Gson/article.html
 *Vogella
 */
public class FridgeService extends Service {
    //Fridge Service Branch

    private int maxTemp;
    private int minTemp;
    private int fridgeTemp;
    private int power;
    private int iceLevel;
    private int maxIceLevel;
    private static boolean tempIncreaseing, tempDecreasing, on, off, iceDispensing, lockIce, unlockIce;

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
        iceDispensing = false;
        iceLevel = 0;
        maxIceLevel = 3;
        lockIce = false;
        unlockIce = false;
        ui = new ServiceUI(this, serviceName);
    }

    //Gson/Json method ction messages
    @Override
    protected void performAction(String a) {
        System.out.println("Connected with service: " + a);
        FridgeModel fridge = new Gson().fromJson(a, FridgeModel.class);

        if (fridge.getAction() == FridgeModel.serviceAction.STATUS) {
            String message = getStatus();
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.STATUS, message));
            sendBack(json);

            //Increase Tempreature
        } else if (fridge.getAction() == FridgeModel.serviceAction.increaseTemp) {
            increaseTemp();
            String message = (tempIncreaseing) ? "The Fridge tempreature is increasing by 1c!        Tempreature: " + fridgeTemp : "Sorry you cannot increase the tempreature. Fridge is at the maximum tempreature of " + fridgeTemp;
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.increaseTemp, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (tempIncreaseing) ? "The Fridge tempreature is increasing by 1c!        Tempreature: " + fridgeTemp : "Sorry you cannot increase the tempreature. Fridge is at the maximum tempreature of " + fridgeTemp;
            ui.updateArea(serviceMessage);

            //Decrease Tempreature
        } else if (fridge.getAction() == FridgeModel.serviceAction.decreaseTemp) {
            decreaseTemp();
            String message = (tempDecreasing) ? "The Room is cooling down by 1c!       Tempreature: " + fridgeTemp : "Sorry you cannot decrease the tempreature, Fridge is at the minimum tempreature!       Tempreature: " + fridgeTemp;
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.decreaseTemp, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (tempDecreasing) ? "The fridge is decreasing by 1c!        Tempreature: " + fridgeTemp : "Sorry: Fridge is at the max temp of " + fridgeTemp;
            ui.updateArea(serviceMessage);
        } //Turn Lights On
        else if (fridge.getAction() == FridgeModel.serviceAction.turnLightsOff) {
            turnLightsOff();
            String message = (off) ? "The Fridge has been turned off" : "The Fridge is currently off";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.turnLightsOff, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (off) ? "Fridge turned off" : "Fridge is off";
            ui.updateArea(serviceMessage);
        } //Turn Lights Off
        else if (fridge.getAction() == FridgeModel.serviceAction.turnLightsOn) {
            turnLightsOn();
            String message = (on) ? "The Fridge has been turned on" : "The fridge is on";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.turnLightsOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (on) ? "The fridge has turned on" : "Fridge is on";
            ui.updateArea(serviceMessage);
        } //Dispense Ice
        else if (fridge.getAction() == FridgeModel.serviceAction.dispenseIce) {
            dispenseIce();
            String message = (iceDispensing) ? "The ice is dispensing" : "Sorry, Ice is at the maximum level";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.dispenseIce, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (iceDispensing) ? "The ice is dispensing" : "Sorry, Ice is at the maximum level";
            ui.updateArea(serviceMessage);
        } //Lock Ice
        else if (fridge.getAction() == FridgeModel.serviceAction.lockIce) {
            lockIce();
            String message = (lockIce) ? "The ice has locked" : "The ice is already locked";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.lockIce, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (iceDispensing) ? "The ice has locked" : "The ice is already locked";
            ui.updateArea(serviceMessage);
        } //Unlock Ice
        else if (fridge.getAction() == FridgeModel.serviceAction.unlockIce) {
            unlockIce();
            String message = (unlockIce) ? "The ice is unlocked" : "The ice is unlocked";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.unlockIce, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (unlockIce) ? "The ice is unlocked" : "The ice is unlocked";
            ui.updateArea(serviceMessage);
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    //Method Logic
    public void increaseTemp() {
        if (fridgeTemp != maxTemp) {
            tempIncreaseing = true;
            iceDispensing = false;
            fridgeTemp += 1;
        } else {
            tempIncreaseing = false;
        }
    }

    public void decreaseTemp() {
        if (fridgeTemp != minTemp) {
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
        }
    }

    public void turnLightsOn() {
        if (power <= 0) {
            power += 100;
            iceDispensing = false;
        }
    }

    public void dispenseIce() {
        if (iceLevel != maxIceLevel) {
            iceDispensing = true;
            iceLevel += 1;
        } else {
            iceDispensing = false;
        }
    }

    public void lockIce() {
        iceDispensing = false;
        lockIce = true;
    }

    public void unlockIce() {
        iceDispensing = true;
        lockIce = false;
    }

    //Current status of different methods
    @Override
    public String getStatus() {
        String message = "";
        if (iceLevel > 0 && iceLevel < 4) {
            if (iceDispensing = true) {

                message = "Ice Level is " + iceLevel;
                String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.dispenseIce, message));
                System.out.println(json);
                sendBack(json);
            }
        } else if (lockIce == true) {
            message = (lockIce) ? "The ice has locked" : "The ice is already locked";
            String json = new Gson().toJson(new FridgeModel(FridgeModel.serviceAction.lockIce, message));
            System.out.println(json);
            sendBack(json);

        }
        return message;
    }

    //Main Methods
    public static void main(String[] args) {
        new FridgeService("Fridge Service");
    }

}
