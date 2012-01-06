package org.rejna.pkcs11;


public class P11Object {
	private long handle;
	private String toString = null;
	
	public P11Object(long obj) {
		this.handle = obj;
	}

	public Attribute getAttribute(AttributeType type) throws P11Exception {
		Attribute template = type.createAttribute();
		try {
			PKCS11.getInstance().getAttribute(this, template);
		} catch (P11Exception e) {
			if (e.getCode() == Defs.CKR_BUFFER_TOO_SMALL)
				return getAttribute(type, type.getDefaultSize() * 2);
		}
		return template;
	}
	
	public Attribute getAttribute(AttributeType type, int size) throws P11Exception {
		Attribute template = type.createAttribute(size);
		try {
			PKCS11.getInstance().getAttribute(this, template);
		} catch (P11Exception e) {
			if (e.getCode() == Defs.CKR_BUFFER_TOO_SMALL)
				return getAttribute(type, size * 2);
		}
		return template;
	}
	
	public void setAttribute(AttributeType type, Object value) throws P11Exception {
		PKCS11 p11 = PKCS11.getInstance();
		p11.setAttribute(this, type.createAttribute(value)); 
	}

	public long getHandle() {
		return handle;
	}
	
	@Override
	public String toString() {
		if (toString == null) {
			try {	
				Attribute attr = getAttribute(AttributeType.CKA_CLASS);
				toString = "Object " + handle + " " + ObjectType.valueOf(((Number)attr.object()).intValue());
			} catch (Exception e) {
				toString =  "Object " + handle + " (error : " + e.getClass().getCanonicalName() + ":" + e.getMessage() + ")";
			}
		}
		return toString;
	}
}
