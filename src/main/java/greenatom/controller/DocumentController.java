package greenatom.controller;

import greenatom.dto.FullUserDto;
import greenatom.dto.UserDocumentsDto;
import greenatom.mappers.UserDocumentsMapper;
import greenatom.model.*;
import greenatom.service.UserDocumentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final UserDocumentsMapper userDocumentsMapper;
    private final UserDocumentsServiceImpl userDocumentsServiceImpl;

    @Autowired
    public DocumentController(UserDocumentsMapper userDocumentsMapper,
                              UserDocumentsServiceImpl userDocumentsService
    ) {
        this.userDocumentsMapper = userDocumentsMapper;
        this.userDocumentsServiceImpl = userDocumentsService;
    }

    //multipart/form-data; boundary=<calculated when request is sent>

//    @RequestPart("attributes") List<Attributes> attributes,
//    @RequestPart("type") DocumentType type,
//    @RequestPart("groups") DocumentGroups groups,
//    @RequestPart("userDocuments") UserDocuments userDocuments

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<UserDocumentsDto> createDocument(
            @AuthenticationPrincipal User user,
            @RequestPart("file") MultipartFile file,
            @RequestPart("documentProperties") FullUserDto fullUserDto
    ) throws IOException {

        userDocumentsServiceImpl.addDocument(
                file,
                fullUserDto.getUserDocuments(),
                user,
                fullUserDto.getType(),
                fullUserDto.getGroups(),
                fullUserDto.getAttributes()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON).build();
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .contentType(MediaType.APPLICATION_JSON).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDocumentsDto> getDocument(@PathVariable("id") Long id, UserDocuments userDocuments) {
        Optional<UserDocuments> userDocumentById = userDocumentsServiceImpl.getUserDocumentById(id, userDocuments);

        return ResponseEntity.ok(userDocumentsMapper.toDocumentByIdDto(userDocumentById));
    }

//    @GetMapping
//    public ResponseEntity<List<UserDocumentsDto>> getDocumentList(@AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(userDocumentsMapper.toDocumentsListDto(documentServiceImpl.getDocumentList()));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> removeDocumentById(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
//        return ResponseUtils.responseEntityOf(documentServiceImpl.removeDocumentById(id));
//    }
}
