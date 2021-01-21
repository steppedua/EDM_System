package greenatom.controller;

import greenatom.dto.DocumentDto;
import greenatom.mappers.DocumentMapper;
import greenatom.mappers.DocumentMapperImpl;
import greenatom.message.ResponseMessage;
import greenatom.model.Document;
import greenatom.model.User;
import greenatom.service.UserDocumentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentMapper documentMapper;
    private final UserDocumentsServiceImpl userDocumentsServiceImpl;

    @Autowired
    public DocumentController(DocumentMapper documentMapper,
                              UserDocumentsServiceImpl userDocumentsService
    ) {
        this.documentMapper = documentMapper;
        this.userDocumentsServiceImpl = userDocumentsService;
    }

    // Метод для загрузки пользователем документа на сервер
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseMessage> createDocument(
            @AuthenticationPrincipal User user,
            @RequestBody DocumentDto documentDto,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String responseMessage = "";

        try {
            //Document newDocument = documentMapper.toDocumentByDocumentDto(documentDto);
            Document newDocument = new Document(
                    file.getOriginalFilename(),
                    file.getBytes(),
                    documentDto.getAttributesList(),
                    user);
            userDocumentsServiceImpl.uploadDocument(newDocument);
            responseMessage = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(responseMessage));
        } catch (Exception e) {
            responseMessage = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage(responseMessage));
        }
    }


    /*@GetMapping("/{id}")
    public ResponseEntity<UserDocumentsDto> getDocument(@PathVariable("id") Long id, UserDocuments userDocuments) {
        Optional<UserDocuments> userDocumentById = userDocumentsServiceImpl.getUserDocumentById(id, userDocuments);

        return ResponseEntity.ok(documentMapper.toDocumentByIdDto(userDocumentById));
    }*/

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
