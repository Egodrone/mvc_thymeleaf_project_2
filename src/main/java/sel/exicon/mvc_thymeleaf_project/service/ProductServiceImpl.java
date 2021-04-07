package sel.exicon.mvc_thymeleaf_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sel.exicon.mvc_thymeleaf_project.converter.ProductConverter;
import sel.exicon.mvc_thymeleaf_project.dto.ProductDto;
import sel.exicon.mvc_thymeleaf_project.entitiy.Product;
import sel.exicon.mvc_thymeleaf_project.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductConverter productConverter;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Override
    public ProductDto saveOrUpdate(ProductDto productDto) {
        if (productDto == null) throw new IllegalArgumentException("ProductDto is not valid");
        Product convertDtoToModel = productConverter.toModel(productDto);
        Product savedObject = productRepository.save(convertDtoToModel);
        return productConverter.toDTO(savedObject);
    }

    @Override
    public List<ProductDto> getAll() {
        Iterable<Product> iterable = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        iterable.iterator().forEachRemaining(productList::add);

        // convert list of product to list of dto
        return new ArrayList<>(productConverter.toDTos(productList));
    }

    @Override
    public ProductDto findById(int id) {
        if (id == 0) throw new IllegalArgumentException("Id should not be null");
        Product product = productRepository.findById(id).orElse(null);
        return productConverter.toDTO(product);
    }

    @Override
    public void deleteById(int id) {
        if (id == 0) throw new IllegalArgumentException("Id should not be null");
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> findByName(String name) {
        if (name == null) throw new IllegalArgumentException("name should not be null");
        List<Product> productList = productRepository.findByNameIgnoreCase(name);
        return new ArrayList<>(productConverter.toDTos(productList));
    }
}
