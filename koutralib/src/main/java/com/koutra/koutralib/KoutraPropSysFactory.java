package com.koutra.koutralib;

public class KoutraPropSysFactory {
	public static KoutraPropSys getKoutraPropSys() {
		return new MemoryKoutraPropSys();
	}
}
