import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/*
 * Created on Tevet 5770 
 */

/**
 * @author �����
 */


public class MyActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        JRadioButton butt = (JRadioButton) e.getSource();
        MainStatechart.buttonPressAlert(Integer.parseInt(butt.getName()));
        butt.setSelected(false);
        System.out.println("Button press " + butt.getName());
        //		butt.setEnabled(false);
        //		butt.setSelected(false);
    }

}
