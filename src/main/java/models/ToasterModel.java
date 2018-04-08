/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author x14380181
 *
 * Andre MacNamara
 */
public class ToasterModel {

    public enum serviceAction {
        turnToasterOn, turnToasterOff, isToasting, putBreadInToaster, STATUS, toasting;
    }

    private serviceAction action;
    private String message;
    private boolean value;

    public ToasterModel() {
    }

    public ToasterModel(serviceAction action) {
        this.action = action;
    }

    public ToasterModel(serviceAction action, String message) {
        this.action = action;
        this.message = message;
    }

    public ToasterModel(serviceAction action, String message, boolean value) {
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
