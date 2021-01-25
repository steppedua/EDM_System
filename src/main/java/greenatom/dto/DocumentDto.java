package greenatom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import greenatom.model.Attributes;
import greenatom.model.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto implements Serializable {

    @JsonProperty("docId")
    private Long docId;  // Уникальный идентификатор документаДТО

    @JsonProperty("documentName")
    private String documentName;  // Полное название документа. Например, "Купля-продажи сапог_2017_форма2.pdf"

    @JsonProperty("file")
    private byte[] file;  // Байт-массив документа - "сам документ"

    @JsonProperty("attributesList")
    private List<Attributes> attributesList; // Список атрибутов документа
}