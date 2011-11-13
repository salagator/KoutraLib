package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKClass;

public class KClassCreationDelta extends KDelta {
	
	private static final long serialVersionUID = 9154614417938761119L;
	
	private long superKClassId;

	public KClassCreationDelta(IKClass kClass) {
		super(kClass.getId());
		this.superKClassId = kClass.getSuperIKClass().getId();
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().createIKClass(
				KoutraPropSysFactory.getKoutraPropSys().getIKClass(superKClassId));
	}

}
