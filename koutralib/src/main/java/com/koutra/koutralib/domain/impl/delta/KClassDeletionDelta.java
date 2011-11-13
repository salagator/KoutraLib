package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKClass;

public class KClassDeletionDelta extends KDelta {

	private static final long serialVersionUID = 3252092009905899123L;
	
	private long superKClassId;

	public KClassDeletionDelta(IKClass kClass) {
		super(kClass.getId());
		this.superKClassId = kClass.getSuperIKClass().getId();
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().deleteIKClass(
				KoutraPropSysFactory.getKoutraPropSys().getIKClass(superKClassId));
	}

}
