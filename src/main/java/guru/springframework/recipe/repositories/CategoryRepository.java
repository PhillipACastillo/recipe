package guru.springframework.recipe.repositories;

import guru.springframework.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // Extend the CrudRepository to find by attributes
      // other than ID
    // The optional class is required to prevent
      // NullPointerExceptions
    Optional<Category> findByDescription(String description);
}
