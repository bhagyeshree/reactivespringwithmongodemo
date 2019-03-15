package com.bhagyeshree.reactive.reactivemongoexmaple1.repository;

import com.bhagyeshree.reactive.reactivemongoexmaple1.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String >{
    
    
    
}
