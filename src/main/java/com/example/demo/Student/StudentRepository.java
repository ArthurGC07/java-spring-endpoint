package com.example.demo.Student;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT * FROM student WHERE email = :email", nativeQuery = true)
    Optional<Student> findStudentByEmail(@Param("email") String email);

//    As duas anotações devem ser usadas em conjunto para um query de
//    INSERT UPDATE e DELETE customizadas, ou seja, declaradas aqui
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE student SET name = :name, email = :email WHERE id = :id",
            nativeQuery = true)
    int updateStudent(
            @Nullable @Param("name") String name,
            @Nullable @Param("email") String email,
            @Param("id") Long id);
}
