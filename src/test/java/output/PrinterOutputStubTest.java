package output;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PrinterOutputStubTest {

    private PrinterOutputStub outputStub;
    private Path path;

    @BeforeClass
    public void initOutput() {
        this.outputStub = PrinterOutputStub.getInstance();
        this.path = Paths.get(PrinterOutputStub.PRINTER_FILE_PATH);
    }

    @BeforeMethod
    public void deleteFile() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    public void writeTextCreatesFileTest() {
        outputStub.writeText("TEXT");
        assertTrue(Files.exists(path));
    }

    @Test
    public void writeTextTest() throws IOException {
        String firstLine = "SAMPLE TEXT";
        String secondLine = "NEXT LINE";
        outputStub.writeText(firstLine);
        outputStub.writeText(secondLine);
        List<String> linesList = getFileLines();
        assertEquals(2, linesList.size());
        assertEquals(firstLine, linesList.get(0));
        assertEquals(secondLine, linesList.get(1));
    }

    @AfterClass
    public void deleteFileAfterTests() throws IOException {
        Files.deleteIfExists(path);
    }

    public List<String> getFileLines() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}