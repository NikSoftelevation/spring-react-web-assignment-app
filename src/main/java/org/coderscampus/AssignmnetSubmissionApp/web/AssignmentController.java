package org.coderscampus.AssignmnetSubmissionApp.web;

import org.coderscampus.AssignmnetSubmissionApp.dto.AssignmentResponseDto;
import org.coderscampus.AssignmnetSubmissionApp.dto.BootcampAssignmentResponseDto;
import org.coderscampus.AssignmnetSubmissionApp.dto.JavaFoundationsAssignmentResponseDto;
import org.coderscampus.AssignmnetSubmissionApp.dto.UserKeyDto;
import org.coderscampus.AssignmnetSubmissionApp.enums.AuthorityEnum;
import org.coderscampus.AssignmnetSubmissionApp.model.Assignment;
import org.coderscampus.AssignmnetSubmissionApp.model.User;
import org.coderscampus.AssignmnetSubmissionApp.service.AssignmentService;
import org.coderscampus.AssignmnetSubmissionApp.service.OrderService;
import org.coderscampus.AssignmnetSubmissionApp.service.UserService;
import org.coderscampus.AssignmnetSubmissionApp.util.AuthorityUtil;
import org.coderscampus.professo.domain.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.coderscampus.AssignmnetSubmissionApp.service.OrderService.BOOTCAMP_OFFER_IDS;
import static org.coderscampus.AssignmnetSubmissionApp.service.OrderService.JAVA_FOUNDATIONS_OFFER_ID;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://assignments.coderscampus.com"}, allowCredentials = "true")

public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createAssignment(@AuthenticationPrincipal User user) {
        Assignment newAssignment = assignmentService.save(user);

        return ResponseEntity.ok(newAssignment);
    }

    @GetMapping("")
    public ResponseEntity<?> getAssignments(@AuthenticationPrincipal User user) {
        Set<Assignment> assignmentsByUser = assignmentService.findByUser(user);
        return ResponseEntity.ok(assignmentsByUser);
    }

    @GetMapping("{assignmentId}")
    public ResponseEntity<?> getAssignment(@PathVariable Long assignmentId, @AuthenticationPrincipal User user) {
        Optional<Assignment> assignmentOpt = assignmentService.findById(Math.toIntExact(assignmentId));

        Set<Offer> offers = orderService.findStudentOrdersByUserId((long) assignmentOpt.get().getUser().getId());
        boolean isBootcampStudent = offers.stream()
                .anyMatch(offer -> BOOTCAMP_OFFER_IDS.contains(offer.getId()));
        boolean isJavaFoundationsStudent = offers.stream()
                .anyMatch(offer -> offer.getId().equals(JAVA_FOUNDATIONS_OFFER_ID));

        if (isBootcampStudent) {

            AssignmentResponseDto response = new BootcampAssignmentResponseDto(assignmentOpt.orElse(new Assignment()));
            return ResponseEntity.ok(response);
        } else if (isJavaFoundationsStudent) {
            AssignmentResponseDto response = new JavaFoundationsAssignmentResponseDto(assignmentOpt.orElse(new Assignment()));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new BootcampAssignmentResponseDto());
    }

    @PutMapping("{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long assignmentId,
                                              @RequestBody Assignment assignment,
                                              @AuthenticationPrincipal User user) {
        // add the code reviewer to this assignment if it was claimed
        if (assignment.getCodeReviewer() != null) {
            User codeReviewer = assignment.getCodeReviewer();
            codeReviewer = userService.findUserByUsername(codeReviewer.getUsername()).orElseThrow();

            if (AuthorityUtil.hasRole(AuthorityEnum.ROLE_CODE_REVIEWER.name(), codeReviewer)) {
                assignment.setCodeReviewer(codeReviewer);
            }
        }
        Assignment updatedAssignment = assignmentService.save(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allAssignments() {
        Map<UserKeyDto, Set<Assignment>> allAssignments = assignmentService.findAll();
        return ResponseEntity.ok(allAssignments);
    }

}