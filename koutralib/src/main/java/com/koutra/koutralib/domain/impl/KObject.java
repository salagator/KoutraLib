package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;

public class KObject extends KBase implements IKObject {
	
	protected KClass kClass;
	
	public KObject() {
		this.kClass = null;
	}
	
	public KObject(KClass kClass) {
		this.kClass = kClass;
	}
	
	public KObject(long id, KClass kClass) {
		super(id);
		this.kClass = kClass;
	}

	@Override
	public IKClass getKClass() {
		return kClass;
	}

	@Override
	public IKProperty getKProperty(IKPropertyDefn propertyDefn) {
		// TODO Auto-generated method stub
		return null;
	}

}
