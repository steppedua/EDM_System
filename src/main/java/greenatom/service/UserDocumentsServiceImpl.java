package greenatom.service;

import greenatom.model.*;
import greenatom.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDocumentsServiceImpl implements UserDocumentsService {

    private final DocumentRepository userDocumentsRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public UserDocumentsServiceImpl(DocumentRepository userDocumentsRepository,
                                    DocumentRepository documentRepository
    ) {
        this.userDocumentsRepository = userDocumentsRepository;
        this.documentRepository = documentRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void uploadDocument(Document document) {
        userDocumentsRepository.save(document);
    }

    @Override
    public Optional<Document> getUserDocumentById(Long id, User user) {
        return userDocumentsRepository.findDocumentByIdAndOwner(id, user);
    }

    @Override
    public List<Document> getUserDocumentsList(User user) {
        return null;
    }

    @Override
    public boolean deleteUserDocumentById(Long id) {
        return false;
    }

    /*@Override
    public Optional<UserDocuments> createUserFolder(UserDocuments userDocuments) {

        Optional<UserDocuments> userDocumentsFromDb
                = userDocumentsRepository.findById(userDocuments.getId());

        if (userDocumentsFromDb.isPresent()) {
            return Optional.empty();
        }

        UserDocuments userDocumentsForDb = new UserDocuments(userDocuments.getOwner());

        return Optional.of(userDocumentsRepository.save(userDocumentsForDb));
    }*/

    /*@Override
    public Optional<Document> addDocument(MultipartFile file,
                                               User user,
                                               DocumentType documentType,
                                               DocumentGroups documentGroups,
                                               List<Attributes> attributesList
    ) throws IOException {

        Document document = createDocument(
                file,
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
    }*/

    /*@Override
    public List<Document> getUserDocumentsList(User user) {
        return userDocumentsRepository.findAllByOwnerId(userDocuments.getOwner().getId());
    }*/

    /*@Override
    public boolean deleteUserFolder(Long id, UserDocuments userDocuments) {
        return userDocumentsRepository.findByDocumentsIdAndOwner(id, userDocuments.getOwner()).map(userDoc -> {
            if (userDocumentsRepository.findByDocumentsIdAndOwner(id, userDocuments.getOwner()).isPresent()) {
                userDocumentsRepository.deleteById(id);
                return true;
            }
            return false;
        }).orElse(false);
    }*/

    /*@Override
    public Optional<Document> getUserDocumentById(Long id) {
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
    }*/

    /*@Override
    public boolean deleteUserDocumentById(Long id) {
        return userDocumentsRepository.findByDocumentsIdAndOwner(id, userDocuments.getOwner()).map(userDoc -> {
            if (userDocumentsRepository.findByDocumentsIdAndOwner(id, userDoc.getOwner()).isPresent()) {
                userDocumentsRepository.deleteByDocumentsIdAndOwner(userDoc.getId(), userDoc.getOwner());
                return true;
            }
            return false;
        }).orElse(false);
    }*/

    /*private Document createDocument(MultipartFile file,
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
                documentType,
                documentGroups,
                attributesList
        );

        Files.createFile(Path.of(uploadPath + File.separator + file.getOriginalFilename()));

        return documentRepository.saveAndFlush(document);
    }*/
}