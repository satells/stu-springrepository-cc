package com.stu.stuspringdata.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UnidadeTrabalho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private String endereco;
	@OneToMany(mappedBy = "unidadeTrabalho")
	private List<Funcionario> funcionario;

	public UnidadeTrabalho(String descricao, String endereco) {
		this.descricao = descricao;
		this.endereco = endereco;
	}

	public UnidadeTrabalho() {
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
