package org.coderscampus.AssignmnetSubmissionApp.dto;

import org.coderscampus.AssignmnetSubmissionApp.enums.AssignmentEnum;
import org.coderscampus.AssignmnetSubmissionApp.enums.AssignmentStatusEnum;
import org.coderscampus.AssignmnetSubmissionApp.enums.JavaFoundationsAssignmentEnum;
import org.coderscampus.AssignmnetSubmissionApp.model.Assignment;

public class JavaFoundationsAssignmentResponseDto implements AssignmentResponseDto {

    private Assignment assignment;
    private AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();
    private JavaFoundationsAssignmentEnum[] javaFoundationsAssignmentEnums = JavaFoundationsAssignmentEnum.values();

    public JavaFoundationsAssignmentResponseDto(Assignment assignment) {
        super();
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public AssignmentEnum[] getAssignmentEnums() {
        return javaFoundationsAssignmentEnums;
    }

    public AssignmentStatusEnum[] getStatusEnums() {
        return statusEnums;
    }
}
