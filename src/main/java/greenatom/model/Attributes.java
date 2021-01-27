package greenatom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attributes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Уникальный идентификатор атрибута

    @NotEmpty
    @Size(min = 3, max = 15)
    @Column(name = "name", unique = true, nullable = false)
    private String name;  // Название атрибута

    @ManyToMany(mappedBy = "documentAttributes")
    private List<Document> documents = new ArrayList<>();
    // У каждого документа может быть несколько атрибутов

    @Override
    public String toString() {
        return "Attributes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documents=" + documents +
                '}';
    }
}