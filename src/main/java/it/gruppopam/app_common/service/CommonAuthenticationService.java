package it.gruppopam.app_common.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.gruppopam.app_common.model.User;
import it.gruppopam.app_common.utils.CommonAppPreferences;

@Singleton
public class CommonAuthenticationService {

    private final CommonAppPreferences appPreference;

    @Inject
    public CommonAuthenticationService(CommonAppPreferences appPreference) {
        this.appPreference = appPreference;
    }

    public boolean login(String username, String password) {
        User requestedUser = new User(username, password);
        return authenticate(requestedUser, appPreference.getAdminUserDetails()) ||
                authenticate(requestedUser, appPreference.getStoreUserDetails());
    }

    private boolean authenticate(User requestedUser, User existingUserCred) {
        boolean authenticated = requestedUser.equals(existingUserCred);
        if (authenticated) {
            appPreference.setLoggedInUser(requestedUser.getName());
        }
        return authenticated;
    }

    public void logout() {
        appPreference.removeUsername();
    }

    public boolean loginAsStoreUser() {
        return login(appPreference.getStoreUserDetails().getName(), appPreference.getStoreUserDetails().getPassword());
    }

}
