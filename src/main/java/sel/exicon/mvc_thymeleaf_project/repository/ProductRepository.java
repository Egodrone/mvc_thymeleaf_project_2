package sel.exicon.mvc_thymeleaf_project.repository;

import org.springframework.data.repository.CrudRepository;
import sel.exicon.mvc_thymeleaf_project.entitiy.Product;

import java.util.List;

//@Repository Optional annotation while using CrudRepository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    // all basic crud operations
    List<Product> findByNameIgnoreCase(String name);

    List<Product> findByProductDetails_DescriptionContains(String description);
}
