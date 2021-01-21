package greenatom.mappers;

import greenatom.dto.DocumentDto;
import greenatom.model.Document;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {

    DocumentDto toDocumentDtoByDocument(Document document);

    Document toDocumentByDocumentDto(DocumentDto documentDto);

}
