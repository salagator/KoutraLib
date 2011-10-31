package com.koutra.koutralib.domain;

/**
 * Interface representing a property definition in the KoutraLib framework.
 * 
 * @author pftakas
 */
public interface IKPropertyDefn extends IKBase {
	public enum Type {
		String,
		Integer,
		Float,
		Boolean
	}
	
	Type getType();
	
	String getName();
}
