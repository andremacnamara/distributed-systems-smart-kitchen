/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author x14484252
 *
 * Sean Cowley
 */
public class PrinterModel {

    public enum serviceAction {
       turnPrinterOff, turnPrinterOn,  isPrinting, putPaperInPrinter, printing, finishPrinting,cancelPrinting, STATUS;
    }
 //Getters and Setters
    private serviceAction action;
    private String message;
    private boolean value;

    public PrinterModel() {
    }

    public PrinterModel(serviceAction action) {
        this.action = action;
    }

    public PrinterModel(serviceAction action, String message) {
        this.action = action;
        this.message = message;
    }

    public PrinterModel(serviceAction action, String message, boolean value) {
        this.action = action;
        this.message = message;
        this.value = value;
    }

    public serviceAction getAction() {
        return action;
    }

    public void setAction(serviceAction action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
