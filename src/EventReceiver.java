import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by User on 18-Jun-17.
 */
public class EventReceiver extends Thread {
    final String host = "localhost";
    final int port = 8090;

    MainStatechart receiver;

    EventReceiver(MainStatechart receiver) {
        this.receiver = receiver;

    }

    public void run() {
        Socket socket;
        try {
            socket = new Socket(host, port);

            OutputStream os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
