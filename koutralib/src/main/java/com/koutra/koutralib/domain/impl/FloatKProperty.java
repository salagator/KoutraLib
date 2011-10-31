package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKPropertyDefn;

public class FloatKProperty extends KProperty {

	protected Double value;
	
	public FloatKProperty() {
		super();
	}
	
	public FloatKProperty(IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(kObject, kPropertyDefn);
	}

	public FloatKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(id, kObject, kPropertyDefn);
	}

	public FloatKProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn, Double value) {
		super(id, kObject, kPropertyDefn);
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
