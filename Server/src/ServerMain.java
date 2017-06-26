/**
 * Created by User on 26-Jun-17.
 */
public class ServerMain {

    public static void main(String[] args) {
        ServerPanel serverPanel = new ServerPanel();
        Listener listener = new Listener();
        listener.start();
        serverPanel.setVisible(true);
    }
}
