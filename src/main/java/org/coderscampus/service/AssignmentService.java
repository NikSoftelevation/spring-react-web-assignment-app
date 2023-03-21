package org.coderscampus.service;

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
        assignment.setStatus("Needs to be submitted");
        assignment.setUser(user);
        return assignmentRepository.save(assignment);
    }
    public Set<Assignment> findByUser(User user) {
        return assignmentRepository.findByUser(user);
    }
    public Optional<Assignment> findAssignmentByAssignmentId(int id) {
        return assignmentRepository.findById(id);
    }
    public Assignment updateAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
}