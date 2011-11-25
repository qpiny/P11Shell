package org.rejna.pkcs11.x64;

import java.util.Vector;

import org.bridj.Pointer;
import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.Defs;
import org.rejna.pkcs11.InvalidMechanismException;
import org.rejna.pkcs11.MechanismType;
import org.rejna.pkcs11.P11Exception;
import org.rejna.pkcs11.P11Object;

public class PKCS11 extends org.rejna.pkcs11.PKCS11 {
	private long session = -1;

	public PKCS11() {
		XltCkLibrary.C_Initialize(null);
	}
	
	public void checkRet(long ret) throws P11Exception {
		checkRet((int) ret);
	}
	
	@Override
	public Attribute createAttribute(AttributeType type, Object value) {
		if (value instanceof Integer)
			return new Attribute(type, Long.valueOf((Integer)value));
		else
			return new Attribute(type, value);	
	}
	
	@Override
	public void close() {
		XltCkLibrary.C_Finalize(null);
	}
	
	@Override
	public Info getInfo() throws P11Exception {
		Info info = new Info();
		checkRet(XltCkLibrary.C_GetInfo(Pointer.pointerTo(info)));
		return info;
	}
	
	@Override
	public int[] getSlotList(boolean present) throws P11Exception {
		Pointer<Long> nSlots = Pointer.allocateLong();
		checkRet(XltCkLibrary.C_GetSlotList((byte)(present ? 1 : 0), null, nSlots));
		
		long n = nSlots.get();
		if (n == 0)
			return new int[0];
		Pointer<Long> slots = Pointer.allocateLongs(n);
		checkRet(XltCkLibrary.C_GetSlotList((byte)(present ? 1 : 0), slots, nSlots));
		
		int[] ret = new int[nSlots.get().intValue()];
		int i = 0;
		for (long l : slots.getLongs())
			ret[i++] = (int) l;
		return ret;
	}
	
	@Override
	public SlotInfo getSlotInfo(int slot) throws P11Exception {
		SlotInfo slotInfo = new SlotInfo();
		checkRet(XltCkLibrary.C_GetSlotInfo(slot, Pointer.pointerTo(slotInfo)));
		return slotInfo;
	}
	
	@Override
	public TokenInfo getTokenInfo(int token) throws P11Exception {
		TokenInfo tokenInfo = new TokenInfo();
		checkRet(XltCkLibrary.C_GetTokenInfo(token, Pointer.pointerTo(tokenInfo)));
		return tokenInfo;
	}
	
	@Override
	public MechanismType[] getMechanism(int slot) throws P11Exception, InvalidMechanismException {
		Pointer<Long> nMech = Pointer.allocateLong();
		checkRet(XltCkLibrary.C_GetMechanismList(slot, null, nMech));
		
		int n = nMech.get().intValue();
		MechanismType[] mechs = new MechanismType[n];
		
		if (n != 0) {
			Pointer<Long> mechType = Pointer.allocateLongs(n);
			checkRet(XltCkLibrary.C_GetMechanismList(slot, mechType, nMech));
			for (int i = 0; i < n; i++)
				mechs[i] = MechanismType.valueOf(mechType.get(i).intValue());
		}
		
		return mechs;
	}
	
	@Override
	public MechanismInfo getMechanismInfo(int slot, MechanismType mechType) throws P11Exception {
		MechanismInfo mechInfo = new MechanismInfo();
		checkRet(XltCkLibrary.C_GetMechanismInfo(slot, mechType.getValue(), Pointer.pointerTo(mechInfo)));
		return mechInfo;
	}
	
	@Override
	public void openSession(int slot, boolean readonly) throws P11Exception {
		Pointer<Long> session = Pointer.allocateLong();
		checkRet(XltCkLibrary.C_OpenSession(slot, Defs.CKF_SERIAL_SESSION | (readonly ? 0 : Defs.CKF_RW_SESSION), null, null, session));
		this.session = session.get();
	}
	
	@Override
	public void login(String pin) throws P11Exception {
		checkRet(XltCkLibrary.C_Login(session, Defs.CKU_USER, Pointer.pointerToBytes(pin.getBytes()), pin.length()));
	}
	
	@Override
	public void logout() throws P11Exception {
		checkRet(XltCkLibrary.C_Logout(session));
	}
	
	@Override
	public void closeSession() throws P11Exception {
		checkRet(XltCkLibrary.C_CloseSession(session));
		session = -1;
	}
	
	@Override
	public void closeAllSession(int slot) throws P11Exception {
		checkRet(XltCkLibrary.C_CloseAllSessions(slot));
		session = -1;
	}
	
	/*
	public P11Object[] listObject(int slot, ObjectType objectType) throws P11Exception {
		Attribute attribute = new Attribute();
		if (objectType != null) {
			attribute.type(XltCkLibrary.CKA_CLASS);
			attribute.pValue(Pointer.pointerToInt(objectType.getValue()));
			attribute.ulValueLen(4);
		}
		checkRet(XltCkLibrary.C_FindObjectsInit(session, Pointer.pointerTo(attribute), objectType != null ? 1 : 0));
		
		Pointer<Integer> obj = Pointer.allocateInt();
		Pointer<Integer> nObj = Pointer.allocateInt();
		checkRet(XltCkLibrary.C_FindObjects(session, obj, 1, nObj));
		
		Vector<P11Object> objs = new Vector<P11Object>();
		while (nObj.get() > 0) {
			objs.add(new P11Object(obj.get()));
			checkRet(XltCkLibrary.C_FindObjects(session, obj, 1, nObj));
		}
		
		checkRet(XltCkLibrary.C_FindObjectsFinal(session));
		return objs.toArray(new P11Object[0]);
	}
	*/
	
	@Override
	public P11Object[] findObjects(org.rejna.pkcs11.Attribute ...  criteria) throws P11Exception {
		Pointer<Attribute> attributes = Pointer.allocateArray(Attribute.class, criteria.length);
		int index = 0;
		for(org.rejna.pkcs11.Attribute criterion: criteria)
			criterion.copyTo(attributes.get(index++));
		checkRet(XltCkLibrary.C_FindObjectsInit(session, attributes, criteria.length));
		
		Pointer<Long> obj = Pointer.allocateLong();
		Pointer<Long> nObj = Pointer.allocateLong();
		checkRet(XltCkLibrary.C_FindObjects(session, obj, 1, nObj));
		
		Vector<P11Object> objs = new Vector<P11Object>();
		while (nObj.get() > 0) {
			objs.add(new P11Object(obj.get().intValue()));
			checkRet(XltCkLibrary.C_FindObjects(session, obj, 1, nObj));
		}
		
		checkRet(XltCkLibrary.C_FindObjectsFinal(session));
		return objs.toArray(new P11Object[0]);
	}
	
	@Override
	public void getAttribute(P11Object obj, org.rejna.pkcs11.Attribute attribute) throws P11Exception {
		checkRet(XltCkLibrary.C_GetAttributeValue(session, obj.getHandle(), Pointer.pointerTo(attribute).as(Attribute.class), 1));
	}
	
	/*
	private Pointer<Attribute> mapToAttribute(HashMap<AttributeType, Object> attrs) {
		Pointer<Attribute> attributes = Pointer.allocateArray(Attribute.class, attrs.size());
		int index = 0;
		for (Entry<AttributeType, Object> criterion: attrs.entrySet()) {
			Attribute attribute = attributes.get(index++);
			AttributeType type = criterion.getKey();
			type.fillTemplate(attribute, criterion.getValue());
		}
		return attributes;
	}
	*/
	/*
	public Attribute[] getAttributes(int obj, Vector<AttributeType> attrTypes) throws P11Exception {
		Pointer<Attribute> attributes = Pointer.allocateArray(Attribute.class, attrTypes.size());
		
		int index = 0;
		for (AttributeType attrType: attrTypes) {
			Attribute attribute = attributes.get(index++);
			attribute.setAttributeType(attrType);
			attribute.setSize(attrType.getDefaultSize());
			attribute.setValue(Pointer.allocateBytes(attrType.getDefaultSize()));
		}
		checkRet(XltCkLibrary.C_GetAttributeValue(session, obj, attributes, attrTypes.size()));
		
		return attributes.toArray();
	}
	*/
	@Override
	public P11Object createObject(org.rejna.pkcs11.Attribute ... attributes) throws P11Exception {
		Pointer<Long> obj = Pointer.allocateLong();
		checkRet(XltCkLibrary.C_CreateObject(session, Pointer.pointerToArray(attributes).as(Attribute.class), attributes.length, obj));
		
		return new P11Object(obj.get().intValue());
	}
	
	@Override
	public byte[] wrap(org.rejna.pkcs11.Mechanism mechanism, P11Object wrappingKey, P11Object wrappedKey, int size) throws P11Exception {
		Pointer<Byte> buffer = Pointer.allocateBytes(size);
		Pointer<Long> bufferLen = Pointer.pointerToLong(size);
		checkRet(XltCkLibrary.C_WrapKey(session, Pointer.pointerTo(mechanism).as(Mechanism.class), wrappingKey.getHandle(), wrappedKey.getHandle(), buffer, bufferLen));
		return buffer.getBytes(bufferLen.get().intValue());
	}
	
	@Override
	public Mechanism createMechanism(MechanismType type, int parameter) {
		Mechanism mechanism = new Mechanism();
		mechanism.mechanism(type.getValue());
		mechanism.parameter(Pointer.pointerToLong(parameter));
		mechanism.parameterLen(8);
		return mechanism;
	}
	
	@Override
	public Mechanism createMechanism(MechanismType type) {
		Mechanism mechanism = new Mechanism();
		mechanism.mechanism(type.getValue());
		mechanism.parameter(null);
		mechanism.parameterLen(0);
		return mechanism;
	}

	@Override
	public void setAttribute(P11Object obj, org.rejna.pkcs11.Attribute attribute)
			throws P11Exception {
		checkRet(XltCkLibrary.C_SetAttributeValue(session, obj.getHandle(), Pointer.pointerTo(attribute).as(Attribute.class), 1));
		
	}

	@Override
	public P11Object generateKey(org.rejna.pkcs11.Mechanism mechanism, org.rejna.pkcs11.Attribute ... attributes) throws P11Exception {
		Pointer<Long> handle = Pointer.allocateLong();
		checkRet(XltCkLibrary.C_GenerateKey(session, Pointer.pointerTo(mechanism).as(Mechanism.class), Pointer.pointerToArray(attributes).as(Attribute.class), attributes.length, handle));
		return new P11Object(handle.get().intValue());
	}
}