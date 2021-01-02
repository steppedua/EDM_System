package greenatom.controller;

import greenatom.dto.DocumentDto;
import greenatom.mappers.DocumentMapper;
import greenatom.model.Document;
import greenatom.model.User;
import greenatom.service.DocumentServiceImpl;
import greenatom.util.ResponseUtils;
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
    private final DocumentServiceImpl documentServiceImpl;
    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentController(DocumentServiceImpl documentService, DocumentMapper documentMapper) {
        this.documentServiceImpl = documentService;
        this.documentMapper = documentMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<DocumentDto> createDocument(
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        documentServiceImpl.createDocument(file, user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        Optional<Document> document = documentServiceImpl.getDocumentById(id);

        return ResponseEntity.ok(documentMapper.toDocumentByIdDto(document.get()));
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getDocumentList(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(documentMapper.toDocumentsListDto(documentServiceImpl.getDocumentList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDocumentById(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        return ResponseUtils.responseEntityOf(documentServiceImpl.removeDocumentById(id));
    }
}
