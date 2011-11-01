package com.koutra.koutralib.domain;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.koutra.koutralib.domain.IKPropertyDefn.Type;
import com.koutra.koutralib.domain.impl.KClass;
import com.koutra.koutralib.domain.impl.KObject;
import com.koutra.koutralib.domain.impl.KPropertyDefn;
import com.koutra.koutralib.domain.impl.StringKProperty;

public class KoutraLibDomainTest {
	private static final String OBJECT_NAME = "Object Name";
	private static int id = 0;
	
	private static int getNextId() {
		return id++;
	}

	@Test
	public void simpleDomainTest() {
		IKPropertyDefn propDefn1 = new KPropertyDefn(
				getNextId(), "name", Type.String);
		IKPropertyDefn propDefn2 = new KPropertyDefn(
				getNextId(), "foofootos", Type.Integer);
		
		Set<IKPropertyDefn> kPropertyDefnSet = new HashSet<IKPropertyDefn>();
		
		IKClass rootKClass = new KClass(getNextId(), null, kPropertyDefnSet);
		rootKClass.attachKPropertyDefn(propDefn1);
		rootKClass.attachKPropertyDefn(propDefn2);
		
		IKObject kObject = new KObject(
				getNextId(), rootKClass, new HashMap<Long, IKProperty>());
		
		IKProperty prop1 = kObject.getKProperty(propDefn1);
		
		assertNull(prop1);
		
		kObject.setKProperty(propDefn1,
				new StringKProperty(getNextId(), kObject, propDefn1, OBJECT_NAME));
		
		prop1 = kObject.getKProperty(propDefn1);
		
		assertNotNull(prop1);
		assertEquals(OBJECT_NAME, prop1.getValue());
	}

}
