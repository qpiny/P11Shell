package org.rejna.pkcs11;

public abstract class PKCS11 {
	private static PKCS11 instance = null;
	
	public static PKCS11 getInstance(int version) {
		if (instance == null) {
			if (version == 32)
				instance = new org.rejna.pkcs11.x32.PKCS11();
			else if (version == 64) {
				instance = new org.rejna.pkcs11.x64.PKCS11();
			}
		}
		return instance;
	}
	
	public static PKCS11 getInstance() {
		return instance;
	}
	
	public void checkRet(int ret) throws P11Exception {
		if (ret != Defs.CKR_OK)
			throw new P11Exception(ret);
	}
	
	public abstract Attribute createAttribute(AttributeType type, Object value);
	
	public abstract void close() throws P11Exception;
	public abstract Info getInfo() throws P11Exception;
	public abstract int[] getSlotList(boolean present) throws P11Exception;
	public abstract SlotInfo getSlotInfo(int slot) throws P11Exception;
	public abstract TokenInfo getTokenInfo(int token) throws P11Exception;
	public abstract MechanismType[] getMechanism(int slot) throws P11Exception, InvalidMechanismException;
	public abstract MechanismInfo getMechanismInfo(int slot, MechanismType mechType) throws P11Exception;
	public abstract void openSession(int slot, boolean readonly) throws P11Exception;
	public abstract void login(String pin) throws P11Exception;
	public abstract void logout() throws P11Exception;
	public abstract void closeSession() throws P11Exception;
	public abstract void closeAllSession(int slot) throws P11Exception;
	public abstract P11Object[] findObjects(Attribute ... criteria) throws P11Exception;
	public abstract void getAttribute(P11Object obj, Attribute attribute) throws P11Exception;
	public abstract void setAttribute(P11Object obj, Attribute attribute) throws P11Exception;
	public abstract P11Object createObject(Attribute ... attributes) throws P11Exception;
	public abstract byte[] wrap(Mechanism mechanism, P11Object wrappingKey, P11Object wrappedKey, int size) throws P11Exception;
	public abstract Mechanism createMechanism(MechanismType type, int parameter);
	public abstract Mechanism createMechanism(MechanismType type);
	public abstract P11Object generateKey(Mechanism mechanism, org.rejna.pkcs11.Attribute ... attributes) throws P11Exception;
}


/*
byte[] keyValue = {0x11,0x12,0x13, 0x14, 0x15,0x16,0x17,0x18};

CryptokiCollection template = new CryptokiCollection();
template.Add(new ObjectAttribute(ObjectAttribute.CKA_CLASS, CryptokiObject.CKO_SECRET_KEY));
template.Add(new ObjectAttribute(ObjectAttribute.CKA_KEY_TYPE, Key.CKK_DES));
template.Add(new ObjectAttribute(ObjectAttribute.CKA_ID, id));
template.Add(new ObjectAttribute(ObjectAttribute.CKA_LABEL, label));
template.Add(new ObjectAttribute(ObjectAttribute.CKA_TOKEN, true));                
template.Add(new ObjectAttribute(ObjectAttribute.CKA_VALYE, keyValue));
template.Add(new ObjectAttribute(ObjectAttribute.CKA_PRIVATE, true));
template.Add(new ObjectAttribute(ObjectAttribute.CKA_MODIFIABLE, false));

CryptokiObject deskey = CurrentSession.Objects.Create(template);
*/