package org.coderscampus.AssignmnetSubmissionApp.repository;

import org.coderscampus.AssignmnetSubmissionApp.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
