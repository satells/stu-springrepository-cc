package com.stu.stuspringdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stu.stuspringdata.model.Cargo;
import com.stu.stuspringdata.repository.CargoRepository;
import com.stu.stuspringdata.repository.FuncionarioRepository;
import com.stu.stuspringdata.repository.UnidadeRepository;

@SpringBootApplication
public class StuSpringdataApplication implements CommandLineRunner {

	private FuncionarioRepository funcionarioRepository;
	private CargoRepository cargoRepository;
	private UnidadeRepository unidadeRepository;

	public StuSpringdataApplication(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository,
			UnidadeRepository unidadeRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeRepository = unidadeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(StuSpringdataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Cargo cargo = new Cargo("Analista de Sistemas");
		this.cargoRepository.save(cargo);
	}

}
