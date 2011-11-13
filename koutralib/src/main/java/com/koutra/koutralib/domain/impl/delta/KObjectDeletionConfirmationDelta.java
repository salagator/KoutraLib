package com.koutra.koutralib.domain.impl.delta;

import java.util.HashMap;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.impl.KObject;

public class KObjectDeletionConfirmationDelta  extends KDelta {

	private static final long serialVersionUID = 4291937144966192253L;
	
	private long superIKClassId;
	
	public KObjectDeletionConfirmationDelta(IKObject kObject) {
		super(kObject.getId());
		this.superIKClassId = kObject.getIKClass().getId();
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().handlePendingIKObjectDeletionConfirmation(
				new KObject(getId(),
						KoutraPropSysFactory.getKoutraPropSys().getIKClass(superIKClassId),
						new HashMap<Long, IKProperty>()));
	}

}
