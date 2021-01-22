package greenatom.dto;

import greenatom.model.Attributes;
import greenatom.model.User;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto implements Serializable {
    private Long id;  // Уникальный идентификатор документаДТО
    private String documentName;  // Полное название документа. Например, "Купля-продажи сапог_2017_форма2.pdf"
    private byte[] value;  // Байт-массив документа - "сам документ"
    private List<Attributes> attributesList; // Список атрибутов документа
    private User documentOwner;  // Владелец документа
}