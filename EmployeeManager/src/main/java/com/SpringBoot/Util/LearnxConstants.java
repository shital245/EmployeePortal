package com.SpringBoot.Util;

public enum LearnxConstants {

	EMPLOYEE_NOT_FOUND("employee.not.found"), 
	GENERAL_EXCEPTION_MESSAGE("general.exception"),
	BAD_REQUEST("bad.request"), 
	ALREADY_PRESENT("already.exist");

	private final String type;

	private LearnxConstants(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type;
	}

}
