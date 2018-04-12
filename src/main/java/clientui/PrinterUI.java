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
    private JButton putPaperInPrinter;
    private JButton print;
    private JButton finishPrinting;
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
        
        print = new JButton("Print");
        print.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{print});
        
        putPaperInPrinter = new JButton("Add Paper");
        putPaperInPrinter.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{putPaperInPrinter});
        
        finishPrinting = new JButton("Cancel Print");
        finishPrinting.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{finishPrinting});
         
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == turnPrinterOff) {
            printerClientRef.turnPrinterOff();
            turnPrinterOff.setEnabled(false);
            turnPrinterOn.setEnabled(true);
            putPaperInPrinter.setEnabled(false);
            print.setEnabled(false);
        } 
        
        else if (e.getSource() == turnPrinterOn) {
            printerClientRef.turnPrinterOn();
            turnPrinterOff.setEnabled(true);
            turnPrinterOn.setEnabled(false);
            putPaperInPrinter.setEnabled(true);
            print.setEnabled(false);
        }
      else if (e.getSource() == putPaperInPrinter){
            printerClientRef.putPaperInPrinter();
            putPaperInPrinter.setEnabled(false);
            print.setEnabled(true);
        }
      
      else if (e.getSource() == print){
            printerClientRef.Printing();
            print.setEnabled(false);
            putPaperInPrinter.setEnabled(false);
        } 
      
      else if (e.getSource() == finishPrinting){
            printerClientRef.finishPrinting();
            print.setEnabled(false);
            putPaperInPrinter.setEnabled(true);
            finishPrinting.setEnabled(false);
        }
    }
    
}