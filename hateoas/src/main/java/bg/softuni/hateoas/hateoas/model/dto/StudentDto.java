package bg.softuni.hateoas.hateoas.model.dto;

import org.springframework.hateoas.server.core.Relation;

import java.util.List;

//Annotation if we want to change the name in the Json file
@Relation(collectionRelation = "students")
public class StudentDto {

    private Long id;
    private String name;
    private Integer age;
    private boolean deleted;

    private List<OrderDto> orders;

    public StudentDto() {
    }

    public Long getId() {
        return id;
    }

    public StudentDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudentDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public StudentDto setAge(Integer age) {
        this.age = age;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public StudentDto setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public StudentDto setOrders(List<OrderDto> orders) {
        this.orders = orders;
        return this;
    }
}
