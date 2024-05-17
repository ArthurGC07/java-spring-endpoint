package com.example.demo.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class StudentController {

    private final StudentService studentService;

    @Autowired //instanciate the studentService as a dependency
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    //define uma rota GET
    @GetMapping(path = "students")
    public List<Student> getStudents(){
        return studentService.getStudent();
    }

    //define uma rota POST
    @PostMapping(path = "newStudent") //a variavel path define a chamada da rota, ou seja, /valorDoPath
    public ResponseEntity<String> registerNewStudent(@RequestBody Student student){
        return studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "deleteStudent/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId){
        return studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody Student student){

        Long studentId = student.getId();
        String studentName = student.getName();
        String studentEmail = student.getEmail();

        return studentService.updateStudent(studentId, studentName, studentEmail);
    }

    //Lógica utilizando somente o request para UPDATE
//    @PutMapping(path = "updateStudent")
//    public ResponseEntity<String> updateStudent(@RequestBody Student student){
//        return studentService.updateStudent(student);
//    }
}
