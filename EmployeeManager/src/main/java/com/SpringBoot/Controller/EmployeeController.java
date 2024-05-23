package com.SpringBoot.Controller;

import java.util.Arrays; 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.SpringBoot.DTO.EmployeeDTO;
import com.SpringBoot.Service.ProjectServiceImpl;


@Controller
public class EmployeeController {

	@Autowired
	public ProjectServiceImpl projectServiceImpl;

	@GetMapping(value = "/")
	@ResponseStatus(value = HttpStatus.OK)
	public String index(Model model) {
		List<EmployeeDTO> employeesAllEmployee = this.projectServiceImpl.getAllEmployees();
		model.addAttribute("employeesAllEmployee", employeesAllEmployee);
		return "view-employee";
	}

	// Below are Rest mapping for data show,add,delete,update

	@GetMapping(value = "/view-employee")
	@ResponseStatus(value = HttpStatus.OK)
	public String adminViewEmployee(Model model) {

		List<EmployeeDTO> employeesAllEmployee = this.projectServiceImpl.getAllEmployees();
		model.addAttribute("employeesAllEmployee", employeesAllEmployee);
		return "view-employee";

	}

	@GetMapping(value = "/add-employee")
	@ResponseStatus(value = HttpStatus.OK)
	public String adminAddEmployee() {
		return "add-employee";
	}

	@PostMapping(value = "/add-employee")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String adminAddEmployeeSuccess(@ModelAttribute EmployeeDTO incomingEmployee, Model model) {

		List<EmployeeDTO> employeesFromDatabase = this.projectServiceImpl.getAllEmployees();
		if (incomingEmployee.getName().isEmpty() || incomingEmployee.getEmail().isEmpty()
				|| incomingEmployee.getPassword().isEmpty()) {
			model.addAttribute("errorMessage", "Fields cannot be null !");
			return "add-employee";
		} else if (employeesFromDatabase == null) {
			//throw new EmployeeNotFoundException("Employees not found !");
			projectServiceImpl.addEmployee(incomingEmployee);
		}

		for (EmployeeDTO employeeDTO : employeesFromDatabase) {
			if (incomingEmployee.getId() == employeeDTO.getId()) {
				model.addAttribute("errorMessage", "Id already exist");
				return "add-employee";
			} else if (incomingEmployee.getEmail().equals(employeeDTO.getEmail())) {
				model.addAttribute("errorMessage", "Email already exist");
				return "add-employee";
			}
		}

		projectServiceImpl.addEmployee(incomingEmployee);
		List<EmployeeDTO> employeesAllEmployee = this.projectServiceImpl.getAllEmployees();
		model.addAttribute("employeesAllEmployee", employeesAllEmployee);
		return "view-employee";

	}

	@GetMapping(value = "/view-employee/delete-employee/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public String adminViewEmployeeRemoveAccess(@PathVariable("id") int id, Model model) {

		this.projectServiceImpl.deleteEmployee(id);
		List<EmployeeDTO> employeesAllEmployee = this.projectServiceImpl.getAllEmployees();
		model.addAttribute("employeesAllEmployee", employeesAllEmployee);

		return "view-employee";

	}


	// Mapping for Update Page to search for employee Id
	@GetMapping(value = "/update-employee")
	@ResponseStatus(value = HttpStatus.OK)
	public String adminUpdateEmployeeById() {
		return "update-employee-first";
	}

	// Mapping for update page to update employee details
	@GetMapping(value = "/update-employee/employee")
	@ResponseStatus(value = HttpStatus.OK)
	public String adminUpdateEmployee(@RequestParam("id") int id, Model model) {

		EmployeeDTO selectedEmployee = this.projectServiceImpl.getEmployeeById(id);

		List<String> jobRoleList = Arrays.asList("Mainframe developer", "Springboot developer", "Java developer",
				"Python developer", "React developer", "Cloud Engineer", "Data scientist");

		String selectedJobRole = selectedEmployee.getRole();

		List<String> jobLevelList = Arrays.asList("JL1", "JL2", "JL3", "JL4", "JL5", "JL6", "JL7");

		String selectedJobLevel = selectedEmployee.getJob_level();

		List<String> unitList = Arrays.asList("Healthcare", "Banking", "Cyber security", "Insurance",
				"Application Development", "Automotive", "Aerospace and Defense");

		String selectedUnit = selectedEmployee.getUnit();

		List<String> officeList = Arrays.asList("Pune Hinjewadi", "Pune Magarpatta", "Mumbai", "Nagpur", "Chennai",
				"Bengaluru", "Hydrabad", "Kolkatta");

		String selectedOffice = selectedEmployee.getOffice();

		model.addAllAttributes(Map.of("jobRoleList", jobRoleList, "selectedJobRole", selectedJobRole, "jobLevelList",
				jobLevelList, "selectedJobLevel", selectedJobLevel, "unitList", unitList, "selectedUnit", selectedUnit,
				"officeList", officeList, "selectedOffice", selectedOffice, "selectedEmployee", selectedEmployee));

		return "update-employee";

	}
	
	
	// Mapping for update page to update employee details
		@GetMapping(value = "/update-employee/employee/{id}")
		@ResponseStatus(value = HttpStatus.OK)
		public String adminUpdateEmployeeThroughViePage(@PathVariable("id") int id, Model model) {

			EmployeeDTO selectedEmployee = null;
			
			try {
				selectedEmployee = this.projectServiceImpl.getEmployeeById(id);
			} catch (Exception e) {
				model.addAttribute("errorMessage", "Employee Not exist!!");
				return "update-employee-first";
			}

			List<String> jobRoleList = Arrays.asList("Mainframe developer", "Springboot developer", "Java developer",
					"Python developer", "React developer", "Cloud Engineer", "Data scientist");

			String selectedJobRole = selectedEmployee.getRole();

			List<String> jobLevelList = Arrays.asList("JL1", "JL2", "JL3", "JL4", "JL5", "JL6", "JL7");

			String selectedJobLevel = selectedEmployee.getJob_level();

			List<String> unitList = Arrays.asList("Healthcare", "Banking", "Cyber security", "Insurance",
					"Application Development", "Automotive", "Aerospace and Defense");

			String selectedUnit = selectedEmployee.getUnit();

			List<String> officeList = Arrays.asList("Pune Hinjewadi", "Pune Magarpatta", "Mumbai", "Nagpur", "Chennai",
					"Bengaluru", "Hydrabad", "Kolkatta");

			String selectedOffice = selectedEmployee.getOffice();

			model.addAllAttributes(Map.of("jobRoleList", jobRoleList, "selectedJobRole", selectedJobRole, "jobLevelList",
					jobLevelList, "selectedJobLevel", selectedJobLevel, "unitList", unitList, "selectedUnit", selectedUnit,
					"officeList", officeList, "selectedOffice", selectedOffice, "selectedEmployee", selectedEmployee));

			return "update-employee";

		}
	
	
	@PostMapping("/update-employee/employee")
	@ResponseStatus(value = HttpStatus.OK)
	public String adminSearchEmployeeUpdateSuccess(@ModelAttribute("updatedEmployee") EmployeeDTO updatedEmployee,
			Model model) {

		if (updatedEmployee.getName().isEmpty() || updatedEmployee.getEmail().isEmpty()) {
			model.addAttribute("errorMessage", "Fields cannot be null !");
			return "update-employee";
		}

		// Below 3 lines of code to encode the employee password in database
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedEmployeePassword = passwordEncoder.encode(updatedEmployee.getPassword());
		updatedEmployee.setPassword(encodedEmployeePassword);

		this.projectServiceImpl.updateEmployee(updatedEmployee);
		List<EmployeeDTO> employeesAllEmployee = this.projectServiceImpl.getAllEmployees();
		model.addAttribute("employeesAllEmployee", employeesAllEmployee);
		return "view-employee";

	}

}