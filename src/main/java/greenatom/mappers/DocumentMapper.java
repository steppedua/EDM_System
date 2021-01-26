package greenatom.mappers;

import greenatom.dto.DocumentDto;
import greenatom.model.Document;
import greenatom.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {

////    @Mappings({
////            @Mapping(target = "id", source = "document.id"),
////            @Mapping(target = "documentName", source = "document.name"),
////            @Mapping(target = "value", source = "document.documentData"),
////            @Mapping(target = "attributesList", source = "document.documentAttributes"),
////            @Mapping(target = "documentOwner", source = "document.owner.username"),
////    })
//    DocumentDto toDocumentDtoByDocument(Optional<Document> document);

    Document toCreateDocumentDto(DocumentDto documentDto, User user);
}
