package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.ToasterClient;

/**
 *
 * @author andre
 */

/*
 *
 * @reference Dominic Carr 
 * https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 */


public class ToasterUI extends ClientUI{
    private static final long serialVersionUID = -5318589393275157185L;
    private JButton turnToasterOn;
    private JButton turnToasterOff;
    private JButton putBreadInToaster;
    private JButton toast;
    private JButton finishToasting;
    
    private final ToasterClient toasterClientRef;

    public ToasterUI(ToasterClient toasterClient) {
        super(toasterClient);
        toasterClientRef = toasterClient;
        init();
    }
    
    @Override
    public void init(){
        super.init();
        turnToasterOn = new JButton("Turn Toaster On");
        turnToasterOn.setEnabled(true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnToasterOn});
        
        turnToasterOff = new JButton("Turn Toaster off");
        turnToasterOff.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnToasterOff});
        
        putBreadInToaster = new JButton("Add Bread");
        putBreadInToaster.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{putBreadInToaster});
        
        toast = new JButton("Toast Bread");
        toast.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{toast});
        
        finishToasting = new JButton("Stop Toasting");
        finishToasting.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{finishToasting});
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == turnToasterOn){
            toasterClientRef.turnToasterOn();
            //Enable all buttons if on = true
            //Leave off = false
            turnToasterOff.setEnabled(true);
            turnToasterOn.setEnabled(false);
            putBreadInToaster.setEnabled(true);
            toast.setEnabled(false);
        }
        else if (e.getSource() == turnToasterOff){
            toasterClientRef.turnToasterOff();
            //
            turnToasterOff.setEnabled(false);
            turnToasterOn.setEnabled(true);
            putBreadInToaster.setEnabled(false);
            toast.setEnabled(false);
        } else if (e.getSource() == putBreadInToaster){
            toasterClientRef.putBreadInToaster();
            putBreadInToaster.setEnabled(false);
            toast.setEnabled(true);
        } else if (e.getSource() == toast){
            toasterClientRef.Toasting();
            toast.setEnabled(false);
            putBreadInToaster.setEnabled(false);
           
            finishToasting.setEnabled(true);
        } else if (e.getSource() == finishToasting){
            toasterClientRef.finishToasting();
            toast.setEnabled(false);
            putBreadInToaster.setEnabled(true);
            finishToasting.setEnabled(false);
        }
    }
    
}

