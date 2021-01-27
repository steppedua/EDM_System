package greenatom.mappers;

import greenatom.dto.DocumentDto;
import greenatom.model.Document;
import greenatom.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface DocumentMapper {

    @Mappings({
            @Mapping(target = "docId", source = "document.id"),
            @Mapping(target = "documentName", source = "document.name"),
            @Mapping(target = "file", source = "document.documentData"),
            @Mapping(target = "attributesList", source = "document.documentAttributes")
    })
    DocumentDto toDocumentDto(Document document);

    Document toCreateDocumentDto(DocumentDto documentDto, User user);

    @Mappings({
            @Mapping(target = "docId", source = "document.id"),
            @Mapping(target = "documentName", source = "document.name"),
            @Mapping(target = "file", source = "document.documentData"),
            @Mapping(target = "attributesList", source = "document.documentAttributes")
    })
    List<DocumentDto> toDocumentsListByOwnerIdDto(List<Document> userDocumentsList);
}
