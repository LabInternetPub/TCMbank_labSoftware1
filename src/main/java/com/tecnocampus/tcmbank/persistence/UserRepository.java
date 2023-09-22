package com.tecnocampus.tcmbank.persistence;

import com.tecnocampus.tcmbank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {}