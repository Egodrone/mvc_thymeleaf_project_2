package sel.exicon.mvc_thymeleaf_project.dto;

import java.util.Arrays;

public class ProductDetailsDto {
    private int id;
    private String name;
    private String description;
    private byte[] image;
    private String imageString;

    public ProductDetailsDto() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    @Override
    public String toString() {
        return "ProductDetailsDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", imageString='" + imageString + '\'' +
                '}';
    }
}
