package com.koutra.koutralib.domain.impl;

import java.util.HashMap;
import java.util.Map;

import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;
import com.koutra.koutralib.domain.IKPropertyDefn.Type;

public class KObject extends KBase implements IKObject {
	
	protected IKClass kClass;
	protected Map<Long, IKProperty> kPropertyMap;
	
	public KObject() {
		this.kClass = null;
		this.kPropertyMap = new HashMap<Long, IKProperty>();
	}
	
	public KObject(IKClass kClass, Map<Long, IKProperty> kPropertyMap) {
		this.kClass = kClass;
		this.kPropertyMap = kPropertyMap;
	}
	
	public KObject(long id, IKClass kClass, Map<Long, IKProperty> kPropertyMap) {
		super(id);
		this.kClass = kClass;
		this.kPropertyMap = kPropertyMap;
	}

	@Override
	public IKClass getIKClass() {
		return kClass;
	}

	@Override
	public IKProperty getIKProperty(IKPropertyDefn kPropertyDefn) {
		return kPropertyMap.get(kPropertyDefn.getId());
	}

	@Override
	public void setIKProperty(IKPropertyDefn kPropertyDefn, IKProperty kProperty) {
		if (!hasIKProperty(kPropertyDefn)) {
			throw new IllegalArgumentException("Attempting to set property '" +
					kPropertyDefn.getName() +
					"' for object that does not have it attached.");
		}
		kPropertyMap.put(kPropertyDefn.getId(), kProperty);
	}

	@Override
	public boolean hasIKProperty(IKPropertyDefn kPropertyDefn) {
		return kClass.containsIKPropertyDefn(kPropertyDefn);
	}

	@Override
	public IKProperty getIKPropertyById(long id) {
		return kPropertyMap.get(id);
	}

	@Override
	public IKProperty getIKPropertyByName(String name) {
		IKPropertyDefn kPropertyDefn = kClass.getIKPropertyDefnByName(name);
		if (kPropertyDefn == null) {
			return null;
		}
		
		return kPropertyMap.get(kPropertyDefn.getId());
	}

	@Override
	public IKProperty getIKPropertyByNameAndType(String name, Type type) {
		IKPropertyDefn kPropertyDefn = kClass.getIKPropertyDefnByNameAndType(name, type);
		if (kPropertyDefn == null) {
			return null;
		}
		
		return kPropertyMap.get(kPropertyDefn.getId());
	}

}
