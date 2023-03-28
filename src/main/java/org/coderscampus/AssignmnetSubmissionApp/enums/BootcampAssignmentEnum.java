package org.coderscampus.AssignmnetSubmissionApp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
@NoArgsConstructor
public enum BootcampAssignmentEnum implements AssignmentEnum {

    ASSIGNMENT_1(1, "HTML Assignment"),
    ASSIGNMENT_2(2, "Guessing Game"),
    ASSIGNMENT_3(3, "User Login"),
    ASSIGNMENT_4(4, "Student Course List"),
    ASSIGNMENT_5(5, "Custom Array List"),
    ASSIGNMENT_6(6, "Reports with Streams"),
    ASSIGNMENT_7(7, "Unit Testing"),
    ASSIGNMENT_8(8, "Multi-threading"),
    ASSIGNMENT_9(9, "Spring MVC"),
    ASSIGNMENT_10(10, "RESTful Service"),
    ASSIGNMENT_11(11, "Full-Stack with Thymeleaf"),
    ASSIGNMENT_12(12, "Reports with SQL"),
    ASSIGNMENT_13(13, "Online Bank"),
    ASSIGNMENT_14(14, "Chatting with JS"),
    FINAL_PROJECT(15, "Final Project");


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
