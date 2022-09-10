package mercury.procurems;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProcuremsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcuremsApplication.class, args);
	}


	 @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/supplierProcurement").allowedOrigins("http://localhost:3000");
			}
		};
	}

	
}
