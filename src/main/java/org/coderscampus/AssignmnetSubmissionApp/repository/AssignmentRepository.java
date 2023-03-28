package org.coderscampus.AssignmnetSubmissionApp.repository;

import org.coderscampus.AssignmnetSubmissionApp.model.Assignment;
import org.coderscampus.AssignmnetSubmissionApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    Set<Assignment> findByUser(User user);

    @Query("select a from Assignment a "
            + "where ((a.status = 'submitted' or a.status = 'resubmitted') and (a.codeReviewer is null or a.codeReviewer = :codeReviewer))"
            + "or a.codeReviewer = :codeReviewer")
    Set<Assignment> findByCodeReviewer(User codeReviewer);

    @Query("select a from Assignment a " +
            "join fetch a.user u " +
            "where u.cohortStartDate is not null and u.bootcampDurationInWeeks is not null")
    List<Assignment> findAllActiveBootcampStudents();
}