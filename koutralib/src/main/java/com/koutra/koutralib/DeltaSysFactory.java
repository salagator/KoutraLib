package com.koutra.koutralib;

public class DeltaSysFactory {
	public static DeltaSys getDeltaSys() {
		return new MemoryDeltaSys();
	}
}
