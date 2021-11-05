package com.stu.stuspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stu.stuspringdata.model.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

}
