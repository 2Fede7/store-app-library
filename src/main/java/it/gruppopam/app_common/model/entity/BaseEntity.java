package it.gruppopam.app_common.model.entity;

import androidx.room.PrimaryKey;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    Long id;
}
