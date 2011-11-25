package org.rejna.pkcs11.x32;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Runtime;
import org.bridj.cpp.CPPRuntime;
import org.rejna.pkcs11.x32.Attribute;
import org.rejna.pkcs11.x32.MechanismInfo;
import org.rejna.pkcs11.x32.TokenInfo;
import org.rejna.pkcs11.x32.SlotInfo;
/**
 * Wrapper for library <b>xltCk</b><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("xltCk") 
@Runtime(CPPRuntime.class) 
public class XltCkLibrary {
	static {
		BridJ.register();
	}
	public static native int C_CancelFunction(int session);
	public static native int C_CloseAllSessions(int CK_SLOT_ID1);
	public static native int C_CloseSession(int session);
	public static native int C_CopyObject(int session, int CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1, Pointer<Integer > CK_OBJECT_HANDLE_PTR1);
	public static native int C_CreateObject(int session, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1, Pointer<Integer > CK_OBJECT_HANDLE_PTR1);
	public static native int C_Decrypt(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DecryptDigestUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DecryptFinal(int session, Pointer<Byte > CK_BYTE_PTR1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DecryptInit(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1);
	public static native int C_DecryptUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DecryptVerifyUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DeriveKey(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1, Pointer<Integer > CK_OBJECT_HANDLE_PTR1);
	public static native int C_DestroyObject(int session, int CK_OBJECT_HANDLE1);
	public static native int C_Digest(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DigestEncryptUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DigestFinal(int session, Pointer<Byte > CK_BYTE_PTR1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_DigestInit(int session, Pointer<Mechanism > CK_MECHANISM_PTR1);
	public static native int C_DigestKey(int session, int CK_OBJECT_HANDLE1);
	public static native int C_DigestUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1);
	public static native int C_Encrypt(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_EncryptFinal(int session, Pointer<Byte > CK_BYTE_PTR1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_EncryptInit(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1);
	public static native int C_EncryptUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_Finalize(Pointer<? > CK_VOID_PTR1);
	public static native int C_FindObjects(int session, Pointer<Integer > CK_OBJECT_HANDLE_PTR1, int CK_ULONG1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_FindObjectsFinal(int session);
	public static native int C_FindObjectsInit(int session, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1);
	public static native int C_GenerateKey(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1, Pointer<Integer > CK_OBJECT_HANDLE_PTR1);
	public static native int C_GenerateKeyPair(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR2, int CK_ULONG2, Pointer<Integer > CK_OBJECT_HANDLE_PTR1, Pointer<Integer > CK_OBJECT_HANDLE_PTR2);
	public static native int C_GenerateRandom(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1);
	public static native int C_GetAttributeValue(int session, int CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1);
	//public static native int C_GetFunctionList(Pointer<Pointer<CK_FUNCTION_LIST > > CK_FUNCTION_LIST_PTR_PTR1);
	public static native int C_GetFunctionStatus(int session);
	public static native int C_GetInfo(Pointer<Info > CK_INFO_PTR1);
	public static native int C_GetMechanismInfo(int CK_SLOT_ID1, int CK_MECHANISM_TYPE1, Pointer<MechanismInfo > CK_MECHANISM_INFO_PTR1);
	public static native int C_GetMechanismList(int CK_SLOT_ID1, Pointer<Integer > CK_MECHANISM_TYPE_PTR1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_GetObjectSize(int session, int CK_OBJECT_HANDLE1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_GetOperationState(int session, Pointer<Byte > CK_BYTE_PTR1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_GetSessionInfo(int session, Pointer<SessionInfo > CK_SESSION_INFO_PTR1);
	public static native int C_GetSlotInfo(int slot, Pointer<SlotInfo> slotInfo);
	public static native int C_GetSlotList(byte CK_BBOOL1, Pointer<Integer > CK_SLOT_ID_PTR1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_GetTokenInfo(int CK_SLOT_ID1, Pointer<TokenInfo > CK_TOKEN_INFO_PTR1);
	public static native int C_Initialize(Pointer<? > CK_VOID_PTR1);
	public static native int C_InitPIN(int session, Pointer<Byte > CK_CHAR_PTR1, int CK_ULONG1);
	public static native int C_InitToken(int CK_SLOT_ID1, Pointer<Byte > CK_CHAR_PTR1, int CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2);
	public static native int C_Login(int session, int CK_USER_TYPE1, Pointer<Byte > CK_CHAR_PTR1, int CK_ULONG1);
	public static native int C_Logout(int session);
	public static native int C_OpenSession(int CK_SLOT_ID1, int CK_FLAGS1, Pointer<? > CK_VOID_PTR1, Pointer<?> CK_NOTIFY1, Pointer<Integer > CK_SESSION_HANDLE_PTR1);
	public static native int C_SeedRandom(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1);
	public static native int C_SetAttributeValue(int session, int CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG1);
	public static native int C_SetOperationState(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, int CK_OBJECT_HANDLE1, int CK_OBJECT_HANDLE2);
	public static native int C_SetPIN(int session, Pointer<Byte > CK_CHAR_PTR1, int CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2, int CK_ULONG2);
	public static native int C_Sign(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_SignEncryptUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_SignFinal(int session, Pointer<Byte > CK_BYTE_PTR1, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_SignInit(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1);
	public static native int C_SignRecover(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_SignRecoverInit(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1);
	public static native int C_SignUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1);
	public static native int C_UnwrapKey(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, int CK_ULONG2, Pointer<Integer > CK_OBJECT_HANDLE_PTR1);
	public static native int C_Verify(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, int CK_ULONG2);
	public static native int C_VerifyFinal(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1);
	public static native int C_VerifyInit(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1);
	public static native int C_VerifyRecover(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Integer > CK_ULONG_PTR1);
	public static native int C_VerifyRecoverInit(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1);
	public static native int C_VerifyUpdate(int session, Pointer<Byte > CK_BYTE_PTR1, int CK_ULONG1);
	public static native int C_WaitForSlotEvent(int CK_FLAGS1, Pointer<Integer > CK_SLOT_ID_PTR1, Pointer<? > CK_VOID_PTR1);
	public static native int C_WrapKey(int session, Pointer<Mechanism > CK_MECHANISM_PTR1, int CK_OBJECT_HANDLE1, int CK_OBJECT_HANDLE2, Pointer<Byte > CK_BYTE_PTR1, Pointer<Integer > CK_ULONG_PTR1);
}
