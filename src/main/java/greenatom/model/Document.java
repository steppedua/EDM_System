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
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 15)
    @Column(name = "name")
    private String name;

    @Lob
    @NotEmpty
    @Column(name = "document_data")
    private byte[] documentData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_id")
    private UserDocuments document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type_id")
    private DocumentType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_groups_id")
    private DocumentGroups group;

    @ManyToMany
    @JoinTable(
            name = "document_attributes",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private List<Attributes> attributes = new ArrayList<>();

    public Document(@NotEmpty @Size(min = 4, max = 15) String name,
                    @NotEmpty byte[] documentData,
                    UserDocuments document,
                    DocumentType type,
                    DocumentGroups group,
                    List<Attributes> attributes
    ) {
        this.name = name;
        this.documentData = documentData;
        this.document = document;
        this.type = type;
        this.group = group;
        this.attributes = attributes;
    }
}