package com.koutra.koutralib.domain;

/**
 * Represents a property of an object in the KoutraLib framework.
 * 
 * @author pftakas
 */
public interface IKProperty extends IKBase {
	IKObject getIKObject();
	
	IKPropertyDefn getIKPropertyDefn();
	
	Object getValue();
	
	boolean isPending();
	
	boolean isInConflict();
	
	IKProperty getIKPropertyOfRecord();
}
