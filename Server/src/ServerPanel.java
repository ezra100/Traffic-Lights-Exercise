import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon Jun 26 20:15:17 IDT 2017
 */


/**
 * @author Ezra Steinmetz
 */
public class ServerPanel extends JFrame {
    final String pathToTLJar = "\"C:\\Users\\User\\workspace\\TrafficLights\\out\\artifacts\\TrafficLights_jar\\TrafficLights.jar\"";
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Ezra Steinmetz
    private JLabel label1;
    private JLabel label2;
    private JComboBox cboSockets;
    private JButton btnShabbos;
    private JButton btnDisableCars;
    private JButton btnEnableCars;
    private JButton btnManualCars;
    private JButton btnDisconnect;
    private JButton btnGenCar;
    private JButton btnConnect;
    private JButton btnSetPosition;
    private JButton btnFreeze;
    private JComboBox comboBox1;
    private JLabel label3;
    private JButton startInstanceBtn;
    private JTextField instanceName;
    public ServerPanel() {
        initComponents();
    }

    private void thisWindowClosing(WindowEvent e) {
        // TODO add your code here
    }

    private void cboSocketsActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnShabbosActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnDisableCarsActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnEnableCarsActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnManualCarsActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnDisconnectActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnGenCarActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnConnectActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnSetPositionActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnFreezeActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void startInstanceActionPerformed(ActionEvent e) {
        String instanceNameText = instanceName.getText();

        try {
            Runtime.getRuntime().exec("java -jar " + pathToTLJar + " " + instanceNameText);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Ezra Steinmetz
        label1 = new JLabel();
        label2 = new JLabel();
        cboSockets = new JComboBox();
        btnShabbos = new JButton();
        btnDisableCars = new JButton();
        btnEnableCars = new JButton();
        btnManualCars = new JButton();
        btnDisconnect = new JButton();
        btnGenCar = new JButton();
        btnConnect = new JButton();
        btnSetPosition = new JButton();
        btnFreeze = new JButton();
        comboBox1 = new JComboBox();
        label3 = new JLabel();
        startInstanceBtn = new JButton();
        instanceName = new JTextField();

        //======== this ========
        setTitle("Main Control");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Control");
        label1.setFont(new Font("Tahoma", Font.PLAIN, 24));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label1);
        label1.setBounds(60, 15, 395, 55);

        //---- label2 ----
        label2.setText("Junction:");
        contentPane.add(label2);
        label2.setBounds(60, 65, 60, 35);

        //---- cboSockets ----
        cboSockets.addActionListener(e -> cboSocketsActionPerformed(e));
        contentPane.add(cboSockets);
        cboSockets.setBounds(130, 70, 130, 25);

        //---- btnShabbos ----
        btnShabbos.setText("Shabbos Button");
        btnShabbos.addActionListener(e -> btnShabbosActionPerformed(e));
        contentPane.add(btnShabbos);
        btnShabbos.setBounds(35, 155, 145, 80);

        //---- btnDisableCars ----
        btnDisableCars.setText("Disable Cars");
        btnDisableCars.addActionListener(e -> btnDisableCarsActionPerformed(e));
        contentPane.add(btnDisableCars);
        btnDisableCars.setBounds(180, 196, 120, 40);

        //---- btnEnableCars ----
        btnEnableCars.setText("Enable Cars");
        btnEnableCars.addActionListener(e -> btnEnableCarsActionPerformed(e));
        contentPane.add(btnEnableCars);
        btnEnableCars.setBounds(180, 155, 120, 40);

        //---- btnManualCars ----
        btnManualCars.setText("Manual Cars");
        btnManualCars.addActionListener(e -> btnManualCarsActionPerformed(e));
        contentPane.add(btnManualCars);
        btnManualCars.setBounds(180, 237, 120, 40);

        //---- btnDisconnect ----
        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(e -> btnDisconnectActionPerformed(e));
        contentPane.add(btnDisconnect);
        btnDisconnect.setBounds(302, 265, 155, 53);

        //---- btnGenCar ----
        btnGenCar.setText("Generate Car");
        btnGenCar.addActionListener(e -> btnGenCarActionPerformed(e));
        contentPane.add(btnGenCar);
        btnGenCar.setBounds(180, 278, 120, 40);

        //---- btnConnect ----
        btnConnect.setText("Connect");
        btnConnect.addActionListener(e -> btnConnectActionPerformed(e));
        contentPane.add(btnConnect);
        btnConnect.setBounds(302, 213, 155, 49);

        //---- btnSetPosition ----
        btnSetPosition.setText("Set Position");
        btnSetPosition.addActionListener(e -> btnSetPositionActionPerformed(e));
        contentPane.add(btnSetPosition);
        btnSetPosition.setBounds(302, 155, 155, 55);

        //---- btnFreeze ----
        btnFreeze.setText("Freeze");
        btnFreeze.addActionListener(e -> btnFreezeActionPerformed(e));
        contentPane.add(btnFreeze);
        btnFreeze.setBounds(35, 235, 145, 83);
        contentPane.add(comboBox1);
        comboBox1.setBounds(131, 105, 135, 25);

        //---- label3 ----
        label3.setText("Button No.:");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(50, 110), label3.getPreferredSize()));

        //---- startInstanceBtn ----
        startInstanceBtn.setText("Start instance");
        startInstanceBtn.addActionListener(e -> startInstanceActionPerformed(e));
        contentPane.add(startInstanceBtn);
        startInstanceBtn.setBounds(325, 110, 115, 35);
        contentPane.add(instanceName);
        instanceName.setBounds(325, 80, 116, instanceName.getPreferredSize().height);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(535, 375);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
