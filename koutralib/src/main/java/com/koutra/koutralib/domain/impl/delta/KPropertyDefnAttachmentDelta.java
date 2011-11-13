package com.koutra.koutralib.domain.impl.delta;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKClass;
import com.koutra.koutralib.domain.IKPropertyDefn;

public class KPropertyDefnAttachmentDelta extends KDelta {

	private static final long serialVersionUID = -1727670061992471973L;
	
	private long kClassId;
	private long kPropertyDefnId;
	
	public KPropertyDefnAttachmentDelta(IKClass kClass,
			IKPropertyDefn kPropertyDefn) {
		super();
		this.kClassId = kClass.getId();
		this.kPropertyDefnId = kPropertyDefn.getId();
	}
	
	@Override
	public void apply() throws DeltaApplicationException {
		KoutraPropSysFactory.getKoutraPropSys().attachIKPropertyDefn(
				KoutraPropSysFactory.getKoutraPropSys().getIKClass(kClassId),
				KoutraPropSysFactory.getKoutraPropSys().getIKPropertyDefn(kPropertyDefnId));
	}

}
