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

/*
 *http://www.vogella.com/tutorials/JavaLibrary-Gson/article.html
 *Vogella
 */
public class FridgeModel {

    //All actions avaliable
    public enum serviceAction {
        increaseTemp, decreaseTemp, turnLightsOff, turnLightsOn, dispenseIce, lockIce, unlockIce, STATUS;
    }

    //Action, message printed out to user, and value.
    //Getters and Setters
    private serviceAction action;
    private String message;
    private boolean value;

    public FridgeModel() {
    }

    public FridgeModel(serviceAction action) {
        this.action = action;
    }

    public FridgeModel(serviceAction action, String message) {
        this.action = action;
        this.message = message;
    }

    public FridgeModel(serviceAction action, String message, boolean value) {
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
