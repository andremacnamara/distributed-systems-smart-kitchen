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
public class OvenModel {

    public enum serviceAction {
        increaseTemp, decreaseTemp, turnOvenOff, putFoodInOven, turnOvenOn,  STATUS;
    }

    private serviceAction action;
    private String message;
    private boolean value;

    public OvenModel() {
    }

    public OvenModel(serviceAction action) {
        this.action = action;
    }

    public OvenModel(serviceAction action, String message) {
        this.action = action;
        this.message = message;
    }

    public OvenModel(serviceAction action, String message, boolean value) {
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
