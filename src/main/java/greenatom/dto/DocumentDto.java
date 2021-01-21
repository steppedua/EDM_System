package greenatom.dto;

import greenatom.model.Attributes;
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
    private String documentOwner;  // Имя владельца документа
    private List<Attributes> attributesList; // Список атрибутов документа
    private byte[] value;  // Байт-массив документа - "сам документ"
}