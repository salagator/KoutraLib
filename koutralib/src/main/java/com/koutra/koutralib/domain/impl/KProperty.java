package com.koutra.koutralib.domain.impl;

import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.IKPropertyDefn;

public abstract class KProperty extends KBase implements IKProperty {
	
	protected IKObject kObject;
	protected IKPropertyDefn kPropertyDefn;
	protected boolean pending;
	protected boolean inConflict;
	protected IKProperty kPropertyOfRecord;
	
	public KProperty() {
		this.kObject = null;
		this.kPropertyDefn = null;
		this.pending = false;
		this.inConflict = false;
		this.kPropertyOfRecord = null;
	}
	
	public KProperty(IKObject kObject, IKPropertyDefn kPropertyDefn) {
		this.kObject = kObject;
		this.kPropertyDefn = kPropertyDefn;
		this.pending = false;
		this.inConflict = false;
		this.kPropertyOfRecord = null;
	}

	public KProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn) {
		super(id);
		this.kObject = kObject;
		this.kPropertyDefn = kPropertyDefn;
		this.pending = false;
		this.inConflict = false;
		this.kPropertyOfRecord = null;
	}

	public KProperty(long id, IKObject kObject, IKPropertyDefn kPropertyDefn,
			boolean inConflict, IKProperty kPropertyOfRecord) {
		super(id);
		this.kObject = kObject;
		this.kPropertyDefn = kPropertyDefn;
		this.pending = false;
		this.inConflict = inConflict;
		this.kPropertyOfRecord = kPropertyOfRecord;
	}

	@Override
	public IKObject getIKObject() {
		return kObject;
	}

	@Override
	public IKPropertyDefn getIKPropertyDefn() {
		return kPropertyDefn;
	}
	
	@Override
	public boolean isPending() {
		return pending;
	}

	@Override
	public boolean isInConflict() {
		return inConflict;
	}
	
	@Override
	public IKProperty getIKPropertyOfRecord() {
		return kPropertyOfRecord;
	}
}
