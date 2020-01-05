package jast.utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String getFileContent(String filePath) {
        try {
            var inputContent = readFileAsString(filePath);
            System.out.println(inputContent);
            return inputContent;
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
        return null;
    }

    private static String readFileAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static void writeToFile(String filePath, String data) {
        var file = new File(filePath);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(data);
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }
}
