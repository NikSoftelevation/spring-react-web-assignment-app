package com.coderscampus.AssisgnmentSubmissionApp.service;

import com.coderscampus.AssisgnmentSubmissionApp.model.Assignment;
import com.coderscampus.AssisgnmentSubmissionApp.model.User;
import com.coderscampus.AssisgnmentSubmissionApp.repository.AssignmentRepository;
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
