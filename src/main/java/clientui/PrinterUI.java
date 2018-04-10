/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.PrinterClient;

/**
 *
 * @author x14484252
 */
public class PrinterUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton turnPrinterOn;
    private JButton turnPrinterOff;
    private final PrinterClient printerClientRef;

    public PrinterUI(PrinterClient printerClient) {
        super(printerClient);
        printerClientRef = printerClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        
        turnPrinterOff = new JButton("Turn Printer Off");
        turnPrinterOff.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnPrinterOff});

        turnPrinterOn = new JButton("Turn Printer On");
        turnPrinterOn.setEnabled(true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnPrinterOn});
         
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == turnPrinterOff) {
            printerClientRef.turnPrinterOff();
            turnPrinterOff.setEnabled(false);
            turnPrinterOn.setEnabled(true);
        } 
        
        else if (e.getSource() == turnPrinterOn) {
            printerClientRef.turnPrinterOn();
            turnPrinterOff.setEnabled(true);
            turnPrinterOn.setEnabled(false);
            
        }
    }
}
