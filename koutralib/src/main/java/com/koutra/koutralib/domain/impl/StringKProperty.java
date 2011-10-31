package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKPropertyDefn;

public class StringKProperty extends KProperty {

	protected String value;
	
	public StringKProperty() {
		super();
	}
	
	public StringKProperty(IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(kObject, kPropertyDefn);
	}

	public StringKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(id, kObject, kPropertyDefn);
	}

	public StringKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn, String value) {
		super(id, kObject, kPropertyDefn);
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
