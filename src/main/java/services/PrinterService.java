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
    private static boolean TurnPrinterOn, TurnPrinterOff ;
    private static boolean PaperInPrinter;
    private static boolean Print;
    private int PrinterOnline;

    public PrinterService(String serviceName) {
        super(serviceName, "_printer._udp.local.");
        power = 0;
        PrinterOnline = 0;
        TurnPrinterOn = false;
        TurnPrinterOff = true;
        PaperInPrinter = false;
        Print = true;
        
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
            String message = (TurnPrinterOff) ? "The Printer has been turned off" : "The Printer is currently offline";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOff, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (TurnPrinterOff) ? "Printer turned off" : "Printer is offline";
            ui.updateArea(serviceMessage);
        }
         
        else if (printer.getAction() == PrinterModel.serviceAction.turnPrinterOn) {
            turnPrinterOn();
            String message = (TurnPrinterOn) ? "The Printer has been turned on" : "The Printer is online";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOn, message));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (TurnPrinterOn) ? "The printer has turned on" : "printer is offline";
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
    
   

    @Override
    public String getStatus() {
        return "The current power level is " + power; 
    }

 public static void main(String[] args){
        new PrinterService("Printer Service");
    }
}
