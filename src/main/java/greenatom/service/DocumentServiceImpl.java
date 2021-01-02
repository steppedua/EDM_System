package greenatom.service;

import greenatom.exception.DocumentNotFoundException;
import greenatom.model.Document;
import greenatom.model.User;
import greenatom.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.io.File;


@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public Optional<Document> createDocument(MultipartFile file, User user) throws IOException {

        Optional<Document> documentFromDB = documentRepository.findByName(file.getOriginalFilename());

        if (documentFromDB.isPresent()) {
            return Optional.empty();
        }

        Document document = new Document(
                uploadPath + File.separator + file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                user
        );

        return Optional.of(documentRepository.saveAndFlush(document));
    }

    @Override
    public List<Document> getDocumentList() {
        return documentRepository.findAll();
    }

    @Override
    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id).map(docId -> {
            if (documentRepository.findById(docId.getId()).isEmpty()) {
                return Optional.<Document>empty();
            }
            return documentRepository.findById(docId.getId());
        }).orElseThrow(() -> new DocumentNotFoundException("Document not found with id: id = " + id));
    }

    @Override
    public boolean removeDocumentById(Long id) {
        return documentRepository.findById(id).map(user -> {
            if (documentRepository.findById(user.getId()).isPresent()) {
                documentRepository.deleteById(user.getId());
                return true;
            }
            return false;
        }).orElse(false);
    }
}