package greenatom.controller;

import greenatom.config.jwt.JwtProvider;
import greenatom.dto.DocumentDto;
import greenatom.mappers.DocumentMapper;
import greenatom.model.Document;
import greenatom.model.User;
import greenatom.service.DocumentsServiceImpl;
import greenatom.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentMapper documentMapper;
    private final DocumentsServiceImpl documentsServiceImpl;
    private final JwtProvider jwtProvider;

    @Autowired
    public DocumentController(DocumentMapper documentMapper,
                              DocumentsServiceImpl documentsServiceImpl,
                              JwtProvider jwtProvider) {
        this.documentMapper = documentMapper;
        this.documentsServiceImpl = documentsServiceImpl;
        this.jwtProvider = jwtProvider;
    }

    // Метод для загрузки пользователем документа на сервер
    @PostMapping(value = "/create")
    public ResponseEntity<Optional<Document>> createDocument(
            @RequestBody DocumentDto documentDto,
            @AuthenticationPrincipal User user
    ) throws IOException {

        Document newDocument = new Document(
                documentDto.getDocumentName(),
                documentDto.getFile(),
                documentDto.getAttributesList(),
                user);

        Optional<Document> document = documentsServiceImpl.uploadDocument(newDocument);

        return ResponseEntity.status(HttpStatus.OK).body(document);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Document>> getDocument(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user
    ) {
        Optional<Document> userDocumentById = documentsServiceImpl.getUserDocumentById(id, user);

        return ResponseEntity.ok().body(userDocumentById);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Document>> getDocumentList(@AuthenticationPrincipal User user) {

        List<Document> userDocumentsList = documentsServiceImpl.getUserDocumentsList(user);

        return ResponseEntity.ok().body(userDocumentsList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDocumentById(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        return ResponseUtils.responseEntityOf(documentsServiceImpl.deleteDocumentByIdAndOwnerId(id, user));
    }
}
