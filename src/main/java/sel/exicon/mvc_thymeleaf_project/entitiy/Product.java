package sel.exicon.mvc_thymeleaf_project.entitiy;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,length = 200)
    private String name;
    @Column(nullable = false)
    private int price;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private ProductDetails productDetails;

    public Product() {
    }

    public Product(String name, int price, ProductDetails productDetails) {
        this.name = name;
        this.price = price;
        this.productDetails = productDetails;
    }

    public Product(int id, String name, int price, ProductDetails productDetails) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productDetails = productDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }
}
