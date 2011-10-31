package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKPropertyDefn;

public class BooleanKProperty extends KProperty {

	protected Boolean value;
	
	public BooleanKProperty() {
		super();
	}
	
	public BooleanKProperty(IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(kObject, kPropertyDefn);
	}

	public BooleanKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(id, kObject, kPropertyDefn);
	}

	public BooleanKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn, Boolean value) {
		super(id, kObject, kPropertyDefn);
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
