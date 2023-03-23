package org.coderscampus.dto;

import lombok.*;
import org.coderscampus.enums.AssignmentEnum;
import org.coderscampus.model.Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class AssignmentResponseDto {
    private Assignment assignment;
    private AssignmentEnum[] assignmentEnums = AssignmentEnum.values();

    public AssignmentResponseDto(Assignment assignment) {
        super();
        this.assignment = assignment;
    }
}