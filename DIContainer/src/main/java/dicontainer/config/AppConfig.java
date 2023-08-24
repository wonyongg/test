package dicontainer.config;

import dicontainer.order.service.AgeDiscountServiceImpl;
import dicontainer.order.service.LevelDiscountServiceImpl;
import dicontainer.order.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OrderService orderService() {
        return new AgeDiscountServiceImpl();
    }
}
