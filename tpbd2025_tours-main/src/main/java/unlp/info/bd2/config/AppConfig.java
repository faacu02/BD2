package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import unlp.info.bd2.repositories.SupplierDAO;
import unlp.info.bd2.repositories.ToursRepository;
import unlp.info.bd2.repositories.ToursRepositoryImpl;
import unlp.info.bd2.services.ToursService;
import unlp.info.bd2.services.ToursServiceImpl;

@ComponentScan(basePackages = "unlp.info.bd2")

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public ToursRepository createRepository(SupplierDAO supplierDAO) {
        return new ToursRepositoryImpl(supplierDAO);
    }

    @Bean
    @Primary
    public ToursService createService(ToursRepository repository) {
        return new ToursServiceImpl(repository);
    }
}

