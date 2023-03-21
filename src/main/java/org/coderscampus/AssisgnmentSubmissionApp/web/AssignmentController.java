package org.coderscampus.AssisgnmentSubmissionApp.web;

import org.coderscampus.AssisgnmentSubmissionApp.model.Assignment;
import org.coderscampus.AssisgnmentSubmissionApp.model.User;
import org.coderscampus.AssisgnmentSubmissionApp.repository.AssignmentRepository;
import org.coderscampus.AssisgnmentSubmissionApp.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("")
    public ResponseEntity<Assignment> createAssignment(@AuthenticationPrincipal User user) {
        Assignment createdAssignment = assignmentService.save(user);
        return ResponseEntity.ok(createdAssignment);
    }

    @GetMapping("")
    public ResponseEntity<Set<Assignment>> getAssignments(@AuthenticationPrincipal User user) {
        Set<Assignment> assignmentsByUser = assignmentService.findByUser(user);
        return ResponseEntity.ok(assignmentsByUser);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<?> getAssignmentByAssignmentId(@PathVariable("assignmentId") int assignmentId, @AuthenticationPrincipal User User) {
        Assignment assignmentOpt = assignmentService.findAssignmentByAssignmentId(assignmentId).orElseThrow(() -> new RuntimeException());
        return ResponseEntity.ok(assignmentOpt);
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable("assignmentId") int assignmentId, @RequestBody Assignment assignment, @AuthenticationPrincipal User user) {
        Assignment updatedAssignment = assignmentService.updateAssignment(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

}