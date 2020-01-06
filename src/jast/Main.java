package jast;

import jast.gui.InitialMsg;

public class Main {

    public static void main(String[] args) {
        InitialMsg initialMsg = new InitialMsg();
        initialMsg.pack();
        initialMsg.setVisible(true);
        initialMsg.dispose();
    }
}
