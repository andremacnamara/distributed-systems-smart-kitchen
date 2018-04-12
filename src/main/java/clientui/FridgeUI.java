/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.FridgeClient;

/**
 *
 * @author x14380181
 */

/*
 *
 * @reference Dominic Carr 
 * https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 */


public class FridgeUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton increase;
    private JButton decrease;
    private JButton turnLightsOn;
    private JButton turnLightsOff;
    private JButton dispenseIce;
    private JButton lockIce;
    private JButton unlockIce;
    private final FridgeClient fridgeClientRef;

    public FridgeUI(FridgeClient fridgeClient) {
        super(fridgeClient);
        fridgeClientRef = fridgeClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        increase = new JButton("Increase");
        increase.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{increase});

        decrease = new JButton("Decrease");
        decrease.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{decrease});
        
        turnLightsOff = new JButton("Turn Fridge Off");
        turnLightsOff.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnLightsOff});

        turnLightsOn = new JButton("Turn Fridge On");
        turnLightsOn.setEnabled(true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnLightsOn});
        
        dispenseIce = new JButton("Dispense Ice");
        dispenseIce.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{dispenseIce});
        
        lockIce = new JButton("Lock Ice");
        lockIce.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{lockIce});
        
        unlockIce = new JButton("Unlock Ice");
        unlockIce.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{unlockIce});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increase) {
            fridgeClientRef.increaseTemp();
        } else if (e.getSource() == decrease) {
            fridgeClientRef.decreaseTemp();
        } else if (e.getSource() == turnLightsOff) {
            fridgeClientRef.turnLightsOff();
            increase.setEnabled(false);
            decrease.setEnabled(false);
            lockIce.setEnabled(false);
            turnLightsOff.setEnabled(false);
            turnLightsOn.setEnabled(true);
        } else if (e.getSource() == turnLightsOn) {
            fridgeClientRef.turnLightsOn();
            increase.setEnabled(true);
            decrease.setEnabled(true);
            turnLightsOff.setEnabled(true);
            dispenseIce.setEnabled(true);
            turnLightsOn.setEnabled(false);
            lockIce.setEnabled(true);
        } else if (e.getSource() == dispenseIce){
            fridgeClientRef.dispenseIce();
        } else if (e.getSource() == lockIce){
            fridgeClientRef.lockIce();
            lockIce.setEnabled(false);
            unlockIce.setEnabled(true);
            dispenseIce.setEnabled(false);
        } else if (e.getSource() == unlockIce){
            fridgeClientRef.unlockIce();
            lockIce.setEnabled(true);
            unlockIce.setEnabled(false);
            dispenseIce.setEnabled(true);
        }
    }
}
