package greenatom.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "value")
    private byte[] value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    public Document(String name, String type, byte[] value, User owner) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.owner = owner;
    }
}
