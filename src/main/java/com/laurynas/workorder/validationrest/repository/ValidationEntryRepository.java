package com.laurynas.workorder.validationrest.repository;

import com.laurynas.workorder.validationrest.repository.model.ValidationEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationEntryRepository extends JpaRepository<ValidationEntry, Long> {
}
