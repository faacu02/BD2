package unlp.info.bd2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import unlp.info.bd2.utils.ToursException;

@SpringBootApplication
public class TrabajoPractico2024Application {

	public static void main(String[] args) throws ToursException {
		System.out.println("hola");
		SpringApplication.run(TrabajoPractico2024Application.class, args);
	}

}
