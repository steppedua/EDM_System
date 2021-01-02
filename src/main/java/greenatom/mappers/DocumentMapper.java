package greenatom.mappers;

import greenatom.dto.DocumentDto;
import greenatom.model.Document;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface DocumentMapper {
    DocumentDto toDocumentByDocumentNameDto(String documentName);

    DocumentDto toDocumentByIdDto(Document document);

    List<DocumentDto> toDocumentsListDto(List<Document> documents);

    Document toCreateDocument(MultipartFile file);
}
