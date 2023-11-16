package hexlet.code.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import hexlet.code.dto.LabelDTO;
import hexlet.code.model.Label;

@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class LabelMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Label map(LabelDTO model);

    @InheritInverseConfiguration(name = "map")
    public abstract LabelDTO map(Label model);

    @InheritConfiguration
    public abstract void update(LabelDTO update, @MappingTarget Label destination);
}

