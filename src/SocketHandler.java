import java.io.*;
import java.net.*;

/**
 * Created by ttrabels on 6/26/2017.
 */
public class SocketHandler extends Thread {

    final String SH_PRESS_CODE = "ShPress";
    public PrintWriter bufferSocketOut;
    Event64 myConnetion;
    String SERVERHOST = "127.0.0.1";
    int DEFAULT_PORT = 4550;
    Socket clientSocket = null;
    BufferedReader bufferSocketIn;
    BufferedReader keyBoard;
    String line;
    String clientName;

    public SocketHandler(String clientName) {
        this.clientName = clientName;
    }

    public void run() {
        try {
            // Request to server
            clientSocket = new Socket(SERVERHOST, DEFAULT_PORT);
            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())),
                    true);
            bufferSocketOut.println(clientName);
            while (true) {
                line = bufferSocketIn.readLine(); // reads a line from the server
                if (line.equals(SH_PRESS_CODE)) {
                    MainStatechart.shbatPress();
                } else {
                    MainStatechart.buttonPressAlert(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e2) {
            }
        }
    }
}
