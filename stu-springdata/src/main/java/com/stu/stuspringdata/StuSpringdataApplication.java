package com.stu.stuspringdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.stu.stuspringdata.model.Cargo;
import com.stu.stuspringdata.model.Funcionario;
import com.stu.stuspringdata.model.UnidadeTrabalho;
import com.stu.stuspringdata.orm.FuncionarioProjecao;
import com.stu.stuspringdata.repository.CargoRepository;
import com.stu.stuspringdata.repository.FuncionarioRepository;
import com.stu.stuspringdata.repository.UnidadeRepository;
import com.stu.stuspringdata.specification.FuncionarioSpecification;

@SpringBootApplication
public class StuSpringdataApplication implements CommandLineRunner {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int CONST_SALARIO = 1;
	private static final int CONST_CPF = 0;
	private static final int CONST_NOME = 2;
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

	private void inserirFuncionarios() {
		long total = this.funcionarioRepository.count();

		if (total > 0) {
			return;
		}

		List<Funcionario> funcionarioList = new ArrayList<Funcionario>();
		File file = new File("./data/funcionarios.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = br.readLine()) != null) {
				String[] campos = s.split(";");
				String nome = campos[CONST_NOME];
				String cpf = campos[CONST_CPF];
				BigDecimal salario = new BigDecimal(campos[CONST_SALARIO]);

				int aleatorioCargo = aleatorioCargo();
				int aleatorioUnidadeTrabalho = aleatorioUnidadeTrabalho();

				Cargo cargo = new Cargo();
				cargo.setId((long) aleatorioCargo);

				UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
				unidadeTrabalho.setId((long) aleatorioUnidadeTrabalho);

				funcionarioList.add(new Funcionario(nome, cpf, salario, cargo, unidadeTrabalho));

				this.funcionarioRepository.saveAll(funcionarioList);

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private int aleatorioUnidadeTrabalho() {
		Random random = new Random();
		return random.nextInt((5 - 1) + 1) + 1;
	}

	private int aleatorioCargo() {
		Random random = new Random();
		return random.nextInt((9886 - 1) + 1) + 1;

	}

	private void inserirUnidades() {
		long total = this.unidadeRepository.count();

		if (total > 0) {
			return;
		}

		List<UnidadeTrabalho> unidades = new ArrayList<UnidadeTrabalho>();
		unidades.add(new UnidadeTrabalho("Loja de Moveis Lar & Lar - Centro", "Praça Boaventura, 150"));
		unidades.add(new UnidadeTrabalho("Loja de Moveis Lar & Lar - Leste", "Rua Sem Saída, 400"));
		unidades.add(new UnidadeTrabalho("Loja de Moveis Lar & Lar - Oeste", "Rua da Praça, 300"));
		unidades.add(new UnidadeTrabalho("Loja de Moveis Lar & Lar - Norte", "Rua do Norte, 20"));
		unidades.add(new UnidadeTrabalho("Loja de Moveis Lar & Lar - Sul", "Rua Centro Sul, 322"));

		this.unidadeRepository.saveAll(unidades);

	}

	private void inserirCargos() {

		long totalDeCargos = this.cargoRepository.count();

		if (totalDeCargos > 0) {
			return;
		}

		File cargosText = new File("./data/cargos.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(cargosText))) {
			String st;
			List<Cargo> cargosList = new ArrayList<Cargo>();
			while ((st = br.readLine()) != null) {
				cargosList.add(new Cargo(st));
			}
			this.cargoRepository.saveAll(cargosList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buscaCargosComPaginacao() {
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("-----------------------------------------------------------------------------------------------------");
		int page = 1;
		int size = 5;
		Sort sort = Sort.by(Sort.Direction.DESC, "descricao");
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Cargo> pageCargos = this.cargoRepository.findAll(pageable);
		System.out.println("Cargos");
		System.out.println("Página atual: " + pageCargos.getNumber());
		System.out.println("Total elementos: " + pageCargos.getTotalElements());
		System.out.println("Total páginas: " + pageCargos.getTotalPages());
		System.out.println("-----------------------------------------------------------------------------------------------------");
		pageCargos.forEach(c -> System.out.println(c.getId() + ":" + c.getDescricao()));
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}

	private void buscaFuncionario() {
		System.out.println("////////////////////////////////////////////////////////////////////////////////");
		Funcionario funcionario = this.funcionarioRepository.localizarPorId(1l);
		System.out.println(funcionario.getNome());
		System.out.println(funcionario.getCargo().getId());
		System.out.println(funcionario.getUnidadeTrabalho().getId());
		System.out.println("-------------------------------------------------------------------------------");
		List<Funcionario> localizarPorNome = this.funcionarioRepository.localizarPorNome("a");
		localizarPorNome.forEach(f -> System.out.println(
				f.getId() + ":" + f.getNome() + ":" + f.getCpf() + ":" + f.getDataDaContratacao() + ":" + f.getSalario() + ":" + f.getCargo().getId()
						+ ":" + f.getCargo().getDescricao() + ":" + f.getUnidadeTrabalho().getId() + ":" + f.getUnidadeTrabalho().getDescricao()));
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------");
		List<Funcionario> localizarPorNomeUnidade = this.funcionarioRepository.localizarPorNomeUnidade(null, 1L);
		localizarPorNomeUnidade.forEach(f -> System.out.println(
				f.getId() + ":" + f.getNome() + ":" + f.getCpf() + ":" + f.getDataDaContratacao() + ":" + f.getSalario() + ":" + f.getCargo().getId()
						+ ":" + f.getCargo().getDescricao() + ":" + f.getUnidadeTrabalho().getId() + ":" + f.getUnidadeTrabalho().getDescricao()));
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------");
		List<Funcionario> findByNomeLikeOrderByNomeDesc = this.funcionarioRepository.findByNomeContainingOrderByNomeDesc("m");

		findByNomeLikeOrderByNomeDesc.forEach(
				f -> System.out.println(f.getId() + ":" + f.getNome() + ":" + f.getCpf() + ":" + f.getDataDaContratacao() + ":" + f.getSalario()));

		LocalDate localDate = LocalDate.parse("07/11/2021", formatter);

		List<Funcionario> buscar = this.funcionarioRepository.buscar("a", new BigDecimal("5000.00"), localDate);
		buscar.forEach(
				f -> System.out.println(f.getId() + ":" + f.getNome() + ":" + f.getCpf() + ":" + f.getDataDaContratacao() + ":" + f.getSalario()));

		List<Funcionario> buscaPorNativeQuery = this.funcionarioRepository.buscaPorNativeQuery(localDate);
		buscaPorNativeQuery.forEach(
				f -> System.out.println(f.getId() + ":" + f.getNome() + ":" + f.getCpf() + ":" + f.getDataDaContratacao() + ":" + f.getSalario()));

	}

	private void buscaFuncionarioComProjecao() {
		List<FuncionarioProjecao> findFuncionarioSalario = this.funcionarioRepository.findFuncionarioSalario();
		findFuncionarioSalario.forEach(f -> System.out.println(f.getId() + ":" + f.getNome() + ":" + f.getSalario()));

	}

	@Override
	public void run(String... args) throws Exception {
		inserirCargos();
		inserirUnidades();
		inserirFuncionarios();
		buscaFuncionario();
		buscaCargosComPaginacao();
		buscaFuncionarioComProjecao();
		buscarPorSpecification();

	}

	private void buscarPorSpecification() {
		List<Funcionario> funcionarios = this.funcionarioRepository.findAll(Specification.where(FuncionarioSpecification.nome("a")));
		funcionarios.forEach(f -> System.out.println(f.getNome()));

	}

}