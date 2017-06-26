/**
 * Created by User on 26-Jun-17.
 */

import com.sun.javafx.scene.web.Debugger;
import jdk.nashorn.internal.runtime.Debug;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;


public class Listener extends Thread {

    final int listenerPort = 4550;
    public PrintWriter bufferSocketOut;
    ServerSocket serverSocket;
    BufferedReader bufferSocketIn;
    Map<String, Socket> socketMap = new HashMap<>();

    public Listener() {
        super("Listener thread");
        // start();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(listenerPort);

            while (true) {
                Socket soc = serverSocket.accept();
                bufferSocketIn = new BufferedReader(new InputStreamReader(
                        soc.getInputStream()));
                bufferSocketOut = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(soc.getOutputStream())),
                        true);
                String socketName = bufferSocketIn.readLine();
                assert !socketMap.containsKey(socketName);
                socketMap.put(socketName, soc);
                System.out.println("added socket " + socketName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
