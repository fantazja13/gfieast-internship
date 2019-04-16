package sale;

import product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private final List<Product> products = new ArrayList<>();
    private BigDecimal totalSum;

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }
}
