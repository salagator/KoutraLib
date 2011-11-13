package com.koutra.koutralib.domain.impl.delta;

import java.util.HashMap;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.impl.KObject;

public class KObjectPendingDeletionDelta extends KDelta {

	private static final long serialVersionUID = -758202744717459922L;
	
	private long superKClassId;
	
	public KObjectPendingDeletionDelta(IKObject kObject) {
		super(kObject.getId());
		this.superKClassId = kObject.getIKClass().getId();
	}

	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().handlePendingIKObjectDeletion(
				new KObject(getId(),
						KoutraPropSysFactory.getKoutraPropSys().getIKClass(superKClassId),
						new HashMap<Long, IKProperty>()));
	}

}
