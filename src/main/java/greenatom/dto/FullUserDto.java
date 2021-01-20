package greenatom.dto;

import greenatom.model.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FullUserDto implements Serializable {
    private Long id;
    private List<Attributes> attributes;
    private HashMap<Long, String> hashMap;
    private DocumentType type;
    private DocumentGroups groups;
    private UserDocuments userDocuments;
}