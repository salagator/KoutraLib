package com.koutra.koutralib.domain.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;
import com.koutra.koutralib.domain.IKPropertyDefn.Type;
import com.koutra.koutralib.domain.impl.BooleanKProperty;
import com.koutra.koutralib.domain.impl.FloatKProperty;
import com.koutra.koutralib.domain.impl.IntegerKProperty;
import com.koutra.koutralib.domain.impl.KClass;
import com.koutra.koutralib.domain.impl.KObject;
import com.koutra.koutralib.domain.impl.KProperty;
import com.koutra.koutralib.domain.impl.KPropertyDefn;
import com.koutra.koutralib.domain.impl.StringKProperty;

public class XmlDomainBuilder {
	protected Set<IKPropertyDefn> propertyDefns = new HashSet<IKPropertyDefn>();
	protected Map<Long, IKPropertyDefn> propertyDefnMap = new HashMap<Long, IKPropertyDefn>();
	protected Set<IKClass> classes = new HashSet<IKClass>();
	protected Map<Long, IKClass> classMap = new HashMap<Long, IKClass>();
	protected Map<Long, IKObject> objectMap = new HashMap<Long, IKObject>();
	protected Map<Long, Set<IKObject>> classInstanceMap = new HashMap<Long, Set<IKObject>>();
	
	public XmlDomainBuilder(String domainPath)
			throws ParserConfigurationException, SAXException, IOException {
		InputStream is = getClass().getResourceAsStream(domainPath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document document = builder.parse(is);
		
		NodeList propDefnList = document.getElementsByTagName("propDefn");
		for (int i = 0; i < propDefnList.getLength(); i++) {
			Element element = (Element) propDefnList.item(i);
			IKPropertyDefn propDefn = new KPropertyDefn(
					Long.parseLong(element.getAttribute("id")),
					element.getAttribute("name"),
					Type.valueOf(element.getAttribute("type")));
			propertyDefns.add(propDefn);
			propertyDefnMap.put(propDefn.getId(), propDefn);
		}
		
		NodeList classList = document.getElementsByTagName("class");
		for (int i = 0; i < classList.getLength(); i++) {
			Element element = (Element) classList.item(i);
			long id = Long.parseLong(element.getAttribute("id"));
			String superClassId = element.getAttribute("superClassId");
			Set<IKPropertyDefn> propertyDefnSet = new HashSet<IKPropertyDefn>();
			
			for (int j = 0; j < element.getChildNodes().getLength(); j++) {
				if (!(element.getChildNodes().item(j) instanceof Element)) {
					continue;
				}
				
				Element attachment = (Element) element.getChildNodes().item(j);
				if (!attachment.getTagName().equals("attach")) {
					continue;
				}
				
				long propDefnId = Long.parseLong(
						attachment.getAttribute("propDefnId"));
				propertyDefnSet.add(propertyDefnMap.get(propDefnId));
			}
			
			IKClass newClass = new KClass(
					id,
					superClassId.length() == 0 ? null : classMap.get(Long.parseLong(superClassId)),
					propertyDefnSet);
			classes.add(newClass);
			classMap.put(id, newClass);
		}
		
		NodeList objectList = document.getElementsByTagName("object");
		for (int i = 0; i < objectList.getLength(); i++) {
			Element element = (Element) objectList.item(i);
			long id = Long.parseLong(element.getAttribute("id"));
			long classId = Long.parseLong(element.getAttribute("classId"));
			IKObject kObject = new KObject(id, classMap.get(classId), new HashMap<Long, IKProperty>());
			
			for (int j = 0; j < element.getChildNodes().getLength(); j++) {
				if (!(element.getChildNodes().item(j) instanceof Element)) {
					continue;
				}
				
				Element property = (Element) element.getChildNodes().item(j);
				if (!property.getTagName().equals("property")) {
					continue;
				}
				long propId = Long.parseLong(property.getAttribute("id"));
				long propDefnId = Long.parseLong(property.getAttribute("propDefnId"));
				String value = property.getAttribute("value");
				
				KProperty prop;
				IKPropertyDefn propDefn = propertyDefnMap.get(propDefnId);
				switch (propDefn.getType()) {
				case String:
					prop = new StringKProperty(propId, kObject, propDefn, value);
					break;
				case Integer:
					prop = new IntegerKProperty(propId, kObject, propDefn, Long.parseLong(value));
					break;
				case Float:
					prop = new FloatKProperty(propId, kObject, propDefn, Double.parseDouble(value));
					break;
				case Boolean:
					prop = new BooleanKProperty(propId, kObject, propDefn, Boolean.parseBoolean(value));
					break;
				default:
					throw new IllegalStateException(
							"Unexpected property definition type: " + propDefn.getType());
				}
				
				kObject.setIKProperty(propDefn, prop);
			}
			
			objectMap.put(id, kObject);
			Set<IKObject> instanceSet = classInstanceMap.get(classId);
			if (instanceSet == null) {
				instanceSet = new HashSet<IKObject>();
				classInstanceMap.put(classId, instanceSet);
			}
			instanceSet.add(kObject);
		}
	}
	
	public Set<IKPropertyDefn> getIKPropertyDefnSet() {
		return propertyDefns;
	}
	
	public IKPropertyDefn getIKPropertyDefn(long id) {
		return propertyDefnMap.get(id);
	}
	
	public Set<IKClass> getIKClassSet() {
		return classes;
	}
	
	public IKClass getIKClass(long id) {
		return classMap.get(id);
	}
	
	public Collection<IKObject> getIKObjectSet() {
		return objectMap.values();
	}
	
	public IKObject getIKObject(long id) {
		return objectMap.get(id);
	}
	
	public Set<IKObject> getIKClassInstances(long classId) {
		return classInstanceMap.get(classId);
	}
}
