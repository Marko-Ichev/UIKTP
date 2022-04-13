package team20.mk.ukim.finki.skit.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.model.enumerations.Role;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String firstName, String lastName, Role role);
}
