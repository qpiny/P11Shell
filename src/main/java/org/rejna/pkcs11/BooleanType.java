package org.rejna.pkcs11;

public enum BooleanType implements P11Enum {
	TRUE(1), FALSE(0);

	private int value;
	
	private BooleanType(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}
}
