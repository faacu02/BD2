package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import unlp.info.bd2.repositories.ServiceDAO;
import unlp.info.bd2.repositories.SupplierDAO;
import unlp.info.bd2.repositories.ToursRepository;
import unlp.info.bd2.repositories.ToursRepositoryImpl;
import unlp.info.bd2.services.ToursService;
import unlp.info.bd2.services.ToursServiceImpl;
import unlp.info.bd2.services.UserService;
import unlp.info.bd2.repositories.UserRepositoryImpl;

@ComponentScan(basePackages = "unlp.info.bd2")
@EnableTransactionManagement
@Configuration
public class AppConfig {

    @Bean
    @Primary
    public ToursRepository createRepository(SupplierDAO supplierDAO, ServiceDAO serviceDAO, UserRepositoryImpl userRepository) {
        return new ToursRepositoryImpl(supplierDAO, serviceDAO,userRepository);
    }

    @Bean
    @Primary
    public ToursService createService(ToursRepository repository) {
        return new ToursServiceImpl(repository);
    }
}

