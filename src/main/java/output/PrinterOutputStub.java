package output;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PrinterOutputStub {

    public static final String PRINTER_FILE_PATH = "printer.txt";
    private static PrinterOutputStub instance;

    public static PrinterOutputStub getInstance() {
        if (instance == null) {
            instance = new PrinterOutputStub();
        }
        return instance;
    }

    private PrinterOutputStub() {
    }

    public void writeText(String text) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PRINTER_FILE_PATH), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(text);
            writer.newLine();
        } catch (IOException exc) {
            System.out.println("WRITING TO PRINTER FILE FAILED");
            exc.printStackTrace();
        }
    }
}
