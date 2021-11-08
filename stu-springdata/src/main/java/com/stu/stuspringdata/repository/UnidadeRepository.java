package com.stu.stuspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stu.stuspringdata.model.UnidadeTrabalho;

@Repository
public interface UnidadeRepository extends CrudRepository<UnidadeTrabalho, Long> {

}
