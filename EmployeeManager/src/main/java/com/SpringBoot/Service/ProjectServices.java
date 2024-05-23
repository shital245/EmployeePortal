package com.SpringBoot.Service;

import java.util.List; 

import com.SpringBoot.DTO.EmployeeDTO;


public interface ProjectServices {

	// **** Employee function **** //
	public List<EmployeeDTO> getAllEmployees();

	public EmployeeDTO getEmployeeById(int id);

	public List<EmployeeDTO> addEmployee(EmployeeDTO employeeDTO);

	public List<EmployeeDTO> deleteEmployee(int id);

	public List<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO);

}
