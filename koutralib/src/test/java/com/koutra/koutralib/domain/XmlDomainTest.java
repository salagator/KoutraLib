package com.koutra.koutralib.domain;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.koutra.koutralib.domain.IKPropertyDefn.Type;
import com.koutra.koutralib.domain.util.XmlDomainBuilder;

public class XmlDomainTest {
	private static final String XMLDomainPath = "/simple_domain.xml";

	@Test
	public void XmlReaderTest() throws ParserConfigurationException, SAXException, IOException {
		XmlDomainBuilder domainBuilder = new XmlDomainBuilder(XMLDomainPath);
		
		// Check property definitions
		Set<IKPropertyDefn> propertyDefnSet = domainBuilder.getIKPropertyDefnSet();
		assertTrue(propertyDefnSet.size() == 2);
		IKPropertyDefn namePropDefn = domainBuilder.getIKPropertyDefn(1);
		assertNotNull(namePropDefn);
		assertEquals("name", namePropDefn.getName());
		assertEquals(Type.String, namePropDefn.getType());
		IKPropertyDefn otherPropPropDefn = domainBuilder.getIKPropertyDefn(2);
		assertNotNull(otherPropPropDefn);
		assertEquals("otherProp", otherPropPropDefn.getName());
		assertEquals(Type.Integer, otherPropPropDefn.getType());
		
		// Check class definitions
		Set<IKClass> classSet = domainBuilder.getIKClassSet();
		assertTrue(classSet.size() == 2);
		IKClass rootClass = domainBuilder.getIKClass(1);
		assertNotNull(rootClass);
		assertTrue(rootClass.containsIKPropertyDefn(namePropDefn));
		assertFalse(rootClass.containsIKPropertyDefn(otherPropPropDefn));
		assertNull(rootClass.getSuperIKClass());
		IKClass childClass = domainBuilder.getIKClass(2);
		assertNotNull(childClass);
		assertEquals(rootClass.getId(), childClass.getSuperIKClass().getId());
		assertTrue(childClass.containsIKPropertyDefn(namePropDefn));
		assertTrue(childClass.containsIKPropertyDefn(otherPropPropDefn));
		
		// Check objects
		Collection<IKObject> objectSet = domainBuilder.getIKObjectSet();
		assertTrue(objectSet.size() == 1);
		IKObject object = domainBuilder.getIKObject(1);
		assertNotNull(object);
		Set<IKObject> objectSetByClass = domainBuilder.getIKClassInstances(childClass.getId());
		assertTrue(objectSetByClass.size() == 1);
		assertEquals(childClass.getId(), object.getIKClass().getId());
		assertTrue(object.hasIKProperty(namePropDefn));
		assertTrue(object.hasIKProperty(otherPropPropDefn));
		assertEquals("Hello World!", object.getIKProperty(namePropDefn).getValue());
		assertEquals(new Long(123), object.getIKProperty(otherPropPropDefn).getValue());
	}
	
}
