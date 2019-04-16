package product;

import java.math.BigDecimal;

public class Product {

    private String productName;
    private Long barCode;
    private BigDecimal price;

    public Product(String productName, Long barCode, BigDecimal price) {
        this.productName = productName;
        this.barCode = barCode;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getBarCode() {
        return barCode;
    }

    public void setBarCode(Long barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
