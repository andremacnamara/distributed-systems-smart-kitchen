/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import servicesui.ServiceUI;

/**
 *
 * @author x14380181
 */
public abstract class Service extends Thread {

    protected String SERVICE_TYPE;
    protected String SERVICE_NAME;
    protected int SERVICE_PORT;
    protected int my_backlog = 5;
    protected ServerSocket my_serverSocket;
    protected JmDNS jmdns;
    protected Socket socket;
    protected String status;
    protected ServiceUI ui;
    protected ServiceInfo info;
    protected final String BAD_COMMAND = "bad Command";
    protected String STATUS_REQUEST = "get_status";
    protected PrintWriter out;
    protected BufferedReader in;

    public Service(String name, String type) {
        this(name, type, "");
    }

    public Service(String name, String type, String location) {
        SERVICE_NAME = name;
        try {
            SERVICE_PORT = findFreePort();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        SERVICE_TYPE = type;
        try {
            my_serverSocket = new ServerSocket(SERVICE_PORT, my_backlog);
        } catch (IOException e) {
            try {
                SERVICE_PORT = findFreePort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            info = ServiceInfo.create(SERVICE_TYPE, SERVICE_NAME, SERVICE_PORT,
                    "params=" + location);
            jmdns.registerService(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    public void deRegister() {
        jmdns.unregisterService(info);
        try {
            this.stop();
            my_serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = my_serverSocket.accept();

                in = new BufferedReader(new InputStreamReader(socket
                        .getInputStream()));

                String msg = in.readLine();
                performAction(msg);
                in.close();
                socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (SecurityException se) {
                se.printStackTrace();
            } finally {
            }
        }
    }

    public String getType() {
        return SERVICE_TYPE;
    }

    public String getServiceName() {
        return SERVICE_NAME;
    }

    public int getPort() {
        return SERVICE_PORT;
    }

    public void sendBack(String a) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(a);
            out.close();
        } catch (IOException e) {
            ui.updateArea("Client not accessible");
        }
    }

    protected abstract void performAction(String a);

    public static int findFreePort() throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();

        return port;
    }

    public abstract String getStatus();
}
