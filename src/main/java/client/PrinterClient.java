/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.PrinterUI;
import models.PrinterModel;
import com.google.gson.Gson;


/**
 *
 * @author x14484252
 */

/* 
*/
public class PrinterClient extends Client {
    private String turnPrinterOn  = "turnPrinterOn";
    private String turnPrinterff = "turnPrinterOff";
    private boolean isLoading  = false;
   
    /*
    * Constructor
    */
    
    public PrinterClient(){
        super();
        serviceType = "_printer._udp.local.";
        ui = new PrinterUI(this);
        name = "Printer";
    }
    
    public void turnPrinterOff(){
        String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOff));
        String message = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(message, PrinterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(printer.getAction() == PrinterModel.serviceAction.turnPrinterOff){
            isLoading = printer.getValue();
            ui.updateArea(printer.getMessage());
        }
    }
    
    public void turnPrinterOn(){
        String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOn));
        String message = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(message, PrinterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(printer.getAction() == PrinterModel.serviceAction.turnPrinterOn){
            isLoading = printer.getValue();
            ui.updateArea(printer.getMessage());
        }
    }
    
  
    
    @Override
    public void updatePoll(String message) {
        if (message.equals("Printer is 100% loaded")) {
            isLoading = false;
        }
    }
    @Override
    public void disable() {
        super.disable();
        ui = new PrinterUI(this);
        isLoading = false;
    }
    
    
}
