package org.coderscampus.repository;

import org.coderscampus.model.Assignment;
import org.coderscampus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    Set<Assignment> findByUser(User user);

    @Query("select a from Assignment a where a.status='submitted'" + "or a.codeReviewer=:codeReviewer")
    Set<Assignment> findByCodeReviewer(User codeReviewer);
}