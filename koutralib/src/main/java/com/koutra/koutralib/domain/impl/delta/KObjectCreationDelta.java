package com.koutra.koutralib.domain.impl.delta;

import java.util.HashMap;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.impl.KObject;

public class KObjectCreationDelta extends KDelta {
	
	private static final long serialVersionUID = -4461990292479579375L;
	
	private long superKClassId;
	
	public KObjectCreationDelta(IKObject kObject) {
		super(kObject.getId());
		this.superKClassId = kObject.getIKClass().getId();
	}

	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().createIKObject(
				new KObject(getId(),
						KoutraPropSysFactory.getKoutraPropSys().getIKClass(superKClassId),
						new HashMap<Long, IKProperty>()));
	}

}
