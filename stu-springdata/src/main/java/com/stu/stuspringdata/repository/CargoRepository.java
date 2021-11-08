package com.stu.stuspringdata.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stu.stuspringdata.model.Cargo;

@Repository
public interface CargoRepository extends PagingAndSortingRepository<Cargo, Long> {

}
