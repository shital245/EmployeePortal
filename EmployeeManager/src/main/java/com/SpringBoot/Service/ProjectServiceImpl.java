package com.SpringBoot.Service;

import java.util.ArrayList;      
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringBoot.DTO.EmployeeDTO;
import com.SpringBoot.Entity.Employee;

import com.SpringBoot.Exception.EmployeeNotFoundException;
import com.SpringBoot.Exception.EmployeeeAlreadyPresentException;
import com.SpringBoot.Exception.EmployeeeInputException;
import com.SpringBoot.Repositories.EmployeeRepositories;
import com.SpringBoot.Util.LearnxConstants;



@Service
public class ProjectServiceImpl implements ProjectServices {

	@Autowired
	public EmployeeRepositories employeeRepositories;

	@Autowired
	public Environment environment;

	@Autowired
	public ModelMapper modelMapper;

	// **** Employee function **** //
	@Override
	public List<EmployeeDTO> getAllEmployees() {
		List<Employee> allEmployees = (List<Employee>) employeeRepositories.findAll();
		List<EmployeeDTO> allEmployeesDtos = new ArrayList<>();
		for (Employee employee : allEmployees) {
			allEmployeesDtos.add(modelMapper.map(employee, EmployeeDTO.class));
		}

		return allEmployeesDtos;
	}

	@Override
	public EmployeeDTO getEmployeeById(int id) {

		Optional<Employee> findById = this.employeeRepositories.findById(id);
		if (findById.isEmpty()) {
			throw new EmployeeNotFoundException(environment.getProperty(LearnxConstants.EMPLOYEE_NOT_FOUND.toString()));
		}

		Employee employee = findById.get();
		return modelMapper.map(employee, EmployeeDTO.class);
	}


	@Override
	public List<EmployeeDTO> addEmployee(EmployeeDTO employeeDTO) {
		Optional<Employee> option = this.employeeRepositories.findById(employeeDTO.getId());

		List<EmployeeDTO> allEmployeesDtos = new ArrayList<>();
		if (option.isEmpty()) {

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedEmployeePassword = passwordEncoder.encode(employeeDTO.getPassword());
			employeeDTO.setPassword(encodedEmployeePassword);
			this.employeeRepositories.save(modelMapper.map(employeeDTO, Employee.class));

		} else {
			throw new EmployeeeAlreadyPresentException(
					environment.getProperty(LearnxConstants.ALREADY_PRESENT.toString()));
		}

		List<Employee> allEmployees = this.employeeRepositories.findAll();

		for (Employee emp : allEmployees) {
			allEmployeesDtos.add(modelMapper.map(emp, EmployeeDTO.class));
		}

		return allEmployeesDtos;

	}

	@Override
	public List<EmployeeDTO> deleteEmployee(int id) {
		Optional<Employee> optional = this.employeeRepositories.findById(id);
		List<EmployeeDTO> allEmployeesDtos = new ArrayList<>();
		if (optional.isEmpty()) {
			throw new EmployeeNotFoundException(environment.getProperty(LearnxConstants.EMPLOYEE_NOT_FOUND.toString()));
		}

		employeeRepositories.deleteById(id);

		List<Employee> allEmployees = this.employeeRepositories.findAll();

		for (Employee emp : allEmployees) {
			allEmployeesDtos.add(modelMapper.map(emp, EmployeeDTO.class));
		}

		return allEmployeesDtos;
	}

	// Below method is not used in rest controller
	@Override
	public List<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO) {
		if (employeeDTO == null) {
			throw new EmployeeeInputException(environment.getProperty(LearnxConstants.BAD_REQUEST.toString()));
		}

		this.employeeRepositories.save(modelMapper.map(employeeDTO, Employee.class));

		List<EmployeeDTO> allEmployeesDtos = new ArrayList<>();
		List<Employee> allEmployees = this.employeeRepositories.findAll();

		for (Employee emp : allEmployees) {
			allEmployeesDtos.add(modelMapper.map(emp, EmployeeDTO.class));
		}
		return allEmployeesDtos;

	}

}
