package it.gruppopam.app_common.mapper;

import java.util.List;

@SuppressWarnings("PMD.GenericsNaming")
public interface BaseMapper<Dto, Entity> {

    Entity mapToModel(Dto dto);

    List<Entity> mapToModel(List<Dto> dto);

    Dto mapToDto(Entity entity);

    List<Dto> mapToDto(List<Entity> entities);
}
