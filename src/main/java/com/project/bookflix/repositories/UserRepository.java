package com.project.bookflix.repositories;

import com.project.bookflix.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);
    Optional<User> findByEmail(String email);


}

//JPA Repository to handle SQL queries
// 1. interface
// 2. extend JPARepository