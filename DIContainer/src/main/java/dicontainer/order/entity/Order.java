package dicontainer.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String productName;

    private int price;

    private Order(Long orderId, String productName, int price) {
        this.orderId = orderId;
        this.productName = productName;
        this.price = price;
    }

    public static Order createOrder(Long orderId, String productName, int price) {
        return new Order(orderId, productName, price);
    }
}
