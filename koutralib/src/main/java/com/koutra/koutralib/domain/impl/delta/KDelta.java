package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.domain.IKDelta;
import com.koutra.koutralib.domain.impl.KBase;

public abstract class KDelta extends KBase implements IKDelta {

	private static final long serialVersionUID = -6799887289293274043L;

	public KDelta() {
		super();
	}
	
	public KDelta(long id) {
		super(id);
	}
}
