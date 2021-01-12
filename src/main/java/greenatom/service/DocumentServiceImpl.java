package greenatom.service;

import greenatom.exception.DocumentNotFoundException;
import greenatom.model.*;
import greenatom.repository.DocumentRepository;
import greenatom.repository.UserDocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


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
    public Document createDocument(MultipartFile file,
                                   UserDocuments userDocuments,
                                   DocumentType documentType,
                                   DocumentGroups documentGroups,
                                   List<Attributes> attributesList
    ) throws IOException {

        Optional<Document> documentFromDB = documentRepository.findByName(file.getOriginalFilename());

        if (documentFromDB.isPresent()) {
            Optional.empty(); //есть проблемы с Optional
        }

        Document document = new Document(
                file.getOriginalFilename(),
                file.getBytes(),
                userDocuments,
                documentType,
                documentGroups,
                attributesList
        );

        Files.createFile(Path.of(uploadPath + File.separator + file.getOriginalFilename()));

        return documentRepository.saveAndFlush(document);
    }

    @Override
    public List<Document> getDocumentList() {
        return documentRepository.findAll();
    }

    @Override
    public Optional<Document> getDocumentById(Long id, User user) {
        return documentRepository.findByIdAndOwner(id, user).map(document -> {
            if (documentRepository.findByIdAndOwner(document.getId(), document.getDocument().getOwner()).isEmpty()) {
                return Optional.<Document>empty();
            }
            return documentRepository.findByIdAndOwner(document.getId(), document.getDocument().getOwner());
        }).orElseThrow(() -> new DocumentNotFoundException("Document not found with id: id = " + id));
    }

    @Override
    public boolean removeDocumentById(Long id, User user) {
        return documentRepository.findByIdAndOwner(id, user).map(document -> {
            if (documentRepository.findByIdAndOwner(
                    document.getId(),
                    document.getDocument().getOwner()
            ).isPresent()) {
                documentRepository.deleteById(document.getId());
                return true;
            }
            return false;
        }).orElse(false);
    }
}