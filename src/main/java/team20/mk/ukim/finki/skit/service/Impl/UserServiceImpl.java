package team20.mk.ukim.finki.skit.service.Impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.model.enumerations.Role;
import team20.mk.ukim.finki.skit.repository.UserRepository;
import team20.mk.ukim.finki.skit.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String firstName, String lastName, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new IllegalArgumentException();
        if (password.equals(repeatPassword)) {
            User user = new User(username, passwordEncoder.encode(password), firstName, lastName,role);

            if (userRepository.findByUsername(username).isPresent()) {
                throw new RuntimeException();
            }

            return userRepository.save(user);
        } else throw new RuntimeException();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
