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

import static com.epam.esm.service.UserAuthenticationAttributeConstant.*;

/**
 * The enum OAuth authentication contains authentication types.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
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

    /**
     * Do process of authentication oauth user.
     *
     * @param userRepository the user repository
     * @param sub            the oauth sub
     * @return the oauth user
     * @throws OAuth2UserNotFoundException the o auth 2 user not found exception
     */
    public OAuth2User doAuthentication(UserRepository userRepository, String sub) throws OAuth2UserNotFoundException {
        User user = findExistsUser(userRepository, sub).orElseThrow(() -> new OAuth2UserNotFoundException(sub));
        Map<String, Object> attributes = buildUserAttributes(user);
        return new DefaultOAuth2User(user.getRoles(), attributes, USER_ID);
    }

    /**
     * Do process of registration oauth user.
     *
     * @param userRepository the user repository
     * @param oauth2User     the oauth user
     */
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
        return (String) attributes.get(GOOGLE_USER_SUB);
    }

    private static String getGithubSub(Map<String, Object> attributes) {
        return ((Integer) attributes.get(GITHUB_USER_SUB)).toString();
    }

    private static OAuth2GoogleUser buildOAuth2GoogleUser(Map<String, Object> attributes) {
        return OAuth2GoogleUser.builder()
                .sub((String) attributes.get(GOOGLE_USER_SUB))
                .name((String) attributes.get(GOOGLE_USER_NAME))
                .email((String) attributes.get(GOOGLE_USER_EMAIL))
                .avatarUrl((String) attributes.get(GOOGLE_USER_AVATAR_URL))
                .active(true)
                .roles(Collections.singleton(UserRole.USER))
                .registrationTypes(Collections.singleton(RegistrationType.GOOGLE))
                .build();
    }

    private static OAuth2GithubUser buildOAuth2GithubUser(Map<String, Object> attributes) {
        return OAuth2GithubUser.builder()
                .sub(((Integer) attributes.get(GITHUB_USER_SUB)).toString())
                .login((String) attributes.get(GITHUB_USER_NAME))
                .email((String) attributes.get(GITHUB_USER_EMAIL))
                .avatarUrl((String) attributes.get(GITHUB_USER_AVATAR_URL))
                .active(true)
                .roles(Collections.singleton(UserRole.USER))
                .registrationTypes(Collections.singleton(RegistrationType.GITHUB))
                .build();
    }

    private static Map<String, Object> buildOAuth2GoogleUserAttributes(User user) {
        OAuth2GoogleUser googleUser = (OAuth2GoogleUser) user;
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(USER_ID, googleUser.getId());
        attributes.put(USER_ACTIVE, googleUser.getActive());
        attributes.put(GOOGLE_USER_SUB, googleUser.getSub());
        attributes.put(GOOGLE_USER_NAME, googleUser.getName());
        attributes.put(GOOGLE_USER_EMAIL, googleUser.getEmail());
        attributes.put(GOOGLE_USER_AVATAR_URL, googleUser.getAvatarUrl());
        return attributes;
    }

    private static Map<String, Object> buildOAuth2GithubUserAttributes(User user) {
        OAuth2GithubUser githubUser = (OAuth2GithubUser) user;
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(USER_ID, githubUser.getId());
        attributes.put(USER_ACTIVE, githubUser.getActive());
        attributes.put(GITHUB_USER_SUB, githubUser.getSub());
        attributes.put(GITHUB_USER_NAME, githubUser.getLogin());
        attributes.put(GITHUB_USER_EMAIL, githubUser.getEmail());
        attributes.put(GITHUB_USER_AVATAR_URL, githubUser.getAvatarUrl());
        return attributes;
    }
}
