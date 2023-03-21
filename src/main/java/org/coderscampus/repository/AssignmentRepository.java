package org.coderscampus.repository;

import org.coderscampus.model.Assignment;
import org.coderscampus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    public Set<Assignment> findByUser(User user);
}