/**
 * Created by User on 26-Jun-17.
 */

import java.io.IOException;
import java.net.*;


public class Server extends Thread {
    final int listenerPort = 4550;
    ServerSocket serverSocket;

    public void run() {
        try {
            serverSocket = new ServerSocket(listenerPort);
            while (true) {
                Socket soc = serverSocket.accept();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
