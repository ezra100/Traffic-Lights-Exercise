/*
 * Created by JFormDesigner on Wed May 30 18:06:46 IDT 2012
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Roee Sefi
 */
@SuppressWarnings("serial")
public class ServerPanel extends JFrame {
    public Boolean isControlled = false;
    public Boolean isAlive;
    Integer currIndex;
    ExtendedSocket currSocket;
    private List<ExtendedSocket> mySockets;
    private List<Event64> myJunctions;
    private List<CarState> carState; // "Cars Manual" or not
    private List<Boolean> isFrozen; // Is junction frozen
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

    public ServerPanel() {
        initComponents();
        isAlive = true;
        mySockets = new ArrayList<>();
        myJunctions = new ArrayList<>();
        carState = new ArrayList<>();
        isFrozen = new ArrayList<>();
        currSocket = null;
        lockButtons();
        btnDisconnect.setEnabled(false);

        pack();
        setVisible(true);
    }

    @SuppressWarnings({"unchecked"})
    public void AddSocket(Socket newSocket, Dialog770 newDialog) {
        ExtendedSocket newExSocket = new ExtendedSocket("Junction " + mySockets.size(),
                newSocket, newDialog);
        this.mySockets.add(newExSocket);
        this.myJunctions.add(newDialog.evJunction);
        carState.add(CarState.ENABLED);
        isFrozen.add(false);
        cboSockets.addItem(newExSocket);

        if (this.mySockets.size() >= 2)
            btnConnect.setEnabled(true);
        else if (this.mySockets.size() >= 1) {
            btnShabbos.setEnabled(true);
            btnFreeze.setEnabled(true);
            btnDisableCars.setEnabled(true);
            btnManualCars.setEnabled(true);
            btnSetPosition.setEnabled(true);
        }
    }

    private void cboSocketsActionPerformed(ActionEvent e) {
        currIndex = cboSockets.getSelectedIndex();
        currSocket = mySockets.get(currIndex);

        updateButtons();
    }

    private void updateButtons() {
        switch (carState.get(currIndex)) {
            case ENABLED:
                btnEnableCars.setEnabled(false);
                btnDisableCars.setEnabled(true);
                btnManualCars.setEnabled(true);
                btnGenCar.setEnabled(false);
                break;
            case DISABLED:
                btnEnableCars.setEnabled(true);
                btnDisableCars.setEnabled(false);
                btnManualCars.setEnabled(true);
                btnGenCar.setEnabled(false);
                break;
            case MANUAL:
                btnEnableCars.setEnabled(true);
                btnDisableCars.setEnabled(true);
                btnManualCars.setEnabled(false);
                btnGenCar.setEnabled(true);
                break;
        }
        if (isFrozen.get(currIndex)) {
            btnFreeze.setText("UnFreeze");
        } else {
            btnFreeze.setText("Freeze");
        }
    }

    private void btnShabbosActionPerformed(ActionEvent e) {
        currSocket.dialog.bufferSocketOut.println("evShabbos");
    }

    private void btnFreezeActionPerformed(ActionEvent e) {
        currSocket.dialog.bufferSocketOut.println("evFreeze");
        isFrozen.set(currIndex, !isFrozen.get(currIndex));
        updateButtons();
    }

    private void btnEnableCarsActionPerformed(ActionEvent e) {
        currSocket.dialog.bufferSocketOut.println("evEnableCars");
        carState.set(currIndex, CarState.ENABLED);
        updateButtons();
    }

    private void btnDisableCarsActionPerformed(ActionEvent e) {
        currSocket.dialog.bufferSocketOut.println("evDisableCars");
        carState.set(currIndex, CarState.DISABLED);
        updateButtons();
    }

    private void btnManualCarsActionPerformed(ActionEvent e) {
        currSocket.dialog.bufferSocketOut.println("evManualCars");
        carState.set(currIndex, CarState.MANUAL);
        updateButtons();
    }

    private void btnGenCarActionPerformed(ActionEvent e) {
        String op;
        op = JOptionPane.showInputDialog("Plase enter ramzor(0-3)-car_key:");
        Pattern p = Pattern.compile("(\\d)-(\\d+)");
        Matcher m = p.matcher(op);
        if (m.find())
            currSocket.dialog.bufferSocketOut.println("evGenCar" + m.group(0));
    }

    private void btnSetPositionActionPerformed(ActionEvent e) {
        String op;
        op = JOptionPane.showInputDialog("Plase enter position(0-Left, 1-Middle, 2-Right, 3-Normal):");
        switch (op) {
            case "0":
                currSocket.dialog.bufferSocketOut.println("evPosLeft");
                break;
            case "1":
                currSocket.dialog.bufferSocketOut.println("evPosMid");
                break;
            case "2":
                currSocket.dialog.bufferSocketOut.println("evPosRight");
                break;
            case "3":
                switch (carState.get(currIndex)) {
                    case ENABLED:
                        currSocket.dialog.bufferSocketOut.println("evEnableCars");
                        break;
                    case DISABLED:
                        currSocket.dialog.bufferSocketOut.println("evDisableCars");
                        break;
                    case MANUAL:
                        currSocket.dialog.bufferSocketOut.println("evManualCars");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void btnConnectActionPerformed(ActionEvent e) {
        lockButtons();
        connectJunctions();

        Controller.theInstance(mySockets, myJunctions);
        isControlled = true;
    }

    private void btnDisconnectActionPerformed(ActionEvent e) {
        isControlled = false;
        disconnectJunctions();
        Controller.instance.stop = true; // Kill the process
        try {
            Controller.instance.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        unlockButtons();
    }

    private void lockButtons() {
        btnShabbos.setEnabled(false);
        btnFreeze.setEnabled(false);
        btnEnableCars.setEnabled(false);
        btnDisableCars.setEnabled(false);
        btnManualCars.setEnabled(false);
        btnGenCar.setEnabled(false);
        btnSetPosition.setEnabled(false);
        btnConnect.setEnabled(false);
        btnDisconnect.setEnabled(true);
    }

    private void unlockButtons() {
        btnShabbos.setEnabled(true);
        btnFreeze.setEnabled(true);
        updateButtons();
        btnSetPosition.setEnabled(true);
        btnConnect.setEnabled(true);
        btnDisconnect.setEnabled(false);
    }

    private void connectJunctions() {
        for (int i = 1; i < mySockets.size() - 1; i++) {
            mySockets.get(i).dialog.bufferSocketOut.println("evPosMid");
        }
        mySockets.get(0).dialog.bufferSocketOut.println("evPosLeft");
        mySockets.get(mySockets.size() - 1).dialog.bufferSocketOut.println("evPosRight");
    }

    private void disconnectJunctions() {
        for (int i = 0; i < mySockets.size(); i++) {
            switch (carState.get(i)) {
                case ENABLED:
                    mySockets.get(i).dialog.bufferSocketOut.println("evEnableCars");
                    break;
                case DISABLED:
                    mySockets.get(i).dialog.bufferSocketOut.println("evDisableCars");
                    break;
                case MANUAL:
                    mySockets.get(i).dialog.bufferSocketOut.println("evManualCars");
                    break;
                default:
                    break;
            }
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        isAlive = false;
    }

    @SuppressWarnings("rawtypes")
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

    private enum CarState {ENABLED, DISABLED, MANUAL}
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
