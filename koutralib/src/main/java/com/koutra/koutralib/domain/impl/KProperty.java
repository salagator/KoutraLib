package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;

public abstract class KProperty extends KBase implements IKProperty {
	
	protected IKObject kObject;
	protected IKPropertyDefn kPropertyDefn;
	
	public KProperty() {
		this.kObject = null;
		this.kPropertyDefn = null;
	}
	
	public KProperty(IKObject kObject, IKPropertyDefn kPropertyDefn) {
		this.kObject = kObject;
		this.kPropertyDefn = kPropertyDefn;
	}

	public KProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(id);
		this.kObject = kObject;
		this.kPropertyDefn = kPropertyDefn;
	}

	@Override
	public IKObject getIKObject() {
		return kObject;
	}

	@Override
	public IKPropertyDefn getIKPropertyDefn() {
		return kPropertyDefn;
	}

}
