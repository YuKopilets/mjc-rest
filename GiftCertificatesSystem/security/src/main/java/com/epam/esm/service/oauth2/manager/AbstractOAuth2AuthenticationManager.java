package com.epam.esm.service.oauth2.manager;

import com.epam.esm.entity.User;
import com.epam.esm.exception.OAuth2UserNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.oauth2.OAuth2AuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Optional;

import static com.epam.esm.util.UserAuthenticationAttributeConstant.USER_ID;

@RequiredArgsConstructor
public abstract class AbstractOAuth2AuthenticationManager implements OAuth2AuthenticationManager {
    protected final UserRepository userRepository;

    @Override
    public void doRegistration(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        String sub = getSubFromAttributes(attributes);
        Optional<User> optionalUser = findExistsUser(sub);
        if (!optionalUser.isPresent()) {
            User user = buildOAuth2User(attributes);
            userRepository.save(user);
        }
    }

    @Override
    public OAuth2User doAuthentication(String sub) throws OAuth2UserNotFoundException {
        User user = findExistsUser(sub).orElseThrow(() -> new OAuth2UserNotFoundException(sub));
        Map<String, Object> attributes = buildUserAttributes(user);
        return new DefaultOAuth2User(user.getRoles(), attributes, USER_ID);
    }

    protected abstract String getSubFromAttributes(Map<String, Object> attributes);

    protected abstract Optional<User> findExistsUser(String sub);

    protected abstract User buildOAuth2User(Map<String, Object> attributes);

    protected abstract Map<String, Object> buildUserAttributes(User user);
}
