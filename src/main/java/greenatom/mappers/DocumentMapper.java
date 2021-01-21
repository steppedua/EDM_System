package greenatom.mappers;

import greenatom.dto.DocumentDto;
import greenatom.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface DocumentMapper {

    @Mappings({
            @Mapping(source = "document.id", target = "id"),
            @Mapping(source = "document.name", target = "documentName"),
            @Mapping(source = "document.documentData", target = "value"),
            @Mapping(source = "document.documentAttributes", target = "attributesList"),
            @Mapping(source = "document.owner.username", target = "documentOwner"),
    })
    DocumentDto toDocumentDtoByDocument(Document document);

    Document toDocumentByDocumentDto(DocumentDto documentDto);

}
