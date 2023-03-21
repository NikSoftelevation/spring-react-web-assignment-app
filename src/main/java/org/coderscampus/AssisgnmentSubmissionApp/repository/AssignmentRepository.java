package org.coderscampus.AssisgnmentSubmissionApp.repository;

import org.coderscampus.AssisgnmentSubmissionApp.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {
}