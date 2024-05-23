package com.SpringBoot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.Entity.Employee;

public interface EmployeeRepositories extends JpaRepository<Employee, Integer> {

}
