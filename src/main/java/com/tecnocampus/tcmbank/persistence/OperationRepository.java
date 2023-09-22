package com.tecnocampus.tcmbank.persistence;

import com.tecnocampus.tcmbank.domain.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Movement, String> {}