package team20.mk.ukim.finki.skit.service;

import team20.mk.ukim.finki.skit.model.User;

public interface AuthService {
    User login(String username, String password);

}
