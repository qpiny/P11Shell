package org.rejna.pkcs11;


public class P11Object {
	private int handle;
	
	public P11Object(int obj) {
		this.handle = obj;
	}

	public Attribute getAttribute(AttributeType type) throws P11Exception {
		Attribute template = type.getTemplate();
		try {
			PKCS11.getInstance().getAttribute(this, template);
		} catch (P11Exception e) {
			if (e.getCode() == Defs.CKR_BUFFER_TOO_SMALL)
				return getAttribute(type, type.getDefaultSize() * 2);
		}
		return template;
	}
	
	public Attribute getAttribute(AttributeType type, int size) throws P11Exception {
		Attribute template = type.getTemplate(size);
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
		p11.setAttribute(this, p11.createAttribute(type, value));
		
	}
	
	public int getHandle() {
		return handle;
	}
	
	@Override
	public String toString() {
		try {
			Attribute attr = getAttribute(AttributeType.CKA_CLASS);
			return "Object " + handle + " " + ObjectType.valueOf(((Number)attr.object()).intValue());
		} catch (Exception e) {
			return "Object " + handle + " (error : " + e.getClass().getCanonicalName() + ":" + e.getMessage() + ")";
		}
	}
}
