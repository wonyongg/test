package resttemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resttemplate.service.ServerService;
import resttemplate.service.apiServerServiceImplement;

@Configuration
public class DiContainer {

    @Bean
    public ServerService serverService() {return new apiServerServiceImplement();}
}
