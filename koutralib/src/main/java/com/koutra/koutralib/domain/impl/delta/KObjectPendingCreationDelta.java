package com.koutra.koutralib.domain.impl.delta;

import java.util.HashMap;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.impl.KObject;

public class KObjectPendingCreationDelta extends KDelta {

	private static final long serialVersionUID = -758202744717459922L;
	
	private long superKClassId;
	
	public KObjectPendingCreationDelta(IKObject kObject) {
		super(kObject.getId());
		this.superKClassId = kObject.getIKClass().getId();
	}

	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().handlePendingIKObjectCreation(
				new KObject(getId(),
						KoutraPropSysFactory.getKoutraPropSys().getIKClass(superKClassId),
						new HashMap<Long, IKProperty>()));
	}

}
