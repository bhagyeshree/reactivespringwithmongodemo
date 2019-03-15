package com.bhagyeshree.reactive.reactivemongoexmaple1.resource;

import com.bhagyeshree.reactive.reactivemongoexmaple1.model.Employee;
import com.bhagyeshree.reactive.reactivemongoexmaple1.model.EmployeeEvents;
import com.bhagyeshree.reactive.reactivemongoexmaple1.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import org.springframework.http.MediaType;


import java.awt.*;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeResource {

    private EmployeeRepository employeeeRepository;

    public EmployeeResource(EmployeeRepository employeeeRepository) {
        this.employeeeRepository = employeeeRepository;
    }


    @GetMapping("/all")
    public Flux<Employee> getAll(){
        
        return employeeeRepository
                .findAll();
    }
    
    @GetMapping("/{id}")
    public Mono<Employee> getId(@PathVariable("id") final String  empId){
        return employeeeRepository.findById(empId);
    }
    
    
    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvents> getEvenets(@PathVariable("id") final String empId)
    {
         //artificial delay able to return data from database 
        //introduce the artifical delays 
      return   employeeeRepository.findById(empId)
                .flatMapMany(employee -> {
                    
                    //Create Flux Of Interval to artifical delay inside this particular reactive streams 
                    
                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                    Flux<EmployeeEvents> employeeEventsFlux = 
                            Flux.fromStream(
                                    Stream.generate(() -> new EmployeeEvents(employee, new Date()))
                            );
                    
                    //we have to have 2 separate flux 
                  return   Flux.zip(interval, employeeEventsFlux)
                            
                            
                            .map(Tuple2::getT2);
                    
                });
    }
    
}
