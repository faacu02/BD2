package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import unlp.info.bd2.repositories.ToursRepository;
import unlp.info.bd2.repositories.ToursRepositoryImpl;
import unlp.info.bd2.services.ToursService;
import unlp.info.bd2.services.ToursServiceImpl;
import unlp.info.bd2.services.UserService;
import unlp.info.bd2.repositories.UserRepositoryImpl;

@EnableTransactionManagement
@Configuration
public class AppConfig {

    @Bean
    @Primary
    public ToursRepository createRepository(  ) {
        return new ToursRepositoryImpl();
    }

    @Bean
    @Primary
    public ToursService createService(ToursRepository repository) {
        return new ToursServiceImpl(repository);
    }
}

