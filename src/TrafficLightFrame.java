import javax.swing.*;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770 
 */

/**
 * @author �����
 */

public class TrafficLightFrame extends JFrame {
    @SuppressWarnings("FieldCanBeLocal")
    private final int WIDTH = 800, HEIGHT = 750;
    TrafficLightPanel myPanel;

    public TrafficLightFrame(String h, StreetLight[] lights) {
        super(h);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(90, -15);
        myPanel = new TrafficLightPanel(lights);
        add(myPanel);
        pack();
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);
    }
}

