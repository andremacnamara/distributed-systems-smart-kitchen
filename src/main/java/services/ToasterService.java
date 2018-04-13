package services;

import com.google.gson.Gson;
import java.util.Timer;
import java.util.TimerTask;
import models.ToasterModel;
import servicesui.ServiceUI;

/**
 *
 * @author andre
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
public class ToasterService extends Service {

    private int power;
    private int breadLevel;
    private int toastLevelPercent;
    private Timer timer;
    private static boolean turnToasterOn, isToasting, turnToasterOff, breadInToaster, toasting;

    public ToasterService(String serviceName) {
        super(serviceName, "_toaster._udp.local.");
        //Toaster Starts Off
        timer = new Timer();
        turnToasterOn = false;
        turnToasterOff = true;
        breadInToaster = false;
        isToasting = false;
        breadLevel = 0;
        toasting = false;
        toastLevelPercent = 0;

        //
        ui = new ServiceUI(this, serviceName);
    }

    //  Gson/Json 
    @Override
    protected void performAction(String a) {
        System.out.println("Connected with service: " + a);
        ToasterModel toaster = new Gson().fromJson(a, ToasterModel.class);

        if (toaster.getAction() == ToasterModel.serviceAction.STATUS) {
            String message = getStatus();
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.STATUS, message));
            sendBack(json);

            //Turn toaster on
        } else if (toaster.getAction() == ToasterModel.serviceAction.turnToasterOn) {
            turnToasterOn();
            String message = (turnToasterOn) ? "The Toaster has been turned On" : "The Toaster is currently On";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.turnToasterOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (turnToasterOn) ? "Toaster turned on!" : "Toaster is on!";
            ui.updateArea(serviceMessage);

            //Turn toaster off
        } else if (toaster.getAction() == ToasterModel.serviceAction.turnToasterOff) {
            turnToasterOff();
            String message = (turnToasterOff) ? "The Toaster has been turned Off" : "The Toaster is currently Off";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.turnToasterOff, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (turnToasterOff) ? "Toaster turned Off" : "Toaster is Off";
            ui.updateArea(serviceMessage);

            //put bread in toaster
        } else if (toaster.getAction() == ToasterModel.serviceAction.putBreadInToaster) {
            putBreadInToaster();
            String message = (breadInToaster) ? "The bread is in the toaster" : "There is currently bread in the toaster";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.putBreadInToaster, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (breadInToaster) ? "The bread is in the toaster" : "There is currently bread in the toaster";
            ui.updateArea(serviceMessage);

            //Toast
        } else if (toaster.getAction() == ToasterModel.serviceAction.toasting) {
            //Instance of time
            timer = new Timer();
            timer.schedule(new ToastTimer(), 100, 1000);
            String message = (toasting) ? "The bread is toasting   tempreature is " + toastLevelPercent : "The bread is currently toasting   tempreature is " + toastLevelPercent;
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.toasting, message, toasting));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (toasting) ? "The bread is toasting   tempreature is " + toastLevelPercent : "The bread is currently toasting    tempreature is " + toastLevelPercent;
            ui.updateArea(serviceMessage);
        } //Stop toasting
        else if (toaster.getAction() == ToasterModel.serviceAction.finishToasting) {
            finishToasting();
            String message = (toasting) ? "The bread is finished toasting" : "The final toast tempreature is " + toastLevelPercent + "\nPlease take your toast";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.finishToasting, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (toasting) ? "The bread is finished toasting" : "The final toast tempreature is " + toastLevelPercent + "Please take your toast";
            ui.updateArea(serviceMessage);
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    //Timer for the toast
    class ToastTimer extends TimerTask {

        @Override
        public void run() {
            isToasting = true;
            if (toastLevelPercent < 100 && breadLevel > 1) {
                toasting = true;
                toastLevelPercent += 10;
            } else {
                toasting = false;
            }
        }
    }

    public void turnToasterOn() {
        if (power <= 0) {
            power += 100;
        }
    }

    public void turnToasterOff() {
        if (power >= 0) {
            power = 0;
            breadLevel = 0;
        }
    }

    public void putBreadInToaster() {
        if (breadLevel <= 0) {
            breadInToaster = true;
            breadLevel = 4;
            toastLevelPercent = 0;
        } else {
            breadInToaster = false;
        }
    }

    public void finishToasting() {
        toasting = false;
        timer.cancel();
        isToasting = false;
        breadLevel = 0;
    }

    @Override
    public String getStatus() {
        String message = "";

        //When toasting is ready to start. Not on and there is bread in toaster
        if (toastLevelPercent == 0 && breadLevel > 0) {
            message = "Bread is ready to Toast!";
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.toasting, message, toasting));
            System.out.println(json);
            sendBack(json);
        } //If bread is roasting, and the toasting is happening
        else if (toastLevelPercent > 0 && toastLevelPercent < 100) {
            if ((toasting = true) && (isToasting = true)) {
                message = "Bread is " + toastLevelPercent + " degrees celsius";
                String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.toasting, message, toasting));
                System.out.println(json);
                sendBack(json);
            } else if (toasting = false) {
                return null;
            }
        } //If temp it 100, and obviously the user didn't cancel earler.
        else if (toastLevelPercent >= 100) {
            message = "The bread is toasted" + toastLevelPercent;
            String json = new Gson().toJson(new ToasterModel(ToasterModel.serviceAction.toasting, message, toasting));
            System.out.println(json);
            sendBack(json);
            toasting = false;
            breadLevel = 0;

            if (isToasting) {
                timer.cancel();
                isToasting = false;
            }
        }

        return message;
    }

    public static void main(String[] args) {
        new ToasterService("ToasterService");
    }

}
