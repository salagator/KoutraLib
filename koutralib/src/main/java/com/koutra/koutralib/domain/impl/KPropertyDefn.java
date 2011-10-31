package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKPropertyDefn;

public class KPropertyDefn extends KBase implements IKPropertyDefn {
	
	protected String name;
	protected Type type;
	
	public KPropertyDefn() {
		type = null;
		name = null;
	}
	
	public KPropertyDefn(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public KPropertyDefn(long id, String name, Type type) {
		super(id);
		this.name = name;
		this.type = type;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

}
