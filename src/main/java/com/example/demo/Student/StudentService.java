package com.example.demo.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service //insures the @Autowire tag in the controller works
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired //intacia o repositorio como uma dependencia
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudent(){
        return studentRepository.findAll();
    }

    public ResponseEntity<String> addNewStudent(Student student) {
        //Optinal<Tipo> define um container objeto que pode ou não conter um objeto <Tipo>
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent() ||
                student.getName().isBlank() ||
                student.getDob() == null ||
                student.getDob().isAfter(LocalDate.now()))
        {
            return new ResponseEntity<>("Email taken or missing fields", HttpStatus.BAD_REQUEST);
        }
        studentRepository.save(student);
        return new ResponseEntity<>("Student added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            return new ResponseEntity<String>("Student doesn't exists", HttpStatus.BAD_REQUEST);
        }
        studentRepository.deleteById(studentId);
        return new ResponseEntity<String>("Student Deleted", HttpStatus.ACCEPTED);
    }



    //Lógica de UPDATE utilizando setters
    public ResponseEntity<String> updateStudent(Long studentId, String name, String email){
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if(!studentOptional.isPresent()){
            return new ResponseEntity<>(
                    "Id doesn't match or doesn't exist",
                    HttpStatus.BAD_REQUEST);
        }
        // retorna a instacia do banco de dados
        Student student = studentOptional.get();

        if(name != null){
            student.setName(name);
        }

        if(email != null){
            student.setEmail(email);
        }

//        o método save() serve tanto para update quanto para insert depende to contexto
//        ou seja, quanto a chave primaria da entidade nao está setada (null) ele faz um INSERT
//        caso esteja setada faz um UPDATE
        studentRepository.save(student);
        return new ResponseEntity<>("Student updated", HttpStatus.ACCEPTED);
    }

//      Lógica de UPDATE utilizando somente o request
//    public ResponseEntity<String> updateStudent(Student student) {
//
//        boolean exists = studentRepository.existsById(student.getId());
//        if(student.getId() == null || !exists){
//            return new ResponseEntity<>("id doesn't exists", HttpStatus.BAD_REQUEST);
//        }
//
//        String studentName = student.getName();
//        String studentEmail = student.getEmail();
//        Long studentId = student.getId();
//
//        studentRepository.updateStudent(studentName, studentEmail, studentId);
//        return new ResponseEntity<String>("Student updated", HttpStatus.ACCEPTED);
//    }
}
