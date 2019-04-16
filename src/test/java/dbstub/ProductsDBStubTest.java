package dbstub;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product.BarcodeInUseException;
import product.InvalidBarcodeException;
import product.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductsDBStubTest {

    private ProductsDBStub dbStub;

    @BeforeClass
    public void initDbStub() {
        this.dbStub = ProductsDBStub.getInstance();
    }

    @Test
    public void addProductTest() {
        Product prd = new Product("Product1", 123456789L, new BigDecimal("19.99"));
        dbStub.addProduct(prd);
        Map<Long, Product> allProducts = dbStub.getAllProducts();
        assertTrue(allProducts.containsKey(prd.getBarCode()));
        assertTrue(allProducts.containsValue(prd));
    }

    @Test(expectedExceptions = BarcodeInUseException.class)
    public void addProductWithExistingBarcodeTest() {
        Product prd2 = new Product("Product2", 59003548L, new BigDecimal("178.99"));
        Product prd3 = new Product("Product2", 59003548L, new BigDecimal("178.99"));
        dbStub.addProduct(prd2);
        dbStub.addProduct(prd3);
    }

    @Test(expectedExceptions = InvalidBarcodeException.class)
    public void addProductWithNullBarcodeTest() {
        Product prd4 = new Product("Product4", null, new BigDecimal("138.13"));
        dbStub.addProduct(prd4);
    }

    @Test(expectedExceptions = InvalidBarcodeException.class)
    public void addProductWithZeroBarcodeTest() {
        Product prd5 = new Product("Product5", 0L, new BigDecimal("138.13"));
        dbStub.addProduct(prd5);
    }

    @Test
    public void addProductsInBatchTest() {
        Product prd6 = new Product("Product6", 15948263L, new BigDecimal("18.97"));
        Product prd7 = new Product("Product7", 4554789889L, new BigDecimal("157.78"));
        Product prd8 = new Product("Product8", 45578861L, new BigDecimal("49.08"));
        dbStub.addProductsInBatch(Arrays.asList(prd6, prd7, prd8));
        assertTrue(dbStub.getAllProducts().containsKey(prd6.getBarCode()));
        assertTrue(dbStub.getAllProducts().containsKey(prd7.getBarCode()));
        assertTrue(dbStub.getAllProducts().containsValue(prd8));
    }

    @Test
    public void findProductByBarcodeTest() {
        Product prd9 = new Product("Product9", 54511227788L, new BigDecimal("1313.33"));
        dbStub.addProduct(prd9);
        Product prdFromDB = dbStub.findProductByBarcode(prd9.getBarCode());
        assertEquals(prd9, prdFromDB);
    }

    @Test
    public void isBarcodeInDBTest() {
        Product prd10 = new Product("Product10", 403369871L, new BigDecimal("999.99"));
        dbStub.addProduct(prd10);
        assertTrue(dbStub.isBarcodeInDB(prd10.getBarCode()));
    }

    @AfterClass
    public void clearDB() {
        dbStub.getAllProducts().clear();
    }

}