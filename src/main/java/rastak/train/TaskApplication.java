package rastak.train;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import rastak.train.ws.model.enums.Role;
import rastak.train.ws.model.request.SignUp;
import rastak.train.ws.service.UserService;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService){
		return args -> {
			var admin = SignUp.builder()
					.username("najafizadeh")
					.fullname("Abdollatif najafizadeh")
					.password("Aa.12345")
					.role(Role.ADMIN)
					.build();
		};
	}
}
