package com.koutra.koutralib.domain;

/**
 * All KoutraLib entities inherit this interface.
 * 
 * @author pftakas
 */
public interface IKBase {
	/**
	 * Setter for the unique identifier for the KoutraLib entity.
	 * @param id A long value that uniquely identifies the entity.
	 */
	void setId(long id);
	
	/**
	 * Accessor for the unique identifier for the KoutraLib entity. Usually identifiers
	 * are unique for similar types.
	 * 
	 * @return A long value that uniquely identifies the entity.
	 */
	long getId();
}
