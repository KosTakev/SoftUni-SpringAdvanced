package bg.softuni.hateoas.hateoas.service.impl;

import bg.softuni.hateoas.hateoas.model.dto.OrderDto;
import bg.softuni.hateoas.hateoas.model.dto.StudentDto;
import bg.softuni.hateoas.hateoas.model.entity.OrderEntity;
import bg.softuni.hateoas.hateoas.model.entity.StudentEntity;
import bg.softuni.hateoas.hateoas.repository.StudentRepository;
import bg.softuni.hateoas.hateoas.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<OrderDto> getStudentOrders(Long studentId) {
        return getStudentById(studentId)
                .map(StudentDto::getOrders)
                .orElseGet(ArrayList::new);
    }

    @Override
    public Optional<StudentDto> getStudentById(Long studentId) {
        return studentRepository
                .findById(studentId)
                .map(this::map);
    }


    //faster way to map without ModelMapper
    private StudentDto map(StudentEntity studentEntity) {

       List<OrderDto> orders = studentEntity
                .getOrders()
                .stream()
                .map(this::map)
                .toList();

        return new StudentDto()
                .setId(studentEntity.getId())
                .setName(studentEntity.getName())
                .setAge(studentEntity.getAge())
                .setDeleted(studentEntity.isDeleted())
                .setOrders(orders);
    }

    //faster way to map without ModelMapper
    private OrderDto map(OrderEntity orderEntity) {

        return new OrderDto()
                .setCourseId(orderEntity.getCourse().getId())
                .setStudentId(orderEntity.getStudent().getId());
    }
}
