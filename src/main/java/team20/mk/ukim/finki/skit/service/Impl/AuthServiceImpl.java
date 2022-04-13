package team20.mk.ukim.finki.skit.service.Impl;


import org.springframework.stereotype.Service;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.repository.UserRepository;
import team20.mk.ukim.finki.skit.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new IllegalArgumentException();
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(RuntimeException::new);


    }
}
