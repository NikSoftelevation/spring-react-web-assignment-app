package org.coderscampus.dto;

import lombok.*;
import org.coderscampus.enums.AssignmentEnum;
import org.coderscampus.enums.AssignmrentStatusEnum;
import org.coderscampus.model.Assignment;

@Getter
@Setter
public class AssignmentResponseDto {
    private Assignment assignment;
    private AssignmentEnum[] assignmentEnums = AssignmentEnum.values();

    private AssignmrentStatusEnum[] statusEnums = AssignmrentStatusEnum.values();

    public AssignmentResponseDto(Assignment assignment) {
        super();
        this.assignment = assignment;
    }
}