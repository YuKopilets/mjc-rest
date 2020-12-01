package com.epam.esm.util;

import com.epam.esm.service.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
public enum Permission {
    GET_USER("hasPermissionToGetUser", Permission::hasSuitableId);

    @Getter
    private final String permissionName;
    private final TernaryPredicate<Authentication, Object, UserDetailsService> ternaryPredicate;

    public Boolean hasPermission(Authentication authentication, Object targetObject,
                                 UserDetailsService userDetailsService) {
        return ternaryPredicate.test(authentication, targetObject, userDetailsService);
    }

    private static boolean hasSuitableId(Authentication authentication, Object targetObject,
                                  UserDetailsService userDetailsService) {
        Long id = (Long) targetObject;
        String login = authentication.getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(login);
        return userDetails.getId().equals(id);
    }
}
