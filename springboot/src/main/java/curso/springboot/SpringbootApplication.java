package curso.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		/* Gerar senha...
		* BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		* String senha = encode.encode("123");
		* System.out.println(senha);
		*/
	}

}
