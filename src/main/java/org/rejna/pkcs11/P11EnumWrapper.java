package org.rejna.pkcs11;

public abstract class P11EnumWrapper<T extends P11Enum> {
	public T valueOf(int value) {
		for (T o : values())
			if (o.getValue() == value)
				return o;
		return null;
	}
	
	public abstract T[] values();
	public abstract T valueOf(String str);
}
