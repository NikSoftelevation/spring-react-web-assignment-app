package com.coderscampus.AssisgnmentSubmissionApp.web;

import com.coderscampus.AssisgnmentSubmissionApp.model.Assignment;
import com.coderscampus.AssisgnmentSubmissionApp.model.User;
import com.coderscampus.AssisgnmentSubmissionApp.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("")
    public ResponseEntity<?> createAssignment(@AuthenticationPrincipal User user) {
        Assignment createdAssignment = assignmentService.save(user);
        return ResponseEntity.ok(createdAssignment);
    }
}