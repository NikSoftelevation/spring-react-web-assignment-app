package org.coderscampus.service;

import org.coderscampus.enums.AssignmrentStatusEnum;
import org.coderscampus.enums.AuthorityEnum;
import org.coderscampus.model.Assignment;
import org.coderscampus.model.User;
import org.coderscampus.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmrentStatusEnum.PENDING_SUBMISSION.getStatus());
        assignment.setNumber(fndNextAssignmentToSubmit(user));
        assignment.setUser(user);
        return assignmentRepository.save(assignment);
    }

    private Integer fndNextAssignmentToSubmit(User user) {
        Set<Assignment> assignmentsByUser = assignmentRepository.findByUser(user);
        if (assignmentsByUser == null) {
            return 1;
        }
        Optional<Integer> nextAssignmentNumOpt = assignmentsByUser.stream()
                .sorted((a1, a2) -> {
                    if (a1.getNumber() == null) return 1;
                    if (a2.getNumber() == null) return 1;
                    return a2.getNumber().compareTo(a1.getNumber());
                })
                .map(assignment -> {
                    if (assignment.getNumber() == null) return 1;
                    return assignment.getNumber() + 1;
                })
                .findFirst();
        return nextAssignmentNumOpt.orElse(1);
    }

    public Set<Assignment> findByUser(User user) {
        boolean hasCodeReviewerRole = user.getAuthorities()
                .stream()
                .filter(auth -> AuthorityEnum.ROLE_CODE_REVIEWER.name().equals(auth.getAuthority()))
                .count() > 0;

        if (hasCodeReviewerRole) {
            //load assignments if you're a code reviewer role

            return assignmentRepository.findByCodeReviewer(user);
        } else {
            //load assignments if you're a student role
            return assignmentRepository.findByUser(user);
        }
    }

    public Optional<Assignment> findAssignmentByAssignmentId(int id) {
        return assignmentRepository.findById(id);
    }

    public Assignment updateAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment save(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
}