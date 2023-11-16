package hexlet.code.mapper;

import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import hexlet.code.dto.TaskDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class TaskMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "taskStatusSlug", target = "taskStatus")
    @Mapping(source = "assigneeId", target = "assignee")
    @Mapping(source = "taskLabelIds", target = "labels")
    public abstract Task map(TaskDTO model);

    @InheritInverseConfiguration(name = "map")
    @Mapping(source = "taskStatus.slug", target = "taskStatusSlug")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "labels", target = "taskLabelIds")
    public abstract TaskDTO map(Task model);

    @InheritConfiguration
    public abstract void update(TaskDTO update, @MappingTarget Task destination);

    public TaskStatus toEntity(String slug) {
        return taskStatusRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Task Status has not found"));
    }
    public Set<Label> toEntity(Set<Long> labelIds) {
        if (labelIds == null) {
            return null;
        }
        return labelIds.stream()
                .map(labelId -> labelRepository.findById(labelId)
                        .orElseThrow(() -> new ResourceNotFoundException("Label with id: " + labelId + " not found")))
                .collect(Collectors.toSet());
    }

    public Set<Long> toDto(Set<Label> labels) {
        return labels.stream().map(Label::getId).collect(Collectors.toSet());
    }
}

