package thrymr.net.hospital.management;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HospitalManagement {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(HospitalManagement.class, args);

	}
		@Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder(){
			return new BCryptPasswordEncoder();
		}
}
