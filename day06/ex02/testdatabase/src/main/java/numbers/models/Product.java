package numbers.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Long identifier;
    private String name;
    private BigDecimal price;

    public Product(Long identifier, String name, BigDecimal price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    // Getters and setters
    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{"
                + "identifier=" + identifier
                + ", name='" + name + '\''
                + ", price=" + price
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return identifier.equals(product.identifier)
                && name.equals(product.name)
                && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, price);
    }
}
