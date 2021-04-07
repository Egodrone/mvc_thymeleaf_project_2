package sel.exicon.mvc_thymeleaf_project.converter;

import org.springframework.stereotype.Component;
import sel.exicon.mvc_thymeleaf_project.dto.ProductDetailsDto;
import sel.exicon.mvc_thymeleaf_project.entitiy.ProductDetails;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

@Component
public class ProductDetailsConverter implements Converter<ProductDetails, ProductDetailsDto> {
    @Override
    public ProductDetails toModel(ProductDetailsDto dto) {
        ProductDetails model = new ProductDetails();
        if (dto != null) {
            model.setId(dto.getId());
            model.setName(dto.getName());
            model.setDescription(dto.getDescription());
            model.setImage(dto.getImage());
        }

        return model;
    }

    @Override
    public ProductDetailsDto toDTO(ProductDetails model) {
        ProductDetailsDto dto = new ProductDetailsDto();
        if (model != null) {
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setDescription(model.getDescription());
            if (model.getImage() != null) {
                // if we want to show image in thymeleaf we should convert byte to string then encode it using Base64
                dto.setImageString(Base64.getEncoder().encodeToString(model.getImage()));
            }
        }
        return dto;
    }

    @Override
    public Collection<ProductDetails> toModels(Collection<ProductDetailsDto> collection) {
        Collection<ProductDetails> models= new ArrayList<>();
        if (collection !=null){
           for (ProductDetailsDto dto: collection){
               models.add(toModel(dto));
           }
        }
        return models;
    }

    @Override
    public Collection<ProductDetailsDto> toDTos(Collection<ProductDetails> collection) {
        Collection<ProductDetailsDto> dtoList = new ArrayList<>();
        if (collection !=null){
            for (ProductDetails model: collection){
                dtoList.add(toDTO(model));
            }
        }
        return dtoList;
    }
}
