package gui;

import javax.swing.*;
import java.awt.event.*;

import Main;
import utilities.*;

public class MainFrm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    JTextArea textArea1;
    private JRadioButton fromTextAreaButton;
    private JRadioButton fromFileButton;

    public MainFrm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        var content = "";
        if (fromFileButton.isSelected()) {
            content = FileUtil.getFileContent(Main.pathToInput);
        } else {
            content = textArea1.getText();
            System.out.println(content);
        }

        AntlrUtil.generateAST(AntlrUtil.getRuleContext(content), false, 0);
        AntlrUtil.output = AntlrUtil.output.concat("digraph G {");
        AntlrUtil.output = AntlrUtil.output.concat("\n");
//        System.out.println("digraph G {");
        AntlrUtil.writeDOT();
        AntlrUtil.output = AntlrUtil.output.concat("}");
//        System.out.println("}");
        FileUtil.writeToFile(Main.pathToOutput, AntlrUtil.output);

        setVisible(false);
        FinalMsg finalMsg = new FinalMsg();
        finalMsg.textArea1.setText(FileUtil.getFileContent(Main.pathToOutput));
        finalMsg.pack();
        finalMsg.setVisible(true);
        System.out.println(AntlrUtil.output);
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
