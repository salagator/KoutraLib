package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKPropertyDefn;
import com.koutra.koutralib.domain.IKPropertyDefn.Type;
import com.koutra.koutralib.domain.impl.KPropertyDefn;

public class KPropertyDefnCreationDelta extends KDelta {
	
	private static final long serialVersionUID = -7826038985879562086L;

	private String name;
	private Type type;

	public KPropertyDefnCreationDelta(IKPropertyDefn kPropertyDefn) {
		super(kPropertyDefn.getId());
		this.name = kPropertyDefn.getName();
		this.type = kPropertyDefn.getType();
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().createIKPropertyDefn(
				new KPropertyDefn(getId(), name, type));
	}

}
