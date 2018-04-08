package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.ToasterClient;

/**
 *
 * @author andre
 */
public class ToasterUI extends ClientUI{
    private static final long serialVersionUID = -5318589393275157185L;
    private JButton turnToasterOn;
    private JButton turnToasterOff;
    private JButton putBreadInToaster;
    private JButton toast;
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
        
        toast = new JButton("Toast");
        toast.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{toast});
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
            toast.setEnabled(true);
        } else if (e.getSource() == toast){
            toasterClientRef.Toasting();
            toast.setEnabled(false);
            putBreadInToaster.setEnabled(false);
        }
    }
    
}

