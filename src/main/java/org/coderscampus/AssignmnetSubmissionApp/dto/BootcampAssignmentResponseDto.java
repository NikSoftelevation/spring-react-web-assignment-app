package org.coderscampus.AssignmnetSubmissionApp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.coderscampus.AssignmnetSubmissionApp.enums.AssignmentEnum;
import org.coderscampus.AssignmnetSubmissionApp.enums.AssignmentStatusEnum;
import org.coderscampus.AssignmnetSubmissionApp.enums.BootcampAssignmentEnum;
import org.coderscampus.AssignmnetSubmissionApp.model.Assignment;

public class BootcampAssignmentResponseDto implements AssignmentResponseDto {

    public BootcampAssignmentResponseDto() {
    }

    public BootcampAssignmentResponseDto(Assignment assignment) {
        super();
        this.assignment = assignment;
    }

    private Assignment assignment;
    private BootcampAssignmentEnum[] bootcampAssignmentEnums = BootcampAssignmentEnum.values();
    private AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();


    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public AssignmentEnum[] getAssignmentEnums() {
        return bootcampAssignmentEnums;
    }

    public AssignmentStatusEnum[] getStatusEnums() {
        return statusEnums;
    }
}
