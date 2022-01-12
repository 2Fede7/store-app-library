package it.gruppopam.app_common.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("PMD.ShortClassName")
@Getter
@EqualsAndHashCode
@ToString
public class User {
    private String name;
    private String password;
}
