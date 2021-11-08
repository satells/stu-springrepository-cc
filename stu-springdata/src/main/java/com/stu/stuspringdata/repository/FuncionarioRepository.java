package com.stu.stuspringdata.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stu.stuspringdata.model.Funcionario;
import com.stu.stuspringdata.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {

	@Query("select f, c, u from Funcionario f join f.cargo c join f.unidadeTrabalho u where f.id = :id")
	public Funcionario localizarPorId(@Param("id") Long id);

	public List<Funcionario> findByNomeContainingOrderByNomeDesc(String nome);

	@Query("select f, c, u from Funcionario f join f.cargo c join f.unidadeTrabalho u where f.nome like %:nome% order by f.nome")
	public List<Funcionario> localizarPorNome(@Param("nome") String nome);

	@Query("select f, c, u from Funcionario f join f.cargo c join f.unidadeTrabalho u where (:nome is null or f.nome=:nome) and u.id = :idUnidade order by f.nome")
	public List<Funcionario> localizarPorNomeUnidade(@Param("nome") String nome, @Param("idUnidade") Long idUnidade);

	@Query("select f from Funcionario f where f.nome like %:nome% and f.salario >= :salario and f.dataDaContratacao=:dataDaContratacao")
	public List<Funcionario> buscar(@Param("nome") String nome, @Param("salario") BigDecimal salario,
			@Param("dataDaContratacao") LocalDate dataDaContratacao);

	@Query(value = "select * from funcionario f where f.dataDaContratacao = dataDaContratacao", nativeQuery = true)
	public List<Funcionario> buscaPorNativeQuery(LocalDate dataDaContratacao);

	@Query(value = "select f.id, f.nome, f.salario from Funcionario f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();

}
