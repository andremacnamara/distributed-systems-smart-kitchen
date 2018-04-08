/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.OvenClient;

/**
 *
 * @author x14484252
 */
public class OvenUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton increase;
    private JButton decrease;
    private JButton turnLightsOn;
    private JButton turnLightsOff;
    private final OvenClient ovenClientRef;

    public OvenUI(OvenClient ovenClient) {
        super(ovenClient);
        ovenClientRef = ovenClient;
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
        
        turnLightsOff = new JButton("Turn Oven Off");
        turnLightsOff.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnLightsOff});

        turnLightsOn = new JButton("Turn Oven On");
        turnLightsOn.setEnabled(true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnLightsOn});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increase) {
            ovenClientRef.increaseTemp();
        } else if (e.getSource() == decrease) {
            ovenClientRef.decreaseTemp();
        } else if (e.getSource() == turnLightsOff) {
            ovenClientRef.turnLightsOff();
            increase.setEnabled(false);
            decrease.setEnabled(false);
            turnLightsOff.setEnabled(false);
            turnLightsOn.setEnabled(true);
        } else if (e.getSource() == turnLightsOn) {
            ovenClientRef.turnLightsOn();
            increase.setEnabled(true);
            decrease.setEnabled(true);
            turnLightsOff.setEnabled(true);
            turnLightsOn.setEnabled(false);
        }
    }
}
