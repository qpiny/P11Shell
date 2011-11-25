package org.rejna.pkcs11.shell;

public enum ObjectType {
	CKO_DATA(0x00000000),
	CKO_CERTIFICATE(0x00000001),
	CKO_PUBLIC_KEY(0x00000002),
	CKO_PRIVATE_KEY(0x00000003),
	CKO_SECRET_KEY(0x00000004),
	CKO_HW_FEATURE(0x00000005),
	CKO_DOMAIN_PARAMETERS(0x00000006),
	CKO_VENDOR_DEFINED(0x80000000);
	
	private int value;
	
	private ObjectType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}