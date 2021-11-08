package com.stu.stuspringdata.specification;

import org.springframework.data.jpa.domain.Specification;

import com.stu.stuspringdata.model.Funcionario;

public class FuncionarioSpecification {

	public static Specification<Funcionario> nome(String nome) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("nome"), "%" + nome + "% ");
	}
}