import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Created on Tevet 5770 
 */

/**
 * @author �����
 */

public class CarMoving extends Thread {
    JLabel myLabel;
    JPanel myPanel;
    int x, dx;
    int y, dy;
    ImageIcon imageIcon;
    boolean first2 = true;
    private CarsLight myLight;
    private int key;

    public CarMoving(JPanel myPanel, CarsLight myLight, int key) {
        this.myPanel = myPanel;
        this.myLight = myLight;
        this.key = key;
        setCarLocationAndMooving();
        imageIcon = getImageIcon();
        myLabel = new JLabel(imageIcon);
        myLabel.setOpaque(false);
        myPanel.add(myLabel);
        setDaemon(true);
        start();
    }

    private void setCarLocationAndMooving() {
        switch (key) {
            case 1: //YA to KN
                x = 850;
                dx = -100;
                y = 70;
                dy = 0;
                break;
            case 2: //Farb to KN p1
                x = 500;
                dx = 0;
                y = 720;
                dy = -50;
                break;
            case 3: //KN to Farb
                x = -30;
                dx = 35;
                y = 405;
                dy = 22;
                break;
            case 4: //KN to YA
                x = -30;
                dx = 50;
                y = 390;
                dy = 0;
                break;

            case 5: //Farb to KN p2
                dx = -50;
                dy = 0;
                break;

            case 6:
                x = 850;
                dx = -100;
                y = 130;
                dy = 0;
                break;
            case 7:
                dx = 0;
                dy = 50;
                break;
            default:
                x = 900;
                dx = -50;
                y = 100;
                dy = 0;
                break;
        }
    }

    public void run() {
        myLabel.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        while (!finish()) {
            if (!(myLight.shouldCarsStop() && toStop())) {
                x += dx;
                y += dy;
                myLabel.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myPanel.repaint();
        }

    }

    private boolean finish() {
        switch (key) {
            case 1:
                return x < -20;
            case 2:
                if (y > 180)
                    return false;
                else {
                    key = 5;
                    setCarLocationAndMooving();
                    imageIcon = getImageIcon();
                    myLabel.removeAll();
                    myLabel.setIcon(imageIcon);
                    myLabel.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
                    return false;
                }
            case 3:
                if (y > 600) {
                    dx = 0;
                    dy = 30;
                }
                return y > 800;
            case 4:
                return x > 800;
            case 5:
                return x < -20;
            case 6:
                if (x < 400) {
                    key = 7;
                    setCarLocationAndMooving();
                    imageIcon = getImageIcon();

                    myLabel.removeAll();
                    myLabel.setIcon(imageIcon);
                    myLabel.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
                    return false;
                }
                return false;
            case 7:
                return y > 800;
        }
        return false;
    }

    private boolean toStop() {
        switch (key) {
            case 1:
            case 6:
                return x > 550;
            case 2:
                return y > 530;
            case 3:
                return x < 100;
            case 4:
                return x <= 150;

        }
        return false;
    }

    private ImageIcon getImageIcon() {
        switch (key) {
            case 1:
            case 6:
                return new ImageIcon("Images/left.gif");
            case 2:
                return new ImageIcon("Images/up.gif");
            case 3:
                return new ImageIcon("Images/right.gif");
            case 4:
                return new ImageIcon("Images/right.gif");
            case 5:
                return new ImageIcon("Images/upLeft.gif");
            case 7:
                return new ImageIcon("Images/down.gif");
            default:
                break;
        }
        return null;
    }


}
