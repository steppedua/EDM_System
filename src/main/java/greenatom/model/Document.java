package greenatom.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Уникальный идентификатор сущности - документа

    @NotEmpty
    @Size(min = 4, max = 15)
    @Column(name = "name")
    private String name;  // Название документа

    @Lob
    @NotEmpty
    @Column(name = "document_data")
    private byte[] documentData;  // Сам документ

    @ManyToMany
    @JoinTable(
            name = "document_attributes",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private List<Attributes> documentAttributes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    public Document(@NotEmpty @Size(min = 4, max = 15) String name,
                    @NotEmpty byte[] documentData,
                    List<Attributes> documentAttributes,
                    User documentOwner
    ) {
        this.name = name;
        this.documentData = documentData;
        this.documentAttributes = documentAttributes;
        this.owner = documentOwner;
    }
}