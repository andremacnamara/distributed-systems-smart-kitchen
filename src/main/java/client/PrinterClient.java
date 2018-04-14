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
references:
Dominic Carr https://moodle.ncirl.ie/mod/resource/view.php?id=53562
*/
public class PrinterClient extends Client {
    private String turnPrinterOn  = "turnPrinterOn";
    private String turnPrinterff = "turnPrinterOff";
    private String putPaperInPrinter = "putPaperInPrinter";
    private boolean isPrinting  = false;
   
    /*
    * Constructor
    */
    
    public PrinterClient(){
        super();
        serviceType = "_printer._udp.local.";
        ui = new PrinterUI(this);
        name = "Printer";
    }
    /* Converts Json   */
    public void turnPrinterOff(){
        String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOff));
        String message = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(message, PrinterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(printer.getAction() == PrinterModel.serviceAction.turnPrinterOff){
            isPrinting = printer.getValue();
            ui.updateArea(printer.getMessage());
        }
    }
    
    public void turnPrinterOn(){
        String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.turnPrinterOn));
        String message = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(message, PrinterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(printer.getAction() == PrinterModel.serviceAction.turnPrinterOn){
            isPrinting = printer.getValue();
            ui.updateArea(printer.getMessage());
        }
    }
     public void Printing(){
        String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.printing));
        String message = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(message, PrinterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(printer.getAction() == PrinterModel.serviceAction.printing){
            isPrinting = printer.getValue();
            ui.updateArea(printer.getMessage());
        }
    }
      public void putPaperInPrinter(){
        String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.putPaperInPrinter));
        String message = sendMessage(json);
        PrinterModel toaster = new Gson().fromJson(message, PrinterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(toaster.getAction() == PrinterModel.serviceAction.putPaperInPrinter){
            isPrinting = toaster.getValue();
            ui.updateArea(toaster.getMessage());
        }
    }
    
    public void finishPrinting(){
        String json = new Gson().toJson(new PrinterModel(PrinterModel.serviceAction.finishPrinting));
        String message = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(message, PrinterModel.class);
        System.out.println("Client Recieved " + json);
        
        if(printer.getAction() == PrinterModel.serviceAction.finishPrinting){
            isPrinting = printer.getValue();
            ui.updateArea(printer.getMessage());
        }
    }
   
   
    @Override
    public void updatePoll(String message) {
        if (message.equals("Print Successful")) {
            isPrinting = false;
        }
    }
    @Override
    public void disable() {
        super.disable();
        ui = new PrinterUI(this);
        isPrinting = false;
    }
    
    
}
