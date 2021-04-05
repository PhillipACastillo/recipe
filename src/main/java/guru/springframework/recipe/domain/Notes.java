package guru.springframework.recipe.domain;


import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = "recipe")
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Recipe recipe;

    // The Lob annotation allows us to store more than
      // the hibernate string limit of 255 characters
      // More specifically, we're telling hibernate to store
      // This string as a CLOB: Character Large Object
    @Lob
    private String recipeNotes;

}
