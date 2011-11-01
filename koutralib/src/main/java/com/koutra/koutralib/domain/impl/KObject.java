package com.koutra.koutralib.domain.impl;

import java.util.HashMap;
import java.util.Map;

import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;
import com.koutra.koutralib.domain.IKPropertyDefn.Type;

public class KObject extends KBase implements IKObject {
	
	protected KClass kClass;
	protected Map<Long, KProperty> kPropertyMap;
	
	public KObject() {
		this.kClass = null;
		this.kPropertyMap = new HashMap<Long, KProperty>();
	}
	
	public KObject(KClass kClass, Map<Long, KProperty> kPropertyMap) {
		this.kClass = kClass;
		this.kPropertyMap = kPropertyMap;
	}
	
	public KObject(long id, KClass kClass, Map<Long, KProperty> kPropertyMap) {
		super(id);
		this.kClass = kClass;
		this.kPropertyMap = kPropertyMap;
	}

	@Override
	public IKClass getKClass() {
		return kClass;
	}

	@Override
	public IKProperty getKProperty(IKPropertyDefn kPropertyDefn) {
		return kPropertyMap.get(kPropertyDefn.getId());
	}

	@Override
	public boolean hasKProperty(IKPropertyDefn kPropertyDefn) {
		return kClass.containsKPropertyDefn(kPropertyDefn);
	}

	@Override
	public IKProperty getKPropertyById(long id) {
		return kPropertyMap.get(id);
	}

	@Override
	public IKProperty getKPropertyByName(String name) {
		IKPropertyDefn kPropertyDefn = kClass.getKPropertyDefnByName(name);
		if (kPropertyDefn == null) {
			return null;
		}
		
		return kPropertyMap.get(kPropertyDefn.getId());
	}

	@Override
	public IKProperty getKPropertyByNameAndType(String name, Type type) {
		IKPropertyDefn kPropertyDefn = kClass.getKPropertyDefnByNameAndType(name, type);
		if (kPropertyDefn == null) {
			return null;
		}
		
		return kPropertyMap.get(kPropertyDefn.getId());
	}

}
