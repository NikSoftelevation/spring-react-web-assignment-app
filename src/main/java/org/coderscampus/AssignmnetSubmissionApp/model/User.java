package org.coderscampus.AssignmnetSubmissionApp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.coderscampus.professo.domain.ProffessoUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
public class User implements UserDetails {
    private static final long serialVersionUID = 1840361243951715062L;
    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();
    private LocalDate cohortStartDate;
    private Integer bootcampDurationInWeeks;

    public User() {
    }

    public User(ProffessoUser proffessoUser, Optional<User> appUserOpt) {
        appUserOpt.ifPresent(appUser -> {
            appUser.getAuthorities()
                    .stream()
                    .forEach(auth -> {
                        Authority newAuth = new Authority();
                        newAuth.setAuthority(auth.getAuthority());
                        newAuth.setUser(this);
                        newAuth.setId(auth.getId());
                        this.getAuthorities().add(newAuth);
                    });
            this.bootcampDurationInWeeks = appUser.getBootcampDurationInWeeks();
            this.cohortStartDate = appUser.getCohortStartDate();
        });
        this.username = proffessoUser.getEmail();
        this.id = proffessoUser.getId();
        this.name = proffessoUser.getName();
        this.password = proffessoUser.getPassword();

    }

    public Integer getBootcampDurationInWeeks() {
        return bootcampDurationInWeeks;
    }

    public void setBootcampDurationInWeeks(Integer bootcampDurationInWeeks) {
        this.bootcampDurationInWeeks = bootcampDurationInWeeks;
    }

    public LocalDate getCohortStartDate() {
        return cohortStartDate;
    }

    public void setCohortStartDate(LocalDate cohortStartDate) {
        this.cohortStartDate = cohortStartDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", cohortStartDate=" + cohortStartDate +
                ", bootcampDuationInWeeks=" + bootcampDurationInWeeks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}