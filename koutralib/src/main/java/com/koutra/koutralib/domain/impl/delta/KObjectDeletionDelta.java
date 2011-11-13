package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKObject;

public class KObjectDeletionDelta extends KDelta {
	
	private static final long serialVersionUID = -3188864259882613502L;

	public KObjectDeletionDelta(IKObject kObject) {
		super(kObject.getId());
	}

	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().deleteIKObject(
				KoutraPropSysFactory.getKoutraPropSys().getIKObject(getId()));
	}

}
