package com.example.splitwise.service;

import com.example.splitwise.model.User;
import com.example.splitwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User loadOrCreateUser(OAuth2User oAuth2User) {
        String oauthId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user;
        } else {
            User newUser = User.builder()
                    .oauthId(oauthId)
                    .email(email)
                    .name(name)
                    .role(User.Role.USER)
                    .build();
            return userRepository.save(newUser);
        }
    }
}
