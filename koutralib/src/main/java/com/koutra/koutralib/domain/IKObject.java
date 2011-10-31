package com.koutra.koutralib.domain;

/**
 * Interface representing an object in the KoutraLib framework.
 * 
 * @author pftakas
 */
public interface IKObject extends IKBase {
	IKClass getKClass();
	
	IKProperty getKProperty(IKPropertyDefn propertyDefn);
}
