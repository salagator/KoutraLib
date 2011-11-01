package com.koutra.koutralib.domain;

import com.koutra.koutralib.domain.IKPropertyDefn.Type;

/**
 * Interface representing an object in the KoutraLib framework.
 * 
 * @author pftakas
 */
public interface IKObject extends IKBase {
	IKClass getKClass();
	
	IKProperty getKProperty(IKPropertyDefn propertyDefn);
	
	boolean hasKProperty(IKPropertyDefn propertyDefn);
	
	IKProperty getKPropertyById(long id);
	
	IKProperty getKPropertyByName(String name);
	
	IKProperty getKPropertyByNameAndType(String name, Type type);
}
