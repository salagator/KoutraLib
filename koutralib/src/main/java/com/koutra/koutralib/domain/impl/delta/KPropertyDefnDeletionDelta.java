package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKPropertyDefn;
import com.koutra.koutralib.domain.IKPropertyDefn.Type;
import com.koutra.koutralib.domain.impl.KPropertyDefn;

public class KPropertyDefnDeletionDelta extends KDelta {

	private static final long serialVersionUID = -7471059031197985807L;
	
	private String name;
	private Type type;

	public KPropertyDefnDeletionDelta(IKPropertyDefn kPropertyDefn) {
		super(kPropertyDefn.getId());
		this.name = kPropertyDefn.getName();
		this.type = kPropertyDefn.getType();
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().deleteIKPropertyDefn(
				new KPropertyDefn(getId(), name, type));
	}

}
