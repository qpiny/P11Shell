package org.rejna.pkcs11;

import java.util.Vector;

import org.bridj.BridJ;
import org.bridj.CLong;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Runtime;
import org.bridj.cpp.CPPRuntime;

@Library("xltCk") 
@Runtime(CPPRuntime.class) 
public class PKCS11 {
	static {
		BridJ.register();
	}
	private static PKCS11 instance = null;
	private CLong session = null;
	
	public static PKCS11 getInstance() {
		if (instance == null) {
			C_Initialize(null);
			instance = new PKCS11();
		}
		return instance;
	}
	
	public void checkRet(CLong ret) throws P11Exception {
		int r = ret.intValue();
		if (r != Defs.CKR_OK)
			throw new P11Exception(r);
	}
	
	public void close() {
		C_Finalize(null);
	}
	
	public Info getInfo() throws P11Exception {
		Info info = new Info();
		checkRet(C_GetInfo(Pointer.pointerTo(info)));
		return info;
	}
	
	public int[] getSlotList(boolean present) throws P11Exception {
		Pointer<CLong> nSlots = Pointer.allocateCLong();
		checkRet(C_GetSlotList((byte)(present ? 1 : 0), null, nSlots));
		
		long n = nSlots.getCLong();
		if (n == 0)
			return new int[0];
		Pointer<CLong> slots = Pointer.allocateCLongs(n);
		checkRet(C_GetSlotList((byte)(present ? 1 : 0), slots, nSlots));
		return slots.getInts();
	}
	
	public SlotInfo getSlotInfo(int slot) throws P11Exception {
		SlotInfo slotInfo = new SlotInfo();
		checkRet(C_GetSlotInfo(new CLong(slot), Pointer.pointerTo(slotInfo)));
		return slotInfo;
	}
	
	public TokenInfo getTokenInfo(int token) throws P11Exception {
		TokenInfo tokenInfo = new TokenInfo();
		checkRet(C_GetTokenInfo(new CLong(token), Pointer.pointerTo(tokenInfo)));
		return tokenInfo;
	}
	
	public MechanismType[] getMechanism(int slot) throws P11Exception, InvalidMechanismException {
		CLong clslot = new CLong(slot);
		Pointer<CLong> nMech = Pointer.allocateCLong();
		checkRet(C_GetMechanismList(clslot, null, nMech));
		
		int n = nMech.get().intValue();
		MechanismType[] mechs = new MechanismType[n];
		
		if (n != 0) {
			Pointer<CLong> mechType = Pointer.allocateCLongs(n * 2);
			checkRet(C_GetMechanismList(clslot, mechType, nMech));
			for (int i = 0; i < n; i++)
				mechs[i] = MechanismType.valueOf(mechType.get(i).intValue());
		}
		
		return mechs;
	}
	
	public MechanismInfo getMechanismInfo(int slot, MechanismType mechType) throws P11Exception {
		MechanismInfo mechInfo = new MechanismInfo();
		checkRet(C_GetMechanismInfo(new CLong(slot), new CLong(mechType.getValue()), Pointer.pointerTo(mechInfo)));
		return mechInfo;
	}
	
	public void openSession(int slot, boolean readonly) throws P11Exception {
		Pointer<CLong> session = Pointer.allocateCLong();
		checkRet(C_OpenSession(new CLong(slot), new CLong(Defs.CKF_SERIAL_SESSION | (readonly ? 0 : Defs.CKF_RW_SESSION)), null, null, session));
		this.session = session.get();
	}
	
	public void login(String pin) throws P11Exception {
		if (pin == null)
			checkRet(C_Login(session, new CLong(Defs.CKU_USER), null, new CLong(0)));
		else
			checkRet(C_Login(session,  new CLong(Defs.CKU_USER), Pointer.pointerToBytes(pin.getBytes()), new CLong(pin.length())));
	}
	
	public void logout() throws P11Exception {
		checkRet(C_Logout(session));
	}
	
	public void closeSession() throws P11Exception {
		checkRet(C_CloseSession(session));
		session = null;
	}
	
	public void closeAllSession(int slot) throws P11Exception {
		checkRet(C_CloseAllSessions(new CLong(slot)));
		session = null;
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
	
	public P11Object[] findObjects(org.rejna.pkcs11.Attribute ... criteria) throws P11Exception {
		Pointer<Attribute> attributes = Pointer.allocateArray(Attribute.class, criteria.length);
		int index = 0;
		for(org.rejna.pkcs11.Attribute criterion: criteria)
			criterion.copyTo(attributes.get(index++));
		checkRet(C_FindObjectsInit(session, attributes, new CLong(criteria.length)));
		
		Pointer<CLong> obj = Pointer.allocateCLong();
		Pointer<CLong> nObj = Pointer.allocateCLong();
		CLong one = new CLong(1);
		checkRet(C_FindObjects(session, obj, one, nObj));
		
		Vector<P11Object> objs = new Vector<P11Object>();
		while (nObj.get().longValue() > 0) {
			objs.add(new P11Object(obj.get().longValue()));
			checkRet(C_FindObjects(session, obj, one, nObj));
		}
		
		checkRet(C_FindObjectsFinal(session));
		return objs.toArray(new P11Object[0]);
	}
	
	public void getAttribute(P11Object obj, org.rejna.pkcs11.Attribute attribute) throws P11Exception {
		checkRet(C_GetAttributeValue(session, new CLong(obj.getHandle()), Pointer.pointerTo(attribute).as(Attribute.class), new CLong(1)));
	}
	
	public P11Object createObject(org.rejna.pkcs11.Attribute ... attributes) throws P11Exception {
		Pointer<CLong> obj = Pointer.allocateCLong();
		Pointer<Attribute> attrs = Pointer.allocateArray(Attribute.class, attributes.length);
		int index = 0;
		for(org.rejna.pkcs11.Attribute attr: attributes)
			attr.copyTo(attrs.get(index++));
		checkRet(C_CreateObject(session, attrs, new CLong(attributes.length), obj));
		
		return new P11Object(obj.getLong());
	}
	
	public byte[] wrap(org.rejna.pkcs11.Mechanism mechanism, P11Object wrappingKey, P11Object wrappedKey, int size) throws P11Exception {
		Pointer<Byte> buffer = Pointer.allocateBytes(size);
		Pointer<CLong> bufferLen = Pointer.pointerToCLong(size);
		checkRet(C_WrapKey(session, Pointer.pointerTo(mechanism).as(Mechanism.class), new CLong(wrappingKey.getHandle()), new CLong(wrappedKey.getHandle()), buffer, bufferLen));
		return buffer.getBytes(bufferLen.getInt());
	}
	/*
	public Mechanism createMechanism(MechanismType type, int parameter) {
		Mechanism mechanism = new Mechanism();
		mechanism.mechanism(type.getValue());
		mechanism.parameter(Pointer.pointerToInt(parameter));
		mechanism.parameterLen(4);
		return mechanism;
	}
	*/
	/*
	public Mechanism createMechanism(MechanismType type) {
		Mechanism mechanism = new Mechanism();
		mechanism.mechanism(type.getValue());
		mechanism.parameter(null);
		mechanism.parameterLen(0);
		return mechanism;
	}
	*/
	
	public void setAttribute(P11Object obj, org.rejna.pkcs11.Attribute attribute) throws P11Exception {
		checkRet(C_SetAttributeValue(session, new CLong(obj.getHandle()), Pointer.pointerTo(attribute).as(Attribute.class), new CLong(1)));
	}

	public P11Object generateKey(org.rejna.pkcs11.Mechanism mechanism, org.rejna.pkcs11.Attribute ... attributes) throws P11Exception {
		Pointer<CLong> handle = Pointer.allocateCLong();
		Pointer<Attribute> attrs = Pointer.allocateArray(Attribute.class, attributes.length);
		int index = 0;
		for(org.rejna.pkcs11.Attribute attr: attributes)
			attr.copyTo(attrs.get(index++));
		checkRet(C_GenerateKey(session, Pointer.pointerTo(mechanism).as(Mechanism.class), attrs, new CLong(attributes.length), handle));
		return new P11Object(handle.getLong());
	}
	
	private static native CLong C_CancelFunction(CLong session);
	private static native CLong C_CloseAllSessions(CLong CK_SLOT_ID1);
	private static native CLong C_CloseSession(CLong session);
	private static native CLong C_CopyObject(CLong session, CLong CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native CLong C_CreateObject(CLong session, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native CLong C_Decrypt(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DecryptDigestUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DecryptFinal(CLong session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DecryptInit(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_DecryptUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DecryptVerifyUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DeriveKey(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native CLong C_DestroyObject(CLong session, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_Digest(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DigestEncryptUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DigestFinal(CLong session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_DigestInit(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1);
	private static native CLong C_DigestKey(CLong session, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_DigestUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_Encrypt(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_EncryptFinal(CLong session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_EncryptInit(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_EncryptUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_Finalize(Pointer<? > CK_VOID_PTR1);
	private static native CLong C_FindObjects(CLong session, Pointer<CLong > CK_OBJECT_HANDLE_PTR1, CLong CK_ULONG1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_FindObjectsFinal(CLong session);
	private static native CLong C_FindObjectsInit(CLong session, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_GenerateKey(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native CLong C_GenerateKeyPair(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR2, CLong CK_ULONG2, Pointer<CLong > CK_OBJECT_HANDLE_PTR1, Pointer<CLong > CK_OBJECT_HANDLE_PTR2);
	private static native CLong C_GenerateRandom(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_GetAttributeValue(CLong session, CLong CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1);
	//private static native CLong C_GetFunctionList(Pointer<Pointer<CK_FUNCTION_LIST > > CK_FUNCTION_LIST_PTR_PTR1);
	private static native CLong C_GetFunctionStatus(CLong session);
	private static native CLong C_GetInfo(Pointer<Info > CK_INFO_PTR1);
	private static native CLong C_GetMechanismInfo(CLong CK_SLOT_ID1, CLong CK_MECHANISM_TYPE1, Pointer<MechanismInfo > CK_MECHANISM_INFO_PTR1);
	private static native CLong C_GetMechanismList(CLong CK_SLOT_ID1, Pointer<CLong > CK_MECHANISM_TYPE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_GetObjectSize(CLong session, CLong CK_OBJECT_HANDLE1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_GetOperationState(CLong session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_GetSessionInfo(CLong session, Pointer<SessionInfo > CK_SESSION_INFO_PTR1);
	private static native CLong C_GetSlotInfo(CLong slot, Pointer<SlotInfo> slotInfo);
	private static native CLong C_GetSlotList(byte CK_BBOOL1, Pointer<CLong > CK_SLOT_ID_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_GetTokenInfo(CLong CK_SLOT_ID1, Pointer<TokenInfo > CK_TOKEN_INFO_PTR1);
	private static native CLong C_Initialize(Pointer<? > CK_VOID_PTR1);
	private static native CLong C_InitPIN(CLong session, Pointer<Byte > CK_CHAR_PTR1, CLong CK_ULONG1);
	private static native CLong C_InitToken(CLong CK_SLOT_ID1, Pointer<Byte > CK_CHAR_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2);
	private static native CLong C_Login(CLong session, CLong CK_USER_TYPE1, Pointer<Byte > CK_CHAR_PTR1, CLong CK_ULONG1);
	private static native CLong C_Logout(CLong session);
	private static native CLong C_OpenSession(CLong CK_SLOT_ID1, CLong CK_FLAGS1, Pointer<? > CK_VOID_PTR1, Pointer<?> CK_NOTIFY1, Pointer<CLong > CK_SESSION_HANDLE_PTR1);
	private static native CLong C_SeedRandom(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_SetAttributeValue(CLong session, CLong CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_SetOperationState(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, CLong CK_OBJECT_HANDLE1, CLong CK_OBJECT_HANDLE2);
	private static native CLong C_SetPIN(CLong session, Pointer<Byte > CK_CHAR_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2, CLong CK_ULONG2);
	private static native CLong C_Sign(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_SignEncryptUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_SignFinal(CLong session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_SignInit(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_SignRecover(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_SignRecoverInit(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_SignUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_UnwrapKey(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, CLong CK_ULONG2, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native CLong C_Verify(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, CLong CK_ULONG2);
	private static native CLong C_VerifyFinal(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_VerifyInit(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_VerifyRecover(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native CLong C_VerifyRecoverInit(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1);
	private static native CLong C_VerifyUpdate(CLong session, Pointer<Byte > CK_BYTE_PTR1, CLong CK_ULONG1);
	private static native CLong C_WaitForSlotEvent(CLong CK_FLAGS1, Pointer<CLong > CK_SLOT_ID_PTR1, Pointer<? > CK_VOID_PTR1);
	private static native CLong C_WrapKey(CLong session, Pointer<Mechanism > CK_MECHANISM_PTR1, CLong CK_OBJECT_HANDLE1, CLong CK_OBJECT_HANDLE2, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);

}