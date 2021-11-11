package com.example.crud_new.repository;

import com.example.crud_new.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;//crud hash
    private ListOperations listOperations;




    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
//        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){

//        hashOperations.put("EMPLOYEE", employee.getId(), employee);
        listOperations.rightPush("EMPLOYEE", employee);
    }
    public List<Employee> findAll(){
//        return hashOperations.values("EMPLOYEE");
        return  listOperations.range("EMPLOYEE ", 0, -1);
    }
    public Employee findById(Integer id){

//        return (Employee) hashOperations.get("EMPLOYEE", id);
        List<Employee> employeeList  = findAll();
        for (Employee employee: employeeList) {
            if(employee.getId() == id){
                return employee;
            }
        }
        return null;
    }

    public void update(Employee employee){
//        saveEmployee(employee);
        saveEmployee(employee);
    }
    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE", id);
        listOperations.remove("EMPLOYEE", 1,findById(id));
    }
}
