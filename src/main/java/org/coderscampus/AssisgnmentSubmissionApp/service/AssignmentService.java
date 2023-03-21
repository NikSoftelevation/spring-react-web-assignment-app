package org.coderscampus.AssisgnmentSubmissionApp.service;

import org.coderscampus.AssisgnmentSubmissionApp.model.Assignment;
import org.coderscampus.AssisgnmentSubmissionApp.model.User;
import org.coderscampus.AssisgnmentSubmissionApp.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
