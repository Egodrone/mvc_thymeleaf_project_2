package sel.exicon.mvc_thymeleaf_project.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sel.exicon.mvc_thymeleaf_project.dto.ProductDto;
import sel.exicon.mvc_thymeleaf_project.entitiy.Product;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ProductConverter implements Converter<Product, ProductDto> {

    ProductDetailsConverter productDetailsConverter;

    @Autowired
    public void setProductDetailsConverter(ProductDetailsConverter productDetailsConverter) {
        this.productDetailsConverter = productDetailsConverter;
    }

    @Override
    public Product toModel(ProductDto dto) {
        Product model = new Product();
        if (dto != null) {
            model.setId(dto.getId());
            model.setName(dto.getName());
            model.setPrice(dto.getPrice());
            model.setProductDetails(productDetailsConverter.toModel(dto.getProductDetailsDto())); // convert detailsDTO to details
        }
        return model;
    }

    @Override
    public ProductDto toDTO(Product model) {
        ProductDto dto = new ProductDto();
        if (model != null) {
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setPrice(model.getPrice());
            dto.setProductDetailsDto(productDetailsConverter.toDTO(model.getProductDetails()));
        }
        return dto;
    }

    @Override
    public Collection<Product> toModels(Collection<ProductDto> collection) {
        Collection<Product> models = new ArrayList<>();
        if (collection != null) {
            for (ProductDto dto : collection) {
                models.add(toModel(dto));
            }
        }
        return models;
    }

    @Override
    public Collection<ProductDto> toDTos(Collection<Product> collection) {
        Collection<ProductDto> dtoList = new ArrayList<>();

        if (collection != null) {
            for (Product model : collection) {
                ProductDto dto = toDTO(model);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
}
