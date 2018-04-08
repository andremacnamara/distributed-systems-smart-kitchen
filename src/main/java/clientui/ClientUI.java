/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.Client;

/**
 *
 * @author andre
 */
public abstract class ClientUI extends JPanel implements ActionListener{
       private static final long serialVersionUID = 1L;
    protected JComboBox services;
    protected JPanel controls;
    protected JTextArea textAreaStatus;
    protected JScrollPane scroll;
    protected Client p;

    public ClientUI(Client a) {
        p = a;
    }

    public void init() {
        setLayout(null);
        services = new JComboBox();
        services.addActionListener(actionListener);
        add(services);
        services.setBounds(170, 5, 200, 30);
        controls = new JPanel();
        controls.setBounds(10, UIConstants.CONTROLY, UIConstants.COMPONENTWIDTH,
                100);
        controls.setLayout(new FlowLayout());
        controls.setBorder(BorderFactory.createLineBorder(Color.black));
        add(controls);
        textAreaStatus = new JTextArea();
        scroll = new JScrollPane();
        scroll.setViewportView(textAreaStatus);
        add(scroll);
    }

    public void add(JButton[] a) {
        for (JButton in : a) {
            in.addActionListener(this);
            controls.add(in);
        }
    }

    public void addChoices(Vector<String> a) {
        System.out.println("passed to add choices" + a);
        remove(services);
        services = new JComboBox(a);
        services.addActionListener(actionListener);
        services.setBounds(170, 5, 200, 30);
        add(services);
        updateUI();
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JComboBox cb = (JComboBox) actionEvent.getSource();
            String serviceName = (String) cb.getSelectedItem();
            p.switchService(serviceName);
        }
    };

    public void refresh(Vector<String> a) {
        this.removeAll();
        init();
        addChoices(a);
    }

    public void clearArea() {
        textAreaStatus.setText("");
    }

    public void updateArea(String string) {
        if (textAreaStatus.getText().equals("")) {
            textAreaStatus.append(string);
        } else {
            textAreaStatus.append("\n" + string);
        }
    }
}
