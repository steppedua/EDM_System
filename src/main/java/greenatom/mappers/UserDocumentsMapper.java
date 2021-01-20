package greenatom.mappers;

import greenatom.dto.UserDocumentsDto;
import greenatom.model.UserDocuments;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper
public interface UserDocumentsMapper {
    UserDocumentsDto toDocumentByIdDto(Optional<UserDocuments> userDocuments);

//    UserDocuments toCreateUserDocumentsDto(Optional<UserDocumentsDto> userDocumentsDto);

//    @Mappings({
//            @Mapping(source = "type", target = "type"),
//            @Mapping(source = "group", target = "groups"),
//            @Mapping(source = "attributes", target = "attributes"),
//            @Mapping(source = "userDocuments", target = "userDocuments")
//    })
//    FullUserDto toFullUserDto(DocumentDto documentDto, UserDocumentsDto userDocumentsDto);
}
