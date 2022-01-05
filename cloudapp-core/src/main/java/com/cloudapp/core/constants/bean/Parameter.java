package com.cloudapp.core.constants.bean;

import java.io.Serializable;

/**
 * 用于常量定义
 */

public class Parameter {

	private Serializable code;

	private String name;

	public Parameter(Serializable code, String name) {
		this.code = code;
		this.name = name;
	}

	public Serializable getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
