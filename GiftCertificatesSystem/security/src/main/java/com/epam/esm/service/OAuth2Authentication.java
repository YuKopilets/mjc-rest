package com.epam.esm.service;

import com.epam.esm.entity.OAuth2GithubUser;
import com.epam.esm.entity.OAuth2GoogleUser;
import com.epam.esm.entity.RegistrationType;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.exception.OAuth2UserNotFoundException;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public enum OAuth2Authentication {
    GOOGLE(OAuth2Authentication::getGoogleSub,
            UserRepository::findGoogleUserBySub,
            OAuth2Authentication::buildOAuth2GoogleUser,
            OAuth2Authentication::buildOAuth2GoogleUserAttributes
    ),
    GITHUB(OAuth2Authentication::getGithubSub,
            UserRepository::findGithubUserBySub,
            OAuth2Authentication::buildOAuth2GithubUser,
            OAuth2Authentication::buildOAuth2GithubUserAttributes
    );

    private final Function<Map<String, Object>, String> getSubFunction;
    private final OptionalUserFunction findUserFunction;
    private final Function<Map<String, Object>, User> buildUserFunction;
    private final Function<User, Map<String, Object>> buildUserAttributesFunction;

    public OAuth2User doAuthentication(UserRepository userRepository, String sub) throws OAuth2UserNotFoundException {
        User user = findExistsUser(userRepository, sub).orElseThrow(() -> new OAuth2UserNotFoundException(sub));
        Map<String, Object> attributes = buildUserAttributes(user);
        return new DefaultOAuth2User(user.getRoles(), attributes, "sub");
    }

    public void doRegistration(UserRepository userRepository, OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        String sub = getSubFromAttributes(attributes);
        Optional<User> optionalUser = findExistsUser(userRepository, sub);
        if (!optionalUser.isPresent()) {
            User user = buildOAuth2User(attributes);
            userRepository.save(user);
        }
    }

    private String getSubFromAttributes(Map<String, Object> attributes) {
        return this.getSubFunction.apply(attributes);
    }

    private Optional<User> findExistsUser(UserRepository userRepository, String sub) {
        return this.findUserFunction.apply(userRepository, sub);
    }

    private User buildOAuth2User(Map<String, Object> attributes) {
        return this.buildUserFunction.apply(attributes);
    }

    private Map<String, Object> buildUserAttributes(User user) {
        return this.buildUserAttributesFunction.apply(user);
    }

    private static String getGoogleSub(Map<String, Object> attributes) {
        return (String) attributes.get("sub");
    }

    private static String getGithubSub(Map<String, Object> attributes) {
        return ((Integer) attributes.get("id")).toString();
    }

    private static OAuth2GoogleUser buildOAuth2GoogleUser(Map<String, Object> attributes) {
        return OAuth2GoogleUser.builder()
                .sub((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .avatarUrl((String) attributes.get("picture"))
                .active(true)
                .roles(Collections.singleton(UserRole.USER))
                .registrationTypes(Collections.singleton(RegistrationType.GOOGLE))
                .build();
    }

    private static OAuth2GithubUser buildOAuth2GithubUser(Map<String, Object> attributes) {
        return OAuth2GithubUser.builder()
                .sub(((Integer) attributes.get("id")).toString())
                .login((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .avatarUrl((String) attributes.get("avatar_url"))
                .active(true)
                .roles(Collections.singleton(UserRole.USER))
                .registrationTypes(Collections.singleton(RegistrationType.GITHUB))
                .build();
    }

    private static Map<String, Object> buildOAuth2GoogleUserAttributes(User user) {
        OAuth2GoogleUser googleUser = (OAuth2GoogleUser) user;
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", googleUser.getId());
        attributes.put("active", googleUser.getActive());
        attributes.put("sub", googleUser.getSub());
        attributes.put("name", googleUser.getName());
        attributes.put("email", googleUser.getEmail());
        attributes.put("avatar_url", googleUser.getAvatarUrl());
        return attributes;
    }

    private static Map<String, Object> buildOAuth2GithubUserAttributes(User user) {
        OAuth2GithubUser githubUser = (OAuth2GithubUser) user;
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", githubUser.getId());
        attributes.put("active", githubUser.getActive());
        attributes.put("sub", githubUser.getSub());
        attributes.put("login", githubUser.getLogin());
        attributes.put("email", githubUser.getEmail());
        attributes.put("avatar_url", githubUser.getAvatarUrl());
        return attributes;
    }
}
