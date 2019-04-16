package sale;

import dbstub.ProductsDBStub;
import output.PrinterOutputStub;
import output.ScreenOutputStub;
import product.Product;

import java.math.BigDecimal;

public class Sale {

    public static final String INVALID = "INVALID BAR-CODE.";
    public static final String NOT_FOUND = "PRODUCT NOT FOUND";
    private final ProductsDBStub productsDB;
    private final ScreenOutputStub screenOutput;
    private final PrinterOutputStub printerOutput;
    private final Receipt receipt = new Receipt();

    public Sale(ProductsDBStub productsDB, ScreenOutputStub screenOutput, PrinterOutputStub printerOutput) {
        this.productsDB = productsDB;
        this.screenOutput = screenOutput;
        this.printerOutput = printerOutput;
    }

    public void scanProduct(Long barCode) {
        if (barCode == null || barCode.equals(0L)) {
            sendToScreen(INVALID);
        } else if (productsDB.isBarcodeInDB(barCode)) {
            Product product = productsDB.findProductByBarcode(barCode);
            receipt.addProduct(product);
            sendToScreen(product.getProductName() + " " + product.getPrice());
        } else {
            sendToScreen(NOT_FOUND);
        }
    }

    public void endSale() {
        StringBuilder sb = new StringBuilder("Scanned products: ");
        sb.append(System.lineSeparator());
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : receipt.getProducts()) {
            sb.append(product.getProductName()).append(" ").append(product.getPrice());
            sb.append(System.lineSeparator());
            total = total.add(product.getPrice());
        }
        sb.append("Total: ").append(total);
        receipt.setTotalSum(total);
        sendToScreen("Total: " + total);
        sendToPrinter(sb.toString());
    }

    private void sendToPrinter(String message) {
        printerOutput.writeText(message);
    }

    private void sendToScreen(String message) {
        screenOutput.writeText(message);
    }

    public Receipt getReceipt() {
        return receipt;
    }
}
