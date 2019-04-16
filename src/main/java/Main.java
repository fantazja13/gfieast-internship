import dbstub.ProductsDBStub;
import output.PrinterOutputStub;
import output.ScreenOutputStub;
import product.BarcodeInUseException;
import product.Product;
import sale.Sale;

import java.math.BigDecimal;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        ProductsDBStub productDB = ProductsDBStub.getInstance();
        PrinterOutputStub printer = PrinterOutputStub.getInstance();
        ScreenOutputStub screen = ScreenOutputStub.getInstance();
        Long barcode1 = 590823L;
        Product product1 = new Product("pierwszy", barcode1, new BigDecimal("12.13"));
        Long barcode2 = 590666L;
        Product product2 = new Product("drugi", barcode2, new BigDecimal("29.13"));
        Long barcode3 = 590113L;
        Product product3 = new Product("trzeci", barcode3, new BigDecimal("49.99"));
        Long barcode4 = 131313L;
        Product product4 = new Product("CZWARTY", barcode4, new BigDecimal("549.99"));
        try {
            productDB.addProductsInBatch(Arrays.asList(product1, product2, product3, product4));
        } catch (BarcodeInUseException exc) {
            System.out.println(exc.getMessage());
        }

        Sale sale = new Sale(productDB, screen, printer);
        sale.scanProduct(barcode1);
        sale.scanProduct(barcode2);
        sale.scanProduct(barcode4);
        sale.scanProduct(barcode3);
        sale.scanProduct(0L);
        sale.scanProduct(123098L);
        sale.endSale();
    }
}
