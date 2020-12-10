package com.epam.esm.service.oauth2.manager;

import com.epam.esm.entity.OAuth2GoogleUser;
import com.epam.esm.entity.RegistrationType;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.oauth2.OAuth2Authentication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.util.UserAuthenticationAttributeConstant.*;

public class GoogleOAuth2AuthenticationManager extends AbstractOAuth2AuthenticationManager {
    public GoogleOAuth2AuthenticationManager(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    protected String getSubFromAttributes(Map<String, Object> attributes) {
        return (String) attributes.get(GOOGLE_USER_SUB);
    }

    @Override
    protected Optional<User> findExistsUser(String sub) {
        return userRepository.findGoogleUserBySub(sub);
    }

    @Override
    protected OAuth2GoogleUser buildOAuth2User(Map<String, Object> attributes) {
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

    @Override
    protected Map<String, Object> buildUserAttributes(User user) {
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

    @Override
    public String getAuthenticationName() {
        return OAuth2Authentication.GOOGLE.name();
    }
}
