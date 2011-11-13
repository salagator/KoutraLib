package com.koutra.koutralib.domain.impl;

import java.util.HashSet;
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
	public IKClass getSuperIKClass() {
		return superKClass;
	}

	@Override
	public Set<IKPropertyDefn> getIKPropertyDefnSet() {
		Set<IKPropertyDefn> retVal = new HashSet<IKPropertyDefn>(kPropertyDefnSet);
		if (superKClass != null) {
			retVal.addAll(superKClass.getIKPropertyDefnSet());
		}
		return retVal;
	}

	@Override
	public boolean containsIKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		if (kPropertyDefnSet.contains(kPropertyDefn)) {
			return true;
		}
		
		if (superKClass != null) {
			return superKClass.containsIKPropertyDefn(kPropertyDefn);
		}
		
		return false;
	}

	@Override
	public void attachIKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to attach a property defn ('" +
					kPropertyDefn.getName() +
					"') to a class that does not have a property definition set attached.");
		}
		
		if (kPropertyDefnSet.contains(kPropertyDefn)) {
			throw new IllegalArgumentException("Attempting to attach a property defn ('" +
					kPropertyDefn.getName() +
					"') to a class that has that property defn attached.");
		}
		
		kPropertyDefnSet.add(kPropertyDefn);
	}

	@Override
	public void detachIKPropertyDefn(IKPropertyDefn kPropertyDefn) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to detach a property defn ('" +
					kPropertyDefn.getName() +
					"') to a class that does not have a property definition set attached.");
		}
		
		if (!kPropertyDefnSet.contains(kPropertyDefn)) {
			throw new IllegalArgumentException("Attempting to detach a property defn ('" +
					kPropertyDefn.getName() +
					"') to a class that does not have that property defn attached.");
		}
		
		kPropertyDefnSet.remove(kPropertyDefn);
	}

	@Override
	public IKPropertyDefn getIKPropertyDefnById(long id) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		for (IKPropertyDefn pDefn : kPropertyDefnSet) {
			if (pDefn.getId() == id) {
				return pDefn;
			}
		}
		if (superKClass != null) {
			return superKClass.getIKPropertyDefnById(id);
		}
		return null;
	}

	@Override
	public IKPropertyDefn getIKPropertyDefnByName(String name) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		for (IKPropertyDefn pDefn : kPropertyDefnSet) {
			if (pDefn.getName().equals(name)) {
				return pDefn;
			}
		}
		if (superKClass != null) {
			return superKClass.getIKPropertyDefnByName(name);
		}
		return null;
	}

	@Override
	public IKPropertyDefn getIKPropertyDefnByNameAndType(String name, Type type) {
		if (kPropertyDefnSet == null) {
			throw new IllegalStateException("Attempting to retrieve a property defn " +
					"from a class that does not have a property definition set attached.");
		}
		
		for (IKPropertyDefn pDefn : kPropertyDefnSet) {
			if (pDefn.getType() == type && pDefn.getName().equals(name)) {
				return pDefn;
			}
		}
		if (superKClass != null) {
			return superKClass.getIKPropertyDefnByNameAndType(name, type);
		}
		return null;
	}

}
