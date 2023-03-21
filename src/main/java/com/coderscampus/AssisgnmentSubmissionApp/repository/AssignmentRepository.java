package com.coderscampus.AssisgnmentSubmissionApp.repository;

import com.coderscampus.AssisgnmentSubmissionApp.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {
}