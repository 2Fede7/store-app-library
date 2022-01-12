package it.gruppopam.app_common.model;

import com.annimon.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Users {
    ADMIN("admin"),
    STORE_USER("store_user");

    private String username;

    public static Users from(String username) {
        return Stream.of(Users.values()).filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }
}
