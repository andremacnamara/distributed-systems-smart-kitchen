/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

/**
 *
 * @author x14380181
 */
import client.ClientManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ClientManagerUI extends JFrame {
        private final JTabbedPane allPanels;
    private static final long serialVersionUID = -4512962459244007477L;

    public ClientManagerUI(final ClientManager clientManager) {
        super("Kitchen Management");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clientManager.end();
            }
        });
        setResizable(false);
        pack();
        setSize(UIConstants.UIWIDTH, UIConstants.UIWIDTH);
        setLocation(setPosition(this));
        allPanels = new JTabbedPane();
        getContentPane().add(allPanels);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        update(this.getGraphics());
    }

    public static Point setPosition(Component c) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - c.getWidth()) / 2);
        return new Point(x, 0);
    }

    public void addPanel(JPanel a, String name) {
        allPanels.addTab(name, a);
    }

    public void removePanel(JPanel returnUI) {
        allPanels.remove(returnUI);
    }
}
