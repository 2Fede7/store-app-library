package it.gruppopam.app_common.utils;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import it.gruppopam.app_common.model.User;
import it.gruppopam.app_common.model.Users;
import it.gruppopam.app_common.service.configurations.ConfigurationStepName;

import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_ID;
import static it.gruppopam.app_common.utils.CommonAppPreferences.PrefKeyUtils.ARE_PULL_JOBS_ENABLED;
import static it.gruppopam.app_common.utils.CommonAppPreferences.PrefKeyUtils.ARE_PUSH_JOBS_ENABLED;
import static it.gruppopam.app_common.utils.CommonAppPreferences.PrefKeyUtils.ENABLED_STORES_NUMBER;
import static it.gruppopam.app_common.utils.CommonAppPreferences.PrefKeyUtils.STORE_ID_KEY;
import static it.gruppopam.app_common.utils.CommonAppPreferences.PrefKeyUtils.STORE_INSEGNA;
import static it.gruppopam.app_common.utils.CommonAppPreferences.PrefKeyUtils.STORE_ORDINAL_NUMBER;

public abstract class CommonAppPreferences {

    private static final long STORE_ID_NOT_PRESENT = -1L;
    protected final SharedPreferences sharedPreferences;

    public CommonAppPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public Integer getStoreOrdinalNumber() {
        String storeOrdinalNumber = sharedPreferences.getString(STORE_ORDINAL_NUMBER, null);
        return StringUtils.isNullOrEmpty(storeOrdinalNumber) ? null : Integer.valueOf(storeOrdinalNumber);
    }

    public Integer getEnabledStoresNumber() {
        String enabledStoresNumber = sharedPreferences.getString(ENABLED_STORES_NUMBER, null);
        return StringUtils.isNullOrEmpty(enabledStoresNumber) ? null : Integer.valueOf(enabledStoresNumber);
    }

    public boolean isStoreIdPresent() {
        return getStoreId() != STORE_ID_NOT_PRESENT;
    }

    public long getStoreId() {
        final String storeIdInPreferences = sharedPreferences.getString(STORE_ID_KEY, "");
        return storeIdInPreferences.isEmpty() ? STORE_ID_NOT_PRESENT : Long.parseLong(storeIdInPreferences);
    }

    public void setStoreId(String storeId) {
        sharedPreferences.edit().putString(STORE_ID_KEY, storeId).apply();
    }

    public void removeStoreId() {
        sharedPreferences.edit().remove(STORE_ID_KEY).apply();
    }

    public void setDesiredAppVersion(int versionId) {
        sharedPreferences.edit().putInt(DESIRED_VERSION_ID, versionId).apply();
    }

    public void setCurrentAppVersionId(Long versionId) {
        sharedPreferences.edit().putLong(PrefKeyUtils.CURRENT_VERSION_ID, versionId).apply();
    }

    public void setCurrentAppVersionType(String versionType) {
        sharedPreferences.edit().putString(PrefKeyUtils.CURRENT_VERSION_TYPE, versionType).apply();
    }

    public int getDesiredAppVersionId() {
        return sharedPreferences.getInt(DESIRED_VERSION_ID, -1);
    }

    public void setShouldIgnoreProfile(boolean shouldIgnoreProfile) {
        sharedPreferences.edit().putBoolean(PrefKeyUtils.SHOULD_IGNORE_PROFILE, shouldIgnoreProfile).apply();
    }

    public void createAdminUser(String userName, String password) {
        sharedPreferences.edit().putString(PrefKeyUtils.ADMIN_USER_NAME, userName)
                .putString(PrefKeyUtils.ADMIN_USER_PASSWORD, password)
                .apply();
    }

    public void createStoreUser(String userName, String password) {
        sharedPreferences.edit().putString(PrefKeyUtils.STORE_USER_NAME, userName)
                .putString(PrefKeyUtils.STORE_USER_PASSWORD, password)
                .apply();
    }

    public User getAdminUserDetails() {
        return new User(sharedPreferences.getString(PrefKeyUtils.ADMIN_USER_NAME, ""),
                sharedPreferences.getString(PrefKeyUtils.ADMIN_USER_PASSWORD, ""));
    }

    public User getStoreUserDetails() {
        return new User(sharedPreferences.getString(PrefKeyUtils.STORE_USER_NAME, ""),
                sharedPreferences.getString(PrefKeyUtils.STORE_USER_PASSWORD, ""));
    }

    public String getLoggedInUserName() {
        return sharedPreferences.getString(PrefKeyUtils.LOGGED_IN_USER, "");
    }

    public Users getLoggedInUser() {
        return Users.from(getLoggedInUserName());
    }

    public boolean isUserSessionActive() {
        return !getLoggedInUserName().isEmpty();
    }

    public void setLoggedInUser(String username) {
        sharedPreferences.edit().putString(PrefKeyUtils.LOGGED_IN_USER, username).apply();
    }

    public boolean isAdmin() {
        return Users.ADMIN.equals(getLoggedInUser());
    }

    public void removeUsername() {
        sharedPreferences.edit().remove(PrefKeyUtils.LOGGED_IN_USER).apply();
    }

    public void disablePullJobs() {
        sharedPreferences.edit().putBoolean(ARE_PULL_JOBS_ENABLED, false).apply();
    }

    public void disablePushJobs() {
        sharedPreferences.edit().putBoolean(ARE_PUSH_JOBS_ENABLED, false).apply();
    }

    public void enablePullJobs() {
        sharedPreferences.edit().putBoolean(ARE_PULL_JOBS_ENABLED, true).apply();
    }

    public void enablePushJobs() {
        sharedPreferences.edit().putBoolean(ARE_PUSH_JOBS_ENABLED, true).apply();
    }

    public String getStoreInsegna() {
        return sharedPreferences.getString(STORE_INSEGNA, null);
    }

    public void markStepAsCompleted(ConfigurationStepName step) {
        sharedPreferences.edit().putBoolean(step.name(), true).apply();
    }

    public boolean isStepCompleted(ConfigurationStepName step) {
        return sharedPreferences.getBoolean(step.name(), false);
    }

    public void setAppAutoconfigured() {
        sharedPreferences.edit().putBoolean(PrefKeyUtils.APP_AUTOCONFIGURED, true).apply();
    }

    public boolean isAppAutoconfigured() {
        return sharedPreferences.getBoolean(PrefKeyUtils.APP_AUTOCONFIGURED, false);
    }

    public void loadServerSettings(HashMap<String, String> serverSettings) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : serverSettings.entrySet()) {
            edit.putString(entry.getKey(), entry.getValue());
        }
        edit.apply();
    }

    public abstract String getDesiredAppVersionType();

    public abstract Long getCurrentAppVersionId();

    public abstract String getCurrentAppVersionType();

    public abstract boolean shouldIgnoreProfile();

    public static final class PrefKeyUtils {
        public static final String APP_AUTOCONFIGURED = "isAppAutoconfigured";
        public static final String STORE_USER_NAME = "storeUserName";
        public static final String STORE_USER_PASSWORD = "storeUserPassword";
        public static final String ADMIN_USER_NAME = "adminUserName";
        public static final String ADMIN_USER_PASSWORD = "adminUserPassword";
        public static final String LOGGED_IN_USER = "loggedInUser";
        public static final String STORE_ORDINAL_NUMBER = "store_ordinal_number";
        public static final String ENABLED_STORES_NUMBER = "enabled_stores_number";
        public static final String STORE_ID_KEY = "storeIdPref";
        public static final String STORE_INSEGNA = "insegna";
        public static final String DESIRED_VERSION_ID = "desiredVersionId";
        public static final String CURRENT_VERSION_ID = "currentVersionId";
        public static final String CURRENT_VERSION_TYPE = "currentVersionType";
        public static final String SHOULD_IGNORE_PROFILE = "shouldIgnoreProfile";
        public static final String ARE_PULL_JOBS_ENABLED = "arePullJobsEnabled";
        public static final String ARE_PUSH_JOBS_ENABLED = "arePushJobsEnabled";
    }
}
