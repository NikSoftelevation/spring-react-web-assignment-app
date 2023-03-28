package org.coderscampus.AssignmnetSubmissionApp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum JavaFoundationsAssignmentEnum implements AssignmentEnum {
    ASSIGNMENT_1(1, "Compounding Interest Calculator"),
    ASSIGNMENT_2(2, "Job Data Parsing & Filtering");
    private int assignmentNum;
    private String assignmentName;


    @Override
    public int getAssignmentNum() {
        return assignmentNum;
    }

    @Override
    public String getAssignmentName() {
        return assignmentName;
    }
}
