package guru.springframework.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class  Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    // A recipe foreign key will be stored on the set of ingredients
      // Or, put another way, only the ingredients will have a direct tie
      // to each recipe, but each recipe will not have a tie to each ingredient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    // Required in order for deleted recipes to also remove attached notes
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    // Do not forget to specify a join table
      // Otherwise, Hibernate will create two relationship tables between
      // the many to many entities
    @ManyToMany
    @JoinTable(name = "recipe_category",
    joinColumns = @JoinColumn(name = "recipe_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    // We specify the EnumType here to override the default (ORDINAL)
    // When modified, the enumerated entity will not encounter any
      // conflicts in the original database
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    // Simplify bidirectional relationships by setting both entities
      // to each other within the same method
    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
