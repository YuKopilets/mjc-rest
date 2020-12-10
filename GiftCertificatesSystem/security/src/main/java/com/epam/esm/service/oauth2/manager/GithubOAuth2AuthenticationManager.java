package com.epam.esm.service.oauth2.manager;

import com.epam.esm.entity.OAuth2GithubUser;
import com.epam.esm.entity.RegistrationType;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.oauth2.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.util.UserAuthenticationAttributeConstant.*;

/**
 * The type Github authentication manager.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see AbstractOAuth2AuthenticationManager
 */
@Component
public class GithubOAuth2AuthenticationManager extends AbstractOAuth2AuthenticationManager {
    public GithubOAuth2AuthenticationManager(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    protected String getSubFromAttributes(Map<String, Object> attributes) {
        return ((Integer) attributes.get(GITHUB_USER_SUB)).toString();
    }

    @Override
    protected Optional<User> findExistsUser(String sub) {
        return userRepository.findGithubUserBySub(sub);
    }

    @Override
    protected OAuth2GithubUser buildOAuth2User(Map<String, Object> attributes) {
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

    @Override
    protected Map<String, Object> buildUserAttributes(User user) {
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

    @Override
    public String getAuthenticationName() {
        return OAuth2Authentication.GITHUB.name();
    }
}
