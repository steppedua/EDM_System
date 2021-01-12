package greenatom.service;

import greenatom.exception.DocumentNotFoundException;
import greenatom.model.*;
import greenatom.repository.UserDocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDocumentsServiceImpl implements UserDocumentsService {

    private final UserDocumentsRepository userDocumentsRepository;
    private final DocumentServiceImpl documentServiceImpl;

    @Autowired
    public UserDocumentsServiceImpl(UserDocumentsRepository userDocumentsRepository, DocumentServiceImpl documentServiceImpl) {
        this.userDocumentsRepository = userDocumentsRepository;
        this.documentServiceImpl = documentServiceImpl;
    }

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

        Document document = documentServiceImpl.createDocument(
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
                documentServiceImpl.removeDocumentById(userDoc.getId(), userDoc.getOwner());
                return true;
            }
            return false;
        }).orElse(false);
    }
}