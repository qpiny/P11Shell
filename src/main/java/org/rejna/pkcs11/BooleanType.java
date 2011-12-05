package org.rejna.pkcs11;

public enum BooleanType implements P11Enum {
	TRUE(true), FALSE(false);

	private boolean value;
	
	private BooleanType(boolean value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value ?  1 : 0;
	}

	public boolean getBoolValue() {
		return value;
	}
}
