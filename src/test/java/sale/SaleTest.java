package sale;

import dbstub.ProductsDBStub;
import org.testng.annotations.*;
import output.PrinterOutputStub;
import output.ScreenOutputStub;
import product.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SaleTest {

    private Sale sale;
    private Product prd1;
    private Product prd2;
    private Product prd3;
    private Product prd4;

    @BeforeClass
    public void initSale() throws IOException {
        ProductsDBStub dbStub = ProductsDBStub.getInstance();
        prd1 = new Product("Product1", 123456789L, new BigDecimal("19.99"));
        prd2 = new Product("Product2", 59003548L, new BigDecimal("178.99"));
        prd3 = new Product("Product3", 58455517L, new BigDecimal("12.45"));
        prd4 = new Product("Product4", 10874112668L, new BigDecimal("38.93"));
        Map<Long, Product> products = dbStub.getAllProducts();
        products.clear();
        for (Product product : Arrays.asList(prd1, prd2, prd3, prd4)) {
            products.put(product.getBarCode(), product);
        }
    }

    @BeforeMethod
    public void clearReceipt() throws IOException {
        this.sale = new Sale(ProductsDBStub.getInstance(), ScreenOutputStub.getInstance(),
                PrinterOutputStub.getInstance());
        Files.deleteIfExists(Paths.get(ScreenOutputStub.SCREEN_FILE_PATH));
        Files.deleteIfExists(Paths.get(PrinterOutputStub.PRINTER_FILE_PATH));
    }

//    @AfterMethod
//    public void deleteFiles() throws IOException {
//        Files.deleteIfExists(Paths.get(ScreenOutputStub.SCREEN_FILE_PATH));
//        Files.deleteIfExists(Paths.get(PrinterOutputStub.PRINTER_FILE_PATH));
//    }

    @Test
    public void scannedProductIsOnReceiptTest() {
        sale.scanProduct(prd1.getBarCode());
        assertEquals(1, sale.getReceipt().getProducts().size());
        assertTrue(sale.getReceipt().getProducts().contains(prd1));
    }

    @Test
    public void scannedProductIsPrintedOnScreenTest() throws IOException {
        sale.scanProduct(prd1.getBarCode());
        sale.scanProduct(prd2.getBarCode());
        List<String> linesList = getScreenLinesList();
        assertEquals(2, linesList.size());
        assertTrue(linesList.get(0).contains(prd1.getProductName()));
        assertTrue(linesList.get(0).contains(prd1.getPrice().toString()));
        assertTrue(linesList.get(1).contains(prd2.getProductName()));
    }

    @Test
    public void scanProductWithNullBarcodeTest() throws IOException {
        sale.scanProduct(null);
        List<String> linesList = getScreenLinesList();
        assertTrue(linesList.contains(Sale.INVALID));
    }

    @Test
    public void scanProductWithZeroBarcodeTest() throws IOException {
        sale.scanProduct(0L);
        List<String> linesList = getScreenLinesList();
        assertTrue(linesList.contains(Sale.INVALID));

    }

    @Test
    public void scanProductWithUnknownBarcodeTest() throws IOException {
        sale.scanProduct(1111111L);
        List<String> linesList = getScreenLinesList();
        assertTrue(linesList.contains(Sale.NOT_FOUND));
    }

    @Test
    public void endingSaleSumAllPricesTest() {
        sale.scanProduct(prd1.getBarCode());
        sale.scanProduct(prd2.getBarCode());
        sale.scanProduct(prd3.getBarCode());
        sale.scanProduct(prd4.getBarCode());
        sale.endSale();
        BigDecimal total = prd1.getPrice().add(prd2.getPrice()).add(prd3.getPrice()).add(prd4.getPrice());
        assertEquals(total, sale.getReceipt().getTotalSum());
    }

    @Test
    public void endingSalePrintsRecipeOnPrinterTest() throws IOException {
        sale.scanProduct(prd1.getBarCode());
        sale.scanProduct(prd2.getBarCode());
        sale.scanProduct(prd3.getBarCode());
        sale.scanProduct(prd4.getBarCode());
        sale.endSale();
        BigDecimal total = prd1.getPrice().add(prd2.getPrice()).add(prd3.getPrice()).add(prd4.getPrice());
        List<String> linesList = getPrinterLinesList();
        assertTrue(linesList.get(linesList.size()-1).contains(total.toString()));
        assertTrue(linesList.get(1).contains(prd1.getProductName()));
        assertTrue(linesList.get(2).contains(prd2.getProductName()));
        assertTrue(linesList.get(3).contains(prd3.getProductName()));
        assertTrue(linesList.get(4).contains(prd4.getProductName()));
        assertTrue(linesList.get(1).contains(prd1.getPrice().toString()));
        assertTrue(linesList.get(2).contains(prd2.getPrice().toString()));
        assertTrue(linesList.get(3).contains(prd3.getPrice().toString()));
        assertTrue(linesList.get(4).contains(prd4.getPrice().toString()));
    }

    @Test
    public void endingSalePrintsSumOnScreenTest() throws IOException {
        sale.scanProduct(prd1.getBarCode());
        sale.scanProduct(prd2.getBarCode());
        sale.scanProduct(prd3.getBarCode());
        sale.scanProduct(prd4.getBarCode());
        sale.endSale();
        BigDecimal total = prd1.getPrice().add(prd2.getPrice()).add(prd3.getPrice()).add(prd4.getPrice());
        List<String> linesList = getScreenLinesList();
        assertTrue(linesList.get(linesList.size()-1).contains(total.toString()));
    }

    @AfterClass
    public void deleteFilesAfterTests() throws IOException {
        Files.deleteIfExists(Paths.get(ScreenOutputStub.SCREEN_FILE_PATH));
        Files.deleteIfExists(Paths.get(PrinterOutputStub.PRINTER_FILE_PATH));
    }

    private List<String> getScreenLinesList() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ScreenOutputStub.SCREEN_FILE_PATH))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    private List<String> getPrinterLinesList() throws IOException {
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(PrinterOutputStub.PRINTER_FILE_PATH))) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}