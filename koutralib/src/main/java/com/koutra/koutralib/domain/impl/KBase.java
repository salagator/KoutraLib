package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKBase;

public abstract class KBase implements IKBase {
	protected long id;
	
	public KBase() {
		this.id = -1;
	}
	
	public KBase(long id) {
		this.id = id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
}
