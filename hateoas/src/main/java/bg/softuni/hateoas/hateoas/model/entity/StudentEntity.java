package bg.softuni.hateoas.hateoas.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity extends BaseEntity {

    private String name;

    private Integer age;

    private boolean deleted;

    @OneToMany (mappedBy = "student", fetch = FetchType.EAGER)
    private List<OrderEntity> orders;

    public StudentEntity() {
    }

    public String getName() {
        return name;
    }

    public StudentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public StudentEntity setAge(Integer age) {
        this.age = age;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public StudentEntity setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public StudentEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }
}
