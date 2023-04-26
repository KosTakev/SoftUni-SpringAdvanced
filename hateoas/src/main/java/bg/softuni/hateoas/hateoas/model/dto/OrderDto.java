package bg.softuni.hateoas.hateoas.model.dto;

public class OrderDto {

    private Long studentId;
    private Long courseId;

    public OrderDto() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public OrderDto setStudentId(Long studentId) {
        this.studentId = studentId;
        return this;
    }

    public Long getCourseId() {
        return courseId;
    }

    public OrderDto setCourseId(Long courseId) {
        this.courseId = courseId;
        return this;
    }
}
