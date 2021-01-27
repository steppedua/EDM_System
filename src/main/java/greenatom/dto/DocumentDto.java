package greenatom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import greenatom.model.Attributes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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

    @Override
    public String toString() {
        return "DocumentDto{" +
                "docId=" + docId +
                ", documentName='" + documentName + '\'' +
                ", attributesList=" + attributesList +
                '}';
    }
}