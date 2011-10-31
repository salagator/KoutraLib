package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKPropertyDefn;

public class IntegerKProperty extends KProperty {

	protected Long value;
	
	public IntegerKProperty() {
		super();
	}
	
	public IntegerKProperty(IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(kObject, kPropertyDefn);
	}

	public IntegerKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(id, kObject, kPropertyDefn);
	}

	public IntegerKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn, Long value) {
		super(id, kObject, kPropertyDefn);
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
