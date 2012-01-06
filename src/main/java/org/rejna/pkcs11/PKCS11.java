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
	private long session = -1;
	
	public static PKCS11 getInstance() {
		if (instance == null) {
			C_Initialize(null);
			instance = new PKCS11();
		}
		return instance;
	}
	
	public void checkRet(long ret) throws P11Exception {
		if (ret != Defs.CKR_OK)
			throw new P11Exception((int)ret);
	}
	
	public void close() {
		C_Finalize(null);
	}
	
	public Info getInfo() throws P11Exception {
		Info info = new Info();
		checkRet(C_GetInfo(Pointer.pointerTo(info)));
		return info;
	}
	
	public long[] getSlotList(boolean present) throws P11Exception {
		Pointer<CLong> nSlots = Pointer.allocateCLong();
		checkRet(C_GetSlotList((byte)(present ? 1 : 0), null, nSlots));
		
		long n = nSlots.getCLong();
		if (n == 0)
			return new long[0];
		Pointer<CLong> slots = Pointer.allocateCLongs(n);
		checkRet(C_GetSlotList((byte)(present ? 1 : 0), slots, nSlots));
		return slots.getCLongs();
	}
	
	public SlotInfo getSlotInfo(int slot) throws P11Exception {
		SlotInfo slotInfo = new SlotInfo();
		checkRet(C_GetSlotInfo(slot, Pointer.pointerTo(slotInfo)));
		return slotInfo;
	}
	
	public TokenInfo getTokenInfo(int token) throws P11Exception {
		TokenInfo tokenInfo = new TokenInfo();
		checkRet(C_GetTokenInfo(token, Pointer.pointerTo(tokenInfo)));
		return tokenInfo;
	}
	
	public MechanismType[] getMechanism(int slot) throws P11Exception, InvalidMechanismException {
		Pointer<CLong> nMech = Pointer.allocateCLong();
		checkRet(C_GetMechanismList(slot, null, nMech));
		
		int n = nMech.get().intValue();
		MechanismType[] mechs = new MechanismType[n];
		
		if (n != 0) {
			Pointer<CLong> mechType = Pointer.allocateCLongs(n);
			checkRet(C_GetMechanismList(slot, mechType, nMech));
			for (int i = 0; i < n; i++)
				mechs[i] = MechanismType.valueOf(mechType.get(i).intValue());
		}
		
		return mechs;
	}
	
	public MechanismInfo getMechanismInfo(int slot, MechanismType mechType) throws P11Exception {
		MechanismInfo mechInfo = new MechanismInfo();
		checkRet(C_GetMechanismInfo(slot, mechType.getValue(), Pointer.pointerTo(mechInfo)));
		return mechInfo;
	}
	
	public void openSession(int slot, boolean readonly) throws P11Exception {
		Pointer<CLong> session = Pointer.allocateCLong();
		checkRet(C_OpenSession(slot, Defs.CKF_SERIAL_SESSION | (readonly ? 0 : Defs.CKF_RW_SESSION), null, null, session));
		this.session = session.get().longValue();
	}
	
	public void login(String pin) throws P11Exception {
		if (pin == null)
			checkRet(C_Login(session, Defs.CKU_USER, null, 0));
		else
			checkRet(C_Login(session,  Defs.CKU_USER, Pointer.pointerToBytes(pin.getBytes()), pin.length()));
	}
	
	public void logout() throws P11Exception {
		checkRet(C_Logout(session));
	}
	
	public void closeSession() throws P11Exception {
		checkRet(C_CloseSession(session));
		session = -1;
	}
	
	public void closeAllSession(int slot) throws P11Exception {
		checkRet(C_CloseAllSessions(slot));
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
	
	public P11Object[] findObjects(org.rejna.pkcs11.Attribute ... criteria) throws P11Exception {
		Pointer<Attribute> attributes = Pointer.allocateArray(Attribute.class, criteria.length);
		int index = 0;
		for(org.rejna.pkcs11.Attribute criterion: criteria)
			criterion.copyTo(attributes.get(index++));
		checkRet(C_FindObjectsInit(session, attributes, criteria.length));
		
		Pointer<CLong> obj = Pointer.allocateCLong();
		Pointer<CLong> nObj = Pointer.allocateCLong();
		checkRet(C_FindObjects(session, obj, 1, nObj));
		
		Vector<P11Object> objs = new Vector<P11Object>();
		while (nObj.get().longValue() > 0) {
			objs.add(new P11Object(obj.get().longValue()));
			checkRet(C_FindObjects(session, obj, 1, nObj));
		}
		
		checkRet(C_FindObjectsFinal(session));
		return objs.toArray(new P11Object[0]);
	}
	
	public void getAttribute(P11Object obj, org.rejna.pkcs11.Attribute attribute) throws P11Exception {
		checkRet(C_GetAttributeValue(session, obj.getHandle(), Pointer.pointerTo(attribute).as(Attribute.class), 1));
	}
	
	public P11Object createObject(org.rejna.pkcs11.Attribute ... attributes) throws P11Exception {
		Pointer<CLong> obj = Pointer.allocateCLong();
		Pointer<Attribute> attrs = Pointer.allocateArray(Attribute.class, attributes.length);
		int index = 0;
		for(org.rejna.pkcs11.Attribute attr: attributes)
			attr.copyTo(attrs.get(index++));
		checkRet(C_CreateObject(session, attrs, attributes.length, obj));
		
		return new P11Object(obj.getLong());
	}
	
	public byte[] wrap(org.rejna.pkcs11.Mechanism mechanism, P11Object wrappingKey, P11Object wrappedKey, int size) throws P11Exception {
		Pointer<Byte> buffer = Pointer.allocateBytes(size);
		Pointer<CLong> bufferLen = Pointer.pointerToCLong(size);
		checkRet(C_WrapKey(session, Pointer.pointerTo(mechanism).as(Mechanism.class), wrappingKey.getHandle(), wrappedKey.getHandle(), buffer, bufferLen));
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
		checkRet(C_SetAttributeValue(session, obj.getHandle(), Pointer.pointerTo(attribute).as(Attribute.class), 1));
	}

	public P11Object generateKey(org.rejna.pkcs11.Mechanism mechanism, org.rejna.pkcs11.Attribute ... attributes) throws P11Exception {
		Pointer<CLong> handle = Pointer.allocateCLong();
		Pointer<Attribute> attrs = Pointer.allocateArray(Attribute.class, attributes.length);
		int index = 0;
		for(org.rejna.pkcs11.Attribute attr: attributes)
			attr.copyTo(attrs.get(index++));
		checkRet(C_GenerateKey(session, Pointer.pointerTo(mechanism).as(Mechanism.class), attrs, attributes.length, handle));
		return new P11Object(handle.getLong());
	}
	
	private static native long C_CancelFunction(long session);
	private static native long C_CloseAllSessions(long CK_SLOT_ID1);
	private static native long C_CloseSession(long session);
	private static native long C_CopyObject(long session, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<CLong> CK_OBJECT_HANDLE_PTR1);
	private static native long C_CreateObject(long session, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native long C_Decrypt(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DecryptDigestUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DecryptFinal(long session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DecryptInit(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	private static native long C_DecryptUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DecryptVerifyUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DeriveKey(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native long C_DestroyObject(long session, long CK_OBJECT_HANDLE1);
	private static native long C_Digest(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DigestEncryptUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DigestFinal(long session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_DigestInit(long session, Pointer<Mechanism > CK_MECHANISM_PTR1);
	private static native long C_DigestKey(long session, long CK_OBJECT_HANDLE1);
	private static native long C_DigestUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	private static native long C_Encrypt(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_EncryptFinal(long session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_EncryptInit(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	private static native long C_EncryptUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_Finalize(Pointer<? > CK_VOID_PTR1);
	private static native long C_FindObjects(long session, Pointer<CLong> CK_OBJECT_HANDLE_PTR1, long CK_ULONG1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_FindObjectsFinal(long session);
	private static native long C_FindObjectsInit(long session, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1);
	private static native long C_GenerateKey(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native long C_GenerateKeyPair(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR2, long CK_ULONG2, Pointer<CLong > CK_OBJECT_HANDLE_PTR1, Pointer<CLong > CK_OBJECT_HANDLE_PTR2);
	private static native long C_GenerateRandom(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	private static native long C_GetAttributeValue(long session, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1);
	//private static native long C_GetFunctionList(Pointer<Pointer<CK_FUNCTION_LIST > > CK_FUNCTION_LIST_PTR_PTR1);
	private static native long C_GetFunctionStatus(long session);
	private static native long C_GetInfo(Pointer<Info > CK_INFO_PTR1);
	private static native long C_GetMechanismInfo(long CK_SLOT_ID1, long CK_MECHANISM_TYPE1, Pointer<MechanismInfo > CK_MECHANISM_INFO_PTR1);
	private static native long C_GetMechanismList(long CK_SLOT_ID1, Pointer<CLong > CK_MECHANISM_TYPE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_GetObjectSize(long session, long CK_OBJECT_HANDLE1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_GetOperationState(long session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_GetSessionInfo(long session, Pointer<SessionInfo > CK_SESSION_INFO_PTR1);
	private static native long C_GetSlotInfo(long slot, Pointer<SlotInfo> slotInfo);
	private static native long C_GetSlotList(byte CK_BBOOL1, Pointer<CLong > CK_SLOT_ID_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_GetTokenInfo(long CK_SLOT_ID1, Pointer<TokenInfo > CK_TOKEN_INFO_PTR1);
	private static native long C_Initialize(Pointer<? > CK_VOID_PTR1);
	private static native long C_InitPIN(long session, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1);
	private static native long C_InitToken(long CK_SLOT_ID1, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2);
	private static native long C_Login(long session, long CK_USER_TYPE1, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1);
	private static native long C_Logout(long session);
	private static native long C_OpenSession(long CK_SLOT_ID1, long CK_FLAGS1, Pointer<? > CK_VOID_PTR1, Pointer<?> CK_NOTIFY1, Pointer<CLong > CK_SESSION_HANDLE_PTR1);
	private static native long C_SeedRandom(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	private static native long C_SetAttributeValue(long session, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1);
	private static native long C_SetOperationState(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, long CK_OBJECT_HANDLE1, long CK_OBJECT_HANDLE2);
	private static native long C_SetPIN(long session, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2, long CK_ULONG2);
	private static native long C_Sign(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_SignEncryptUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_SignFinal(long session, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_SignInit(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	private static native long C_SignRecover(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_SignRecoverInit(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	private static native long C_SignUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	private static native long C_UnwrapKey(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG2, Pointer<CLong > CK_OBJECT_HANDLE_PTR1);
	private static native long C_Verify(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, long CK_ULONG2);
	private static native long C_VerifyFinal(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	private static native long C_VerifyInit(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	private static native long C_VerifyRecover(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<CLong > CK_ULONG_PTR1);
	private static native long C_VerifyRecoverInit(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	private static native long C_VerifyUpdate(long session, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	private static native long C_WaitForSlotEvent(long CK_FLAGS1, Pointer<CLong > CK_SLOT_ID_PTR1, Pointer<? > CK_VOID_PTR1);
	private static native long C_WrapKey(long session, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1, long CK_OBJECT_HANDLE2, Pointer<Byte > CK_BYTE_PTR1, Pointer<CLong > CK_ULONG_PTR1);
}