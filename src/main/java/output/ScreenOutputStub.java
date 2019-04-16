package output;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ScreenOutputStub {

    public static final String SCREEN_FILE_PATH = "screen.txt";
    private static ScreenOutputStub instance;

    public static ScreenOutputStub getInstance() {
        if (instance == null) {
            instance = new ScreenOutputStub();
        }
        return instance;
    }

    private ScreenOutputStub() {
    }

    public void writeText(String text) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SCREEN_FILE_PATH), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(text);
            writer.newLine();
        } catch (IOException exc) {
            System.out.println("WRITING TO SCREEN FILE FAILED");
            exc.printStackTrace();
        }
    }
}
