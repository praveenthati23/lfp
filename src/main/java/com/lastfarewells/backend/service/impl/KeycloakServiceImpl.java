package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.config.KeycloakProvider;
import com.lastfarewells.backend.dto.LoginDto;
import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.exception.IAMException;
import com.lastfarewells.backend.service.IAMService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceImpl implements IAMService {

    @Value("${keycloak.realm}")
    public String realm;

    private final KeycloakProvider kcProvider;

    /**
     * Create user in Keycloak with provided user credentials
     *
     * @return keycloak userId
     */
    @Override
    public String createUser(SignupDto user) {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Response response = usersResource.create(kcUser);
        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            log.info("User created in Keycloak with id {}", userId);
            // Send email verification - Move to async method later
            // usersResource.get(userId).sendVerifyEmail();
            return userId;
        }

        throw new IAMException(response.getStatus() + " Keycloak Exception in User creation");
    }

    /**
     * Validate and generate access token for user creds
     *
     * @return access token details
     */
    @Override
    public AccessTokenResponse login(LoginDto loginDTO) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginDTO.getEmail(), loginDTO.getPassword()).build();
        try {
            return keycloak.tokenManager().getAccessToken();
        } catch (BadRequestException | NotAuthorizedException ex) {
            log.error("Keycloak Login failed!!, User login failed, invalid account", ex);
            throw new IAMException("Invalid credentials");
        }
    }

    /**
     * Initiate resend password for userId
     */
    @Override
    public void sendResetPassword(String userId) {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        usersResource.get(userId)
            .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }

    /**
     * Invalidate token while user logout for provided token
     */
    @Override
    public void logout(String token) {
        kcProvider.getInstance().tokenManager().invalidate(token);
    }

    @Override
    public void updatePassword(String userId, String password) {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);
        usersResource.get(userId)
            .resetPassword(credentialRepresentation);
    }

    @Override
    public void verifyEmail(String email) {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        List<UserRepresentation> users = usersResource.search(email, 0, 1);
        if (!users.isEmpty()) {
            UserResource userResource = usersResource.get(users.get(0).getId());
            UserRepresentation user = userResource.toRepresentation();
            user.setEmailVerified(true);
            userResource.update(user);
        }
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
