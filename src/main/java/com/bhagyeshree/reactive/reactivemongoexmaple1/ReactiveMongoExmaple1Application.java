package com.bhagyeshree.reactive.reactivemongoexmaple1;

import com.bhagyeshree.reactive.reactivemongoexmaple1.model.Employee;
import com.bhagyeshree.reactive.reactivemongoexmaple1.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveMongoExmaple1Application {

	
	@Bean
	CommandLineRunner employees(EmployeeRepository employeeRepository)
	{
		return args -> {
			employeeRepository
					
					.deleteAll()

					.subscribe(null, null, () -> {
						
						//here we iterated throw the list of stream 
						//for every stream save that in repository and print that out 
						//we deleted from the reposittoy 
					//once the delete was sucessfull 	
						//we are injecting data into the mongo database this is what mono and reactive and we have to suscribe to get the data and work on it 
						
						

						Stream.of(new Employee(UUID.randomUUID().toString(),
								"Peter", 23000L),new Employee(UUID.randomUUID().toString(),
								"Sam", 13000L),new Employee(UUID.randomUUID().toString(),
								"Ryan", 20000L),new Employee(UUID.randomUUID().toString(),
								"Chris", 53000L)
						)
								.forEach(employee -> {
									employeeRepository
											.save(employee)
											.subscribe(System.out::println);

								});
				
			})
			;
		};
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReactiveMongoExmaple1Application.class, args);
	}

}
