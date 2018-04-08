/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author x14380181
 */
import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import clientui.ClientManagerUI;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientManager implements ServiceListener {

    private final ClientManagerUI ui;
    private JmDNS jmdns;
    //private final BedClient client = new BedClient();

    private ArrayList<Client> clients;

    public ClientManager() {
        clients = new ArrayList<>();
        clients.add(new FridgeClient());
        clients.add(new ToasterClient());
        clients.add(new OvenClient());

        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            for (Client client : clients){
            jmdns.addServiceListener(client.getServiceType(), this);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui = new ClientManagerUI(this);
    }

    public void end() {
        try {
            jmdns.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void serviceAdded(ServiceEvent arg0) {
        System.out.println(arg0);
        arg0.getDNS().requestServiceInfo(arg0.getType(), arg0.getName(), 0);
    }

    public void serviceRemoved(ServiceEvent arg0) {
        System.out.println(arg0);
        String type = arg0.getType();
        String name = arg0.getName();
        ServiceInfo newService = null;
        
        for (Client client : clients){
            if (client.getServiceType().equals(type) && client.hasMultiple()) {
                if (client.isCurrent(name)) {
                    ServiceInfo[] a = jmdns.list(type);
                    for (ServiceInfo in : a) {
                        if (!in.getName().equals(name)) {
                            newService = in;
                        }
                    }
                    client.switchService(newService);
                }
                client.remove(name);
            } else if (client.getServiceType().equals(type)) {
                ui.removePanel(client.returnUI());
                client.disable();
                client.initialized = false;
            }
        }
    }

    public void serviceResolved(ServiceEvent arg0) {
        System.out.println(arg0);
        String address = arg0.getInfo().getHostAddress();
        int port = arg0.getInfo().getPort();
        String type = arg0.getInfo().getType();

        for (Client client : clients) {
            if (client.getServiceType().equals(type) && !client.isInitialized()) {
                client.setUp(address, port);
                ui.addPanel(client.returnUI(), client.getName());
                client.setCurrent(arg0.getInfo());
                client.addChoice(arg0.getInfo());
            } else if (client.getServiceType().equals(type)
                    && client.isInitialized()) {
                client.addChoice(arg0.getInfo());

            }
        }
    }

    public static void main(String[] args) {
        new ClientManager();

    }
}
