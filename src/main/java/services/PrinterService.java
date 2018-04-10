/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import models.PrinterModel;
import servicesui.ServiceUI;

/**
 *
 * @author x14484252
 */
public class PrinterService extends Service {
    //Printer Service Branch

    private int power;
    private static boolean on, off ;

    public PrinterService(String serviceName) {
        super(serviceName, "_printer._udp.local.");
        power = 0;
        on = false;
        off = true;
        ui = new ServiceUI(this, serviceName);
    }

    @Override
    protected void performAction(String a) {
        System.out.println("Connected with service: " + a);
        PrinterModel printer = new Gson().fromJson(a, PrinterModel.class);

        if (printer.getAction() == PrinterModel.serviceAction.STATUS) {
            String message = getStatus();
            String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.STATUS, message));
            sendBack(json);
        } 
        
         else if (printer.getAction() == PrinterModel.serviceAction.turnPrinterOff) {
            turnPrinterOff();
            String message = (off) ? "The Printer has been turned off" : "The Printer is currently off";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOff, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (off) ? "Printer turned off" : "Printer is off";
            ui.updateArea(serviceMessage);
        }
         
        else if (printer.getAction() == PrinterModel.serviceAction.turnPrinterOn) {
            turnPrinterOn();
            String message = (on) ? "The Printer has been turned on" : "The Printer is on";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (on) ? "The printer has turned on" : "printer is on";
            ui.updateArea(serviceMessage);
        }
        else {
            sendBack(BAD_COMMAND + " - " +a);
        }
    }
    
    

    public void turnPrinterOff() {
        if (power >= 0) {
            power = 0;
            System.out.println("Power Off. Printer is off");
        }
    }

    public void turnPrinterOn() {
        if (power <= 0) {
            power += 100;
            System.out.println("The power level is" +power + ". The printer is on.");
        }
    }
    
    
    public static void main(String[] args){
        new PrinterService("Printer Service");
    }

    @Override
    public String getStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
