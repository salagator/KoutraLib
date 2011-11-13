package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKClass;

public class KClassUpdateDelta extends KDelta {
	
	private static final long serialVersionUID = -7916053966906197149L;
	
	private long superKClassId;

	public KClassUpdateDelta(IKClass kClass) {
		super(kClass.getId());
		this.superKClassId = kClass.getSuperIKClass().getId();
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().updateIKClass(
				KoutraPropSysFactory.getKoutraPropSys().getIKClass(superKClassId));
	}

}
