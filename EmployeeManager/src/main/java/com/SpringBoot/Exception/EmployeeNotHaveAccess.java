package com.SpringBoot.Exception;

public class EmployeeNotHaveAccess extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeNotHaveAccess(String message) {
		super(message);
	}
}
