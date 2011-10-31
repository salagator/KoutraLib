package com.koutra.koutralib.domain.impl;

import java.util.Set;

import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKPropertyDefn;
import com.koutra.koutralib.domain.IKPropertyDefn.Type;

public class KClass extends KBase implements IKClass {
	
	protected IKClass superKClass;
	protected Set<IKPropertyDefn> kPropertyDefnSet;
	
	public KClass() {
		this.superKClass = null;
		this.kPropertyDefnSet = null;
	}
	
	public KClass(IKClass superKClass, Set<IKPropertyDefn> kPropertyDefnSet) {
		this.superKClass = superKClass;
		this.kPropertyDefnSet = kPropertyDefnSet;
	}
	
	public KClass(long id, IKClass superKClass, Set<IKPropertyDefn> kPropertyDefnSet) {
		super(id);
		this.superKClass = superKClass;
		this.kPropertyDefnSet = kPropertyDefnSet;
	}

	@Override
	public IKClass getSuperKClass() {
		return superKClass;
	}

	@Override
	public Set<IKPropertyDefn> getKPropertyDefnSet() {
		return kPropertyDefnSet;
	}

	@Override
	public boolean containsKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		return kPropertyDefnSet.contains(kPropertyDefn);
	}

	@Override
	public void attachKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to attach a property defn " +
					"to a class that does not have a property definition set attached.");
		}
		
		kPropertyDefnSet.add(kPropertyDefn);
	}

	@Override
	public IKPropertyDefn getKPropertyDefnById(long id) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		for (IKPropertyDefn pDefn : kPropertyDefnSet) {
			if (pDefn.getId() == id) {
				return pDefn;
			}
		}
		return null;
	}

	@Override
	public IKPropertyDefn getKPropertyDefnByName(String name) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		for (IKPropertyDefn pDefn : kPropertyDefnSet) {
			if (pDefn.getName().equals(name)) {
				return pDefn;
			}
		}
		return null;
	}

	@Override
	public IKPropertyDefn getKPropertyDefnByNameAndType(String name, Type type) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		for (IKPropertyDefn pDefn : kPropertyDefnSet) {
			if (pDefn.getType() == type && pDefn.getName().equals(name)) {
				return pDefn;
			}
		}
		return null;
	}

}