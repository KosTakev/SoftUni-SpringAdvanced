package bg.softuni.hateoas.hateoas.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "courses")
public class CourseEntity extends BaseEntity {

    private String name;

    private BigDecimal price;

    @OneToMany (mappedBy = "course", fetch = FetchType.EAGER)
    private List<OrderEntity> orders;

    public CourseEntity() {
    }

    public String getName() {
        return name;
    }

    public CourseEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public CourseEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }
}
