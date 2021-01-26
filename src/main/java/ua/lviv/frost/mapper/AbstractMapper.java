package ua.lviv.frost.mapper;

public abstract class AbstractMapper<ENTITY, DTO> {

    public abstract DTO toDto(ENTITY entity);

    public abstract ENTITY toEntity(DTO dto);
}
