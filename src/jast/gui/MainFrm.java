package jast.gui;

import jast.utilities.AntlrUtil;
import jast.utilities.FileUtil;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        var content = "";
        if (fromFileButton.isSelected()) {
            content = FileUtil.getFileContent(FileUtil.pathToInput);
        } else {
            content = textArea1.getText();
        }

        AntlrUtil.generateAST(AntlrUtil.getRuleContext(content), false, 0);
        AntlrUtil.output = AntlrUtil.output.concat("digraph G {");
        AntlrUtil.output = AntlrUtil.output.concat("\n");
        AntlrUtil.writeDOT();
        AntlrUtil.output = AntlrUtil.output.concat("}");

        FileUtil.writeToFile(FileUtil.pathToOutput, AntlrUtil.output);

        setVisible(false);
        FinalMsg finalMsg = new FinalMsg();
        finalMsg.textArea1.setText(FileUtil.getFileContent(FileUtil.pathToOutput));
        finalMsg.pack();
        finalMsg.setVisible(true);
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
