import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.*;

import com.intellij.uiDesigner.core.*;
//import com.jgoodies.forms.factories.*;

/*
 * Created by JFormDesigner on Mon Jun 26 20:15:17 IDT 2017
 */


/**
 * @author Ezra Steinmetz
 */
public class ServerPanel extends JFrame {
    static ServerPanel instance;
    final String SH_PRESS_CODE = "ShPress";
    final String pathToTLJar = "\"out\\artifacts\\TrafficLights_jar\\TrafficLights.jar\"";
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Ezra Steinmetz
    private JButton btnShabbos;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel1;
    private JLabel label2;
    private JComboBox JunctionListCB;
    private JLabel label3;
    private JComboBox ButtonComboBox;
    private JButton button1;
    private JPanel panel6;
    private JInternalFrame internalFrame1;
    private JLabel label1;
    private JTextField instanceName;
    private JButton startInstanceBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public ServerPanel() {
        instance = this;
        initComponents();
        for (int i = 0; i < 16; i++) {
            ButtonComboBox.addItem(i);
        }
    }

    public static void addJunction(String junction) {
        instance.JunctionListCB.addItem(junction);
    }

    private void thisWindowClosing(WindowEvent e) {
        // TODO add your code here
    }

    private void cboSocketsActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void btnShabbosActionPerformed(ActionEvent e) {
        Socket socket = Listener.getMap().get(JunctionListCB.getSelectedItem());
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        writer.println(SH_PRESS_CODE);

    }

    private void createJunctionAction(ActionEvent e) {
        String instanceNameText = instanceName.getText();
        if (instanceNameText.equals("") || Listener.getMap().containsKey(instanceNameText)) {
            return;
        }
        try {
            Runtime.getRuntime().exec("java -jar " + pathToTLJar + " " + instanceNameText);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void sendButtonCommandEvent(ActionEvent e) {
        Socket socket = Listener.getMap().get(JunctionListCB.getSelectedItem());
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        writer.println(ButtonComboBox.getSelectedItem().toString());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Ezra Steinmetz
        btnShabbos = new JButton();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel1 = new JPanel();
        label2 = new JLabel();
        JunctionListCB = new JComboBox();
        label3 = new JLabel();
        ButtonComboBox = new JComboBox();
        button1 = new JButton();
        panel6 = new JPanel();
        internalFrame1 = new JInternalFrame();
        label1 = new JLabel();
        instanceName = new JTextField();
        startInstanceBtn = new JButton();

        //======== this ========
        setTitle("Main Control");
        setMinimumSize(new Dimension(80, 39));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- btnShabbos ----
        btnShabbos.setText("Shabbos Button");
        btnShabbos.addActionListener(e -> btnShabbosActionPerformed(e));
        contentPane.add(btnShabbos);
        btnShabbos.setBounds(60, 210, 130, 55);

        //======== panel2 ========
        {

            // JFormDesigner evaluation mark
            panel2.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                            java.awt.Color.red), panel2.getBorder()));
            panel2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if ("border".equals(e.getPropertyName())) throw new RuntimeException();
                }
            });

            panel2.setLayout(null);
        }
        contentPane.add(panel2);
        panel2.setBounds(45, 60, panel2.getPreferredSize().width, 0);

        //======== panel3 ========
        {
            panel3.setLayout(null);
        }
        contentPane.add(panel3);
        panel3.setBounds(60, 35, panel3.getPreferredSize().width, 0);

        //======== panel4 ========
        {
            panel4.setBorder(new TitledBorder("Send Command"));

            //======== panel1 ========
            {
                panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));

                //---- label2 ----
                label2.setText("Junction:");
                panel1.add(label2, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- JunctionListCB ----
                JunctionListCB.addActionListener(e -> cboSocketsActionPerformed(e));
                panel1.add(JunctionListCB, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                //---- label3 ----
                label3.setText("Button No.:");
                panel1.add(label3, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                panel1.add(ButtonComboBox, new GridConstraints(1, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
            }
            panel4.add(panel1);

            //---- button1 ----
            button1.setText("Send Button Command");
            button1.addActionListener(e -> sendButtonCommandEvent(e));
            panel4.add(button1);
        }
        contentPane.add(panel4);
        panel4.setBounds(40, 55, 170, 225);

        //======== panel6 ========
        {
            panel6.setLayout(new BorderLayout(2, 2));
        }
        contentPane.add(panel6);
        panel6.setBounds(new Rectangle(new Point(270, 250), panel6.getPreferredSize()));

        //======== internalFrame1 ========
        {
            internalFrame1.setVisible(true);
            internalFrame1.setBorder(new EtchedBorder());
            internalFrame1.setTitle("Start New Instance");
            Container internalFrame1ContentPane = internalFrame1.getContentPane();
            internalFrame1ContentPane.setLayout(null);

            //---- label1 ----
            label1.setText("Name:");
            internalFrame1ContentPane.add(label1);
            label1.setBounds(new Rectangle(new Point(20, 10), label1.getPreferredSize()));

            //---- instanceName ----
            instanceName.setAlignmentX(3.5F);
            internalFrame1ContentPane.add(instanceName);
            instanceName.setBounds(65, 5, 80, instanceName.getPreferredSize().height);

            //---- startInstanceBtn ----
            startInstanceBtn.setText("Start instance");
            startInstanceBtn.addActionListener(e -> createJunctionAction(e));
            internalFrame1ContentPane.add(startInstanceBtn);
            startInstanceBtn.setBounds(15, 35, 125, startInstanceBtn.getPreferredSize().height);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < internalFrame1ContentPane.getComponentCount(); i++) {
                    Rectangle bounds = internalFrame1ContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = internalFrame1ContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                internalFrame1ContentPane.setMinimumSize(preferredSize);
                internalFrame1ContentPane.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(internalFrame1);
        internalFrame1.setBounds(245, 115, 175, 115);

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
}
