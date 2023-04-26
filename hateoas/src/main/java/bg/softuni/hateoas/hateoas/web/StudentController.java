package bg.softuni.hateoas.hateoas.web;

import bg.softuni.hateoas.hateoas.model.dto.OrderDto;
import bg.softuni.hateoas.hateoas.model.dto.StudentDto;
import bg.softuni.hateoas.hateoas.service.StudentService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/students")
@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<StudentDto>>> getStudents() {

        List<EntityModel<StudentDto>> studentEntityModels = studentService
                .getAllStudents()
                .stream()
                .map(s -> EntityModel.of(s, getStudentLinks(s)))
                .toList();

        return ResponseEntity
                .ok(CollectionModel.of(studentEntityModels));

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<StudentDto>> getStudentById(@PathVariable ("id") Long id) {

        Optional<StudentDto> studentOpt = studentService.getStudentById(id);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        StudentDto student = studentOpt.get();

        return ResponseEntity.ok(EntityModel.of(student, getStudentLinks(student)));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<CollectionModel<EntityModel<OrderDto>>>
    getStudentOrders(@PathVariable ("id") Long id) {

        List<EntityModel<OrderDto>> orders = studentService
                .getStudentOrders(id)
                .stream()
                .map(o -> EntityModel.of(o, getOrdersLink(o)))
                .toList();

        return ResponseEntity
                .ok(CollectionModel.of(orders));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<StudentDto>> updateStudent(@PathVariable ("id") Long id,
                              StudentDto studentDto) {

        throw new UnsupportedOperationException("Not important right now!");
    }

    //Adding links to student and order:
    private Link[] getStudentLinks(StudentDto studentDto) {
        List<Link> studentLinks = new ArrayList<>();

        Link selfRel =
                linkTo(methodOn(StudentController.class)
                .getStudentById(studentDto.getId()))
                .withSelfRel();

        studentLinks.add(selfRel);

        if (!studentDto.isDeleted()) {
            Link orderLink = linkTo(methodOn(StudentController.class)
                    .getStudentOrders(studentDto.getId()))
                    .withRel("orders");

            studentLinks.add(orderLink);

            Link updateLink = linkTo(methodOn(StudentController.class)
                    .updateStudent(studentDto.getId(), studentDto))
                    .withRel("updates");

            studentLinks.add(updateLink);
        }

        return studentLinks.toArray(new Link[studentLinks.size()]);
    }

    private Link getOrdersLink(OrderDto orderDto) {

        return linkTo(methodOn(StudentController.class)
                        .getStudentById(orderDto.getStudentId()))
                        .withRel("students");
    }

}
