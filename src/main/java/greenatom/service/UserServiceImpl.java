package greenatom.service;

import greenatom.dto.UserDto;
import greenatom.model.Role;
import greenatom.model.User;
import greenatom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> createUser(User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            Optional.empty();
        }

        User newUser = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));

        return Optional.of(userRepository.saveAndFlush(user));
    }

    @Override
    public boolean removeUser(Long id) {
        return userRepository.findById(id).map(user -> {
            if (userRepository.findById(user.getId()).isPresent()) {
                userRepository.deleteById(user.getId());
                return true;
            }
            return false;
        }).orElse(false);
    }
}
