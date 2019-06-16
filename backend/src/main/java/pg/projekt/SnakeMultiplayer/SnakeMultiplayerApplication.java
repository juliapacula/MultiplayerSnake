package pg.projekt.SnakeMultiplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SnakeMultiplayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnakeMultiplayerApplication.class, args);
	}
}
