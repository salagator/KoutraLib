package com.koutra.koutralib.domain;

/**
 * All KoutraLib entities inherit this interface.
 * 
 * @author pftakas
 */
public interface IKBase {
	/**
	 * Accessor for the unique identifier for the KoutraLib entity. Usually identifiers
	 * are unique for similar types.
	 * 
	 * @return A long value that uniquely identifies the entity.
	 */
	long getId();
}
