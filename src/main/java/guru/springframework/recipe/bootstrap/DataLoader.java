package guru.springframework.recipe.bootstrap;

import guru.springframework.recipe.domain.*;
import guru.springframework.recipe.repositories.CategoryRepository;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository,
                      RecipeRepository recipeRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private UnitOfMeasure getUnitOfMeasure(String description) {
        return unitOfMeasureRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Unit of measure not found"));
    }

    private Category getCategory(String description) {
        return categoryRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(1);

        UnitOfMeasure tablespoons = getUnitOfMeasure("Tablespoon");
        UnitOfMeasure teaspoons = getUnitOfMeasure("Teaspoon");
        UnitOfMeasure cup = getUnitOfMeasure("Cup");
        UnitOfMeasure pinch = getUnitOfMeasure("Pinch");
        UnitOfMeasure ounce = getUnitOfMeasure("Ounce");
        UnitOfMeasure dash = getUnitOfMeasure("Dash");
        UnitOfMeasure each = getUnitOfMeasure("Each");

        Category americanCategory = getCategory("American");
        Category mexicanCategory = getCategory("Mexican");

        Recipe guac = new Recipe();
        guac.setDescription("America's favorite dip.");
        guac.setPrepTime(10);
        guac.setCookTime(0);
        guac.setDifficulty(Difficulty.EASY);
        guac.setServings(2);
        guac.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guac.setDirections("1. Cut the avocado, remove flesh." +
                "2. Mash with a fork." +
                "3. Add salt, lime juice, and the rest ");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just buy it at the shop");
        guacNotes.setRecipe(guac);
        guac.setNotes(guacNotes);

        guac.getIngredients()
                .add(new Ingredient("ripe avocados", new BigDecimal(2), each, guac));
        guac.getIngredients()
                .add(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoons, guac));
        guac.getIngredients()
                .add(new Ingredient("fresh lime juice or lemon juice",
                        new BigDecimal(2),
                        tablespoons,
                        guac));
        guac.getIngredients()
                .add(new Ingredient("minced red onion or thinly sliced green onion",
                        new BigDecimal(2),
                        tablespoons,
                        guac));
        guac.getIngredients()
                .add(new Ingredient("serrano chiles, stems and seeds removed, minced",
                        new BigDecimal(2),
                        each,
                        guac));
        guac.getIngredients()
                .add(new Ingredient("Cilantro", new BigDecimal(2), tablespoons, guac));
        guac.getIngredients()
                .add(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash, guac));
        guac.getIngredients()
                .add(new Ingredient("ripe tomato, seeds and pulp removed, chopped",
                        new BigDecimal(".5"),
                        each,
                        guac));

        guac.getCategories().add(americanCategory);
        guac.getCategories().add(mexicanCategory);

        recipes.add(guac);

        return recipes;
    }

}
