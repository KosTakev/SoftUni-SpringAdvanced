package bg.softuni.hateoas.hateoas.service;

import bg.softuni.hateoas.hateoas.model.dto.OrderDto;
import bg.softuni.hateoas.hateoas.model.dto.StudentDto;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<OrderDto> getStudentOrders(Long studentId);

    public Optional<StudentDto> getStudentById(Long studentId);

    public List<StudentDto> getAllStudents();




}
