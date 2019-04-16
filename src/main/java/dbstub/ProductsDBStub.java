package dbstub;

import product.BarcodeInUseException;
import product.InvalidBarcodeException;
import product.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsDBStub {

    private static ProductsDBStub instance;
    private Map<Long, Product> allProducts = new HashMap<>();

    public static ProductsDBStub getInstance() {
        if (instance == null) {
            instance = new ProductsDBStub();
        }
        return instance;
    }

    private ProductsDBStub(){
    }

    public Map<Long, Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(Map<Long, Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void addProductsInBatch(List<Product> products) throws BarcodeInUseException {
        for (Product product : products) {
            addProduct(product);
        }
    }

    public void addProduct(Product product) throws BarcodeInUseException, InvalidBarcodeException {
        if (product.getBarCode() == null || product.getBarCode().equals(0L)) {
            throw new InvalidBarcodeException("Barcode has to be integer number greater than 0.");
        }
        if (allProducts.containsKey(product.getBarCode())) {
            throw new BarcodeInUseException("This barcode is already in use.");
        } else {
            allProducts.put(product.getBarCode(), product);
        }
    }

    public Product findProductByBarcode(Long barCode) {
        return allProducts.get(barCode);
    }

    public boolean isBarcodeInDB(Long barCode) {
        return allProducts.containsKey(barCode);
    }
}
