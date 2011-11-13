package com.koutra.koutralib.domain.impl.delta;

import java.util.HashMap;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKObject;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.impl.KObject;

public class KObjectCreationConfirmationDelta  extends KDelta {

	private static final long serialVersionUID = 4291937144966192253L;
	
	private long superIKClassId;
	private long pendingIKObjectId;
	
	public KObjectCreationConfirmationDelta(IKObject kObject, long pendingIKObjectId) {
		super(kObject.getId());
		this.superIKClassId = kObject.getIKClass().getId();
		this.pendingIKObjectId = pendingIKObjectId;
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().handlePendingIKObjectCreationConfirmation(
				new KObject(getId(),
						KoutraPropSysFactory.getKoutraPropSys().getIKClass(superIKClassId),
						new HashMap<Long, IKProperty>()), pendingIKObjectId);
	}

}
