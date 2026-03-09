package bd.rh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RhApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RhApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(RhApplication.class, args);
	}
}
/*
@SpringBootApplication
public class RhApplication {
    public static void main(String[] args) {
        SpringApplication.run(RhApplication.class, args);
    }
}*/

