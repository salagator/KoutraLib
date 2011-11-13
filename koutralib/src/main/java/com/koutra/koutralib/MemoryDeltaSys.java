package com.koutra.koutralib;

import java.util.HashMap;
import java.util.Map;

import com.koutra.koutralib.domain.IKDelta;

public class MemoryDeltaSys extends DeltaSys {
	
	private Map<Long, IKDelta> deltaMap = new HashMap<Long, IKDelta>();

	@Override
	public void addDelta(IKDelta kDelta) {
		if (deltaMap.containsKey(kDelta.getId())) {
			throw new IllegalStateException("Trying to add a delta that " +
					"already exists to the delta system (id: " + kDelta.getId() + ").");
		}
		
		deltaMap.put(kDelta.getId(), kDelta);
	}

}
