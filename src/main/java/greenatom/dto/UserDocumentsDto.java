package greenatom.dto;

import greenatom.model.Document;
import greenatom.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDocumentsDto {
    private Long id;
    private User owner;
    private List<Document> documents;
}