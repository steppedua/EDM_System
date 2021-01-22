package greenatom.controller;

import greenatom.dto.DocumentDto;
import greenatom.mappers.DocumentMapper;
import greenatom.message.ResponseMessage;
import greenatom.model.Attributes;
import greenatom.model.Document;
import greenatom.model.User;
import greenatom.service.DocumentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentMapper documentMapper;
    private final DocumentsServiceImpl documentsServiceImpl;

    @Autowired
    public DocumentController(DocumentMapper documentMapper,
                              DocumentsServiceImpl documentsServiceImpl
    ) {
        this.documentMapper = documentMapper;
        this.documentsServiceImpl = documentsServiceImpl;
    }

    // Метод для загрузки пользователем документа на сервер
    @PostMapping(value = "/create")
    public ResponseEntity<Optional<Document>> createDocument(
            @RequestParam("attributesList") List<Attributes> attributesList,
            @RequestParam("documentOwner") @AuthenticationPrincipal User owner,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

//        String responseMessage = "";

//        try {
        //Document newDocument = documentMapper.toDocumentByDocumentDto(documentDto);
        Document newDocument = new Document(
                file.getOriginalFilename(),
                file.getBytes(),
                attributesList,
                owner);

        Optional<Document> document = documentsServiceImpl.uploadDocument(newDocument);

//            responseMessage = "Uploaded the file successfully: " + file.getOriginalFilename();

        return ResponseEntity.status(HttpStatus.OK).body(document);
//        } catch (Exception e) {
//            responseMessage = "Could not upload the file: " + file.getOriginalFilename() + "!";
//
//            return ResponseEntity
//                    .status(HttpStatus.EXPECTATION_FAILED)
//                    .body(document);
//        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Document>> getDocument(
            @PathVariable("id") Long id,
           @AuthenticationPrincipal User userId
    ) {
        Optional<Document> userDocumentById = documentsServiceImpl.getUserDocumentById(id, userId);

        return ResponseEntity.ok().body(userDocumentById);
    }

//    @GetMapping
//    public ResponseEntity<List<UserDocumentsDto>> getDocumentList(@AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(documentMapper.toDocumentsListDto(documentServiceImpl.getDocumentList()));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> removeDocumentById(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
//        return ResponseUtils.responseEntityOf(documentServiceImpl.removeDocumentById(id));
//    }
}
