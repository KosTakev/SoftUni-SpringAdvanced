package bg.softuni.hateoas.hateoas.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @ManyToOne
    private CourseEntity course;

    @ManyToOne
    private StudentEntity student;

    public OrderEntity() {
    }

    public CourseEntity getCourse() {
        return course;
    }

    public OrderEntity setCourse(CourseEntity course) {
        this.course = course;
        return this;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public OrderEntity setStudent(StudentEntity student) {
        this.student = student;
        return this;
    }
}
