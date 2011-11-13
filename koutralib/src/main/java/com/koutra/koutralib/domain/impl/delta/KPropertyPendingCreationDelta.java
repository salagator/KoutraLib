package com.koutra.koutralib.domain.impl.delta;

import java.io.Serializable;

import com.koutra.koutralib.KoutraPropSysFactory;
import com.koutra.koutralib.domain.DeltaApplicationException;
import com.koutra.koutralib.domain.IKProperty;
import com.koutra.koutralib.domain.impl.BooleanKProperty;
import com.koutra.koutralib.domain.impl.FloatKProperty;
import com.koutra.koutralib.domain.impl.IntegerKProperty;
import com.koutra.koutralib.domain.impl.StringKProperty;

public class KPropertyPendingCreationDelta extends KDelta {

	private static final long serialVersionUID = -2252147761410306170L;
	
	private long kObjectId;
	private long kPropertyDefnId;
	private Serializable value;

	public KPropertyPendingCreationDelta(IKProperty kProperty) {
		super(kProperty.getId());
		this.kObjectId = kProperty.getIKObject().getId();
		this.kPropertyDefnId = kProperty.getIKPropertyDefn().getId();
		this.value = (Serializable) kProperty.getValue();
	}

	@Override
	public void apply() throws DeltaApplicationException {
		IKProperty property;
		switch (KoutraPropSysFactory.getKoutraPropSys().getIKPropertyDefn(kPropertyDefnId).getType()) {
		case Boolean:
			property = new BooleanKProperty(getId(),
					KoutraPropSysFactory.getKoutraPropSys().getIKObject(kObjectId),
					KoutraPropSysFactory.getKoutraPropSys().getIKPropertyDefn(kPropertyDefnId),
					(Boolean) value);
			break;
		case String:
			property = new StringKProperty(getId(),
					KoutraPropSysFactory.getKoutraPropSys().getIKObject(kObjectId),
					KoutraPropSysFactory.getKoutraPropSys().getIKPropertyDefn(kPropertyDefnId),
					(String) value);
			break;
		case Integer:
			property = new IntegerKProperty(getId(),
					KoutraPropSysFactory.getKoutraPropSys().getIKObject(kObjectId),
					KoutraPropSysFactory.getKoutraPropSys().getIKPropertyDefn(kPropertyDefnId),
					(Long) value);
			break;
		case Float:
			property = new FloatKProperty(getId(),
					KoutraPropSysFactory.getKoutraPropSys().getIKObject(kObjectId),
					KoutraPropSysFactory.getKoutraPropSys().getIKPropertyDefn(kPropertyDefnId),
					(Double) value);
			break;
		default:
			throw new IllegalStateException("Unknown property definition type: " +
					KoutraPropSysFactory.getKoutraPropSys().getIKPropertyDefn(kPropertyDefnId).getType());
		}
		KoutraPropSysFactory.getKoutraPropSys().handlePendingIKPropertyCreation(property);
	}

}
