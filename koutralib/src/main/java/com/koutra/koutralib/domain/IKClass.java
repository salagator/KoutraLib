package com.koutra.koutralib.domain;

import java.util.Set;

import com.koutra.koutralib.domain.IKPropertyDefn.Type;

/**
 * Interface representing a class in the KoutraLib framework.
 * 
 * @author pftakas
 */
public interface IKClass extends IKBase {
	IKClass getSuperIKClass();
	
	Set<IKPropertyDefn> getIKPropertyDefnSet();
	
	boolean containsIKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	void attachIKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	void detachIKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	IKPropertyDefn getIKPropertyDefnById(long id);
	
	IKPropertyDefn getIKPropertyDefnByName(String name);
	
	IKPropertyDefn getIKPropertyDefnByNameAndType(String name, Type type);
}
