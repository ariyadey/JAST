package co.daylam;

import co.daylam.gui.InitialMsg;

public class Main {

    public static String pathToInput = "input.java";
    public static String pathToOutput = "output.dot";

    public static void main(String[] args) {
        InitialMsg initialMsg = new InitialMsg();
        initialMsg.pack();
        initialMsg.setVisible(true);
    }
}
