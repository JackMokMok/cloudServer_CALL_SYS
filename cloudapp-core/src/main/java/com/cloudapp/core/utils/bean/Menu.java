package com.cloudapp.core.utils.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;

	private String name;

	private List<Path> paths = new ArrayList<Path>();

	public Menu(PathGroup pg) {
		this.code = pg.getCode();
		this.name = pg.getName();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public List<Path> getPaths() {
		return paths;
	}
}
