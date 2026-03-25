package com.esgi.coursemanager.repository;

import com.esgi.coursemanager.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository  extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    Page<Student> findByEmail(String email, Pageable pageable);
}
