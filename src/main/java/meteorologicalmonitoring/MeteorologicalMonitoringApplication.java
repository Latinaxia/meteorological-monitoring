package meteorologicalmonitoring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("meteorologicalmonitoring.mapper")
@EnableScheduling
public class MeteorologicalMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeteorologicalMonitoringApplication.class, args);
	}

}
