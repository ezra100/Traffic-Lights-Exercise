import java.io.*;
import java.net.*;

/**
 * Created by ttrabels on 6/26/2017.
 */
public class SocketHandler extends Thread {

    public PrintWriter bufferSocketOut;
    Event64 myConnetion;
    String SERVERHOST = "127.0.0.1";
    int DEFAULT_PORT = 770;
    Socket clientSocket = null;
    BufferedReader bufferSocketIn;
    BufferedReader keyBoard;
    String line;

    public SocketHandler(Event64 evClientConn) {
        this.myConnetion = evClientConn;
        setDaemon(true);
        start();
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
            while (true) {
                line = bufferSocketIn.readLine(); // reads a line from the server
                myConnetion.sendEvent(line);
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
