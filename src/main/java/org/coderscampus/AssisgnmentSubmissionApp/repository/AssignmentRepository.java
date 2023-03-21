package org.coderscampus.AssisgnmentSubmissionApp.repository;

import org.coderscampus.AssisgnmentSubmissionApp.model.Assignment;
import org.coderscampus.AssisgnmentSubmissionApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    public Set<Assignment> findByUser(User user);
}