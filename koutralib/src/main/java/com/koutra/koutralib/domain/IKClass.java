package com.koutra.koutralib.domain;

import java.util.Set;

import com.koutra.koutralib.domain.IKPropertyDefn.Type;

/**
 * Interface representing a class in the KoutraLib framework.
 * 
 * @author pftakas
 */
public interface IKClass extends IKBase {
	IKClass getSuperKClass();
	
	Set<IKPropertyDefn> getKPropertyDefnSet();
	
	boolean containsKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	void attachKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	void detachKPropertyDefn(IKPropertyDefn kPropertyDefn);
	
	IKPropertyDefn getKPropertyDefnById(long id);
	
	IKPropertyDefn getKPropertyDefnByName(String name);
	
	IKPropertyDefn getKPropertyDefnByNameAndType(String name, Type type);
}
