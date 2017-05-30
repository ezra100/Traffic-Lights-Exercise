import javax.swing.JPanel;

/*
 * Created on Tevet 5770 
 */

/**
 * @author �����
 */

public class CarsMaker extends Thread {
    JPanel myPanel;
    int key;
    private CarsLight myLight;

    public CarsMaker(JPanel myPanel, CarsLight myLight, int key) {
        this.myPanel = myPanel;
        this.myLight = myLight;
        this.key = key;
        setDaemon(true);
        start();
    }

    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                sleep(300);
                if (!myLight.shouldCarsStop()) {
                    new CarMoving(myPanel, myLight, key);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
