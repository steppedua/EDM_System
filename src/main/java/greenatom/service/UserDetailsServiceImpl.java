package greenatom.service;

import greenatom.mappers.UserMapper;
import greenatom.model.Role;
import greenatom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserDetailsServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " doesn't exists"));

//        List<GrantedAuthority> userAuthorities = user.getRoles()
//                .stream().map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());

//        List<GrantedAuthority> userAuthorities = new ArrayList<>();
//        if(user.getRoles() != null) {
//            user.getRoles().stream()
//                    .map(Role::getName)
//                    .map(SimpleGrantedAuthority::new)
//                    .forEach(userAuthorities::add);
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), userAuthorities);

//        return new UserDetailsImpl(user, userAuthorities);
        return new UserDetailsImpl(user);
    }
}
