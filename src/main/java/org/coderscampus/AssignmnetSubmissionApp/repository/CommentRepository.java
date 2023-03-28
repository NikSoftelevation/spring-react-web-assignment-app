package org.coderscampus.AssignmnetSubmissionApp.repository;

import java.util.Set;

import org.coderscampus.AssignmnetSubmissionApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c "
            + " where c.assignment.id = :assignmentId")
    Set<Comment> findByAssignmentId(Long assignmentId);

}