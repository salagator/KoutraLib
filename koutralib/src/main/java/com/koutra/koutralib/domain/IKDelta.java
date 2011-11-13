package com.koutra.koutralib.domain;

import java.io.Serializable;

public interface IKDelta extends IKBase, Serializable {
	void apply() throws DeltaApplicationException;
}
