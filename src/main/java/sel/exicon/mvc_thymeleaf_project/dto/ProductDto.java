package sel.exicon.mvc_thymeleaf_project.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ProductDto {

    private int id;
    @NotEmpty(message = "Field cannot be empty")
    @Size(min = 2,max = 20, message = "Has to be in 2-20 chars range")
    private String name;

    private int price;

    private ProductDetailsDto productDetailsDto;

    public ProductDto() {
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

    public ProductDetailsDto getProductDetailsDto() {
        return productDetailsDto;
    }

    public void setProductDetailsDto(ProductDetailsDto productDetailsDto) {
        this.productDetailsDto = productDetailsDto;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productDetailsDto=" + productDetailsDto +
                '}';
    }
}
