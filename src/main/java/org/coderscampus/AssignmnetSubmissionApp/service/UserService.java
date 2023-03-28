package org.coderscampus.AssignmnetSubmissionApp.service;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.coderscampus.AssignmnetSubmissionApp.dto.UserDto;
import org.coderscampus.AssignmnetSubmissionApp.dto.UserKeyDto;
import org.coderscampus.AssignmnetSubmissionApp.model.Authority;
import org.coderscampus.AssignmnetSubmissionApp.model.User;
import org.coderscampus.AssignmnetSubmissionApp.repository.AuthorityRepository;
import org.coderscampus.AssignmnetSubmissionApp.repository.UserRepository;
import org.coderscampus.professo.domain.ProffessoUser;
import org.coderscampus.professo.repository.ProffessoUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProffessoUserRepo proffessoUserRepo;
    @Autowired
    private AuthorityRepository authorityRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void createUser(UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setName(userDto.getName());
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        newUser.setPassword(encodedPassword);
        userRepo.save(newUser);
        Authority authority = new Authority();
        authority.setAuthority("ROLE_STUDENT");
        authority.setUser(newUser);
        authorityRepo.save(authority);

    }

    public User duplicateProffessoUser(ProffessoUser proffessoUser) {
        User user = new User(proffessoUser, Optional.empty());
        user = userRepo.save(user);
        return user;
    }

    @Secured({"ROLE_INSTRUCTOR"})
    @Transactional
    public List<UserKeyDto> findBootcampStudents() {
        List<Tuple> proffessoUsersRawData = proffessoUserRepo.findBootcampStudents();
        var droppedStudents = proffessoUserRepo.findDroppedBootcampStudents();

        List<User> users = userRepo.findAll();
        List<UserKeyDto> proffessoUsers = proffessoUsersRawData.stream()
                .map(data -> {
                    Optional<User> match = users.stream().filter(user -> user.getUsername().equals(data.get(0))).findFirst();
                    UserKeyDto userKeyDto = new UserKeyDto((String) data.get(0), (String) data.get(1), null, null);
                    match.ifPresent(user -> {
                        userKeyDto.setBootcampDurationInWeeks(user.getBootcampDurationInWeeks());
                        userKeyDto.setStartDate(user.getCohortStartDate());
                    });
                    return userKeyDto;
                })
                .collect(Collectors.toList());

        return proffessoUsers.stream()
                .filter(proffessoUser -> !droppedStudents.stream()
                        .filter(droppedStudent -> droppedStudent.getEmail().equalsIgnoreCase(proffessoUser.getEmail()))
                        .findAny()
                        .isPresent())
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_INSTRUCTOR"})
    public List<User> findNonConfiguredStudents() {
        return userRepo.findAllInactiveBootcampStudents();
    }

    public void updateUser(UserKeyDto user) {
        Optional<User> userOpt = userRepo.findByUsername(user.getEmail());
        userOpt.ifPresentOrElse(u -> {
            u.setCohortStartDate(user.getStartDate() != null ? user.getStartDate() : user.getCohortStartDate());
            u.setBootcampDurationInWeeks(user.getBootcampDurationInWeeks());
            u.setName(user.getName());
            userRepo.save(u);
        }, () -> {
            Optional<ProffessoUser> proffessoUserOpt = proffessoUserRepo.findByEmail(user.getEmail());
            proffessoUserOpt.ifPresent(proffessoUser -> duplicateProffessoUser(proffessoUser));
        });
    }
}