package greenatom.service;

import greenatom.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);

    List<User> getUserList();

    Optional<User> findByUsername(String username);

    Optional<User> createUser(User user);

    boolean removeUser(Long id);
}