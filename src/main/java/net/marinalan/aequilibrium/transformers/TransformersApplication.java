package net.marinalan.aequilibrium.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransformersApplication implements CommandLineRunner {

	@Autowired
	TransformerDaoImpl transformerDao;

	public static void main(String[] args) {
		SpringApplication.run(TransformersApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//transformerDao.playWithEntityManager();
	}
}
