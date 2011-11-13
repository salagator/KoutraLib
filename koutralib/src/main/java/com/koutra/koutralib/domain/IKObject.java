package com.koutra.koutralib.domain;

import com.koutra.koutralib.domain.IKPropertyDefn.Type;

/**
 * Interface representing an object in the KoutraLib framework.
 * 
 * @author pftakas
 */
public interface IKObject extends IKBase {
	IKClass getIKClass();
	
	IKProperty getIKProperty(IKPropertyDefn kPropertyDefn);
	
	void setIKProperty(IKPropertyDefn kPropertyDefn, IKProperty kProperty);
	
	boolean hasIKProperty(IKPropertyDefn kPropertyDefn);
	
	IKProperty getIKPropertyById(long id);
	
	IKProperty getIKPropertyByName(String name);
	
	IKProperty getIKPropertyByNameAndType(String name, Type type);
}
