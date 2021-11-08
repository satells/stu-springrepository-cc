package com.stu.stuspringdata.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Funcionario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private LocalDate dataDaContratacao = LocalDate.now();
	private BigDecimal salario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cargo_id", nullable = false)
	private Cargo cargo;

	@ManyToOne(fetch = FetchType.LAZY)
	private UnidadeTrabalho unidadeTrabalho;

	public Funcionario(String nome, String cpf, BigDecimal salario, Cargo cargo, UnidadeTrabalho unidadeTrabalho) {
		this.nome = nome;
		this.cpf = cpf;
		this.salario = salario;
		this.cargo = cargo;
		this.unidadeTrabalho = unidadeTrabalho;
	}

	public Funcionario() {
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public LocalDate getDataDaContratacao() {
		return dataDaContratacao;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public UnidadeTrabalho getUnidadeTrabalho() {
		return unidadeTrabalho;
	}

}
