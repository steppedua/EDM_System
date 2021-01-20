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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDocumentsServiceImpl implements UserDocumentsService {

    private final UserDocumentsRepository userDocumentsRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public UserDocumentsServiceImpl(UserDocumentsRepository userDocumentsRepository,
                                    DocumentRepository documentRepository
    ) {
        this.userDocumentsRepository = userDocumentsRepository;
        this.documentRepository = documentRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public Optional<UserDocuments> createUserFolder(UserDocuments userDocuments) {

        Optional<UserDocuments> userDocumentsFromDb = userDocumentsRepository.findById(userDocuments.getId());

        if (userDocumentsFromDb.isPresent()) {
            return Optional.empty();
        }

        UserDocuments userDocumentsForDb = new UserDocuments(userDocuments.getOwner());

        return Optional.of(userDocumentsRepository.save(userDocumentsForDb));
    }

    @Override
    public Optional<UserDocuments> addDocument(MultipartFile file,
                                               UserDocuments userDocuments,
                                               User user,
                                               DocumentType documentType,
                                               DocumentGroups documentGroups,
                                               List<Attributes> attributesList
    ) throws IOException {

        Document document = createDocument(
                file,
                userDocuments,
                documentType,
                documentGroups,
                attributesList
        );

        return userDocumentsRepository.findById(userDocuments.getId())
                .map(userDoc -> {
                    userDoc.setOwner(user);
                    userDoc.setDocuments(Collections.singletonList(document));

                    return userDocumentsRepository.save(userDoc);
                });
    }

    @Override
    public List<UserDocuments> getUserDocumentsList(UserDocuments userDocuments) {
        return userDocumentsRepository.findAllByOwnerId(userDocuments.getOwner().getId());
    }

    @Override
    public boolean deleteUserFolder(Long id, UserDocuments userDocuments) {
        return userDocumentsRepository.findByDocumentsIdAndOwner(id, userDocuments.getOwner()).map(userDoc -> {
            if (userDocumentsRepository.findByDocumentsIdAndOwner(id, userDocuments.getOwner()).isPresent()) {
                userDocumentsRepository.deleteById(id);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public Optional<UserDocuments> getUserDocumentById(Long id, UserDocuments userDocuments) {
        return userDocumentsRepository.findByDocumentsIdAndOwner(id, userDocuments.getOwner())
                .map(userDoc -> {
                    if (userDocumentsRepository.findByDocumentsIdAndOwner(userDoc.getId(), userDoc.getOwner()).isPresent()) {
                        return Optional.<UserDocuments>empty();
                    }
                    return userDocumentsRepository.findByDocumentsIdAndOwner(userDoc.getId(), userDoc.getOwner());
                }).orElseThrow(
                        () -> new DocumentNotFoundException("Document not found with id: id = " + id +
                                " and user: " + userDocuments.getOwner())
                );
    }

    @Override
    public boolean deleteUserDocumentById(Long id, UserDocuments userDocuments) {
        return userDocumentsRepository.findByDocumentsIdAndOwner(id, userDocuments.getOwner()).map(userDoc -> {
            if (userDocumentsRepository.findByDocumentsIdAndOwner(id, userDoc.getOwner()).isPresent()) {
                userDocumentsRepository.deleteByDocumentsIdAndOwner(userDoc.getId(), userDoc.getOwner());
                return true;
            }
            return false;
        }).orElse(false);
    }

    private Document createDocument(MultipartFile file,
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
}