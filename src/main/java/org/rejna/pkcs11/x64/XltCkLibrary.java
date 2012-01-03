package org.rejna.pkcs11.x64;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Runtime;
import org.bridj.cpp.CPPRuntime;
import org.rejna.pkcs11.x64.SlotInfo;
import org.rejna.pkcs11.x64.Attribute;
import org.rejna.pkcs11.x64.MechanismInfo;
import org.rejna.pkcs11.x64.TokenInfo;
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
	public static native long C_CancelFunction(long CK_SESSION_HANDLE1);
	public static native long C_CloseAllSessions(long CK_SLOT_ID1);
	public static native long C_CloseSession(long CK_SESSION_HANDLE1);
	public static native long C_CopyObject(long CK_SESSION_HANDLE1, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<Long > CK_OBJECT_HANDLE_PTR1);
	public static native long C_CreateObject(long CK_SESSION_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<Long > CK_OBJECT_HANDLE_PTR1);
	public static native long C_Decrypt(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DecryptDigestUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DecryptFinal(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DecryptInit(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	public static native long C_DecryptUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DecryptVerifyUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DeriveKey(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<Long > CK_OBJECT_HANDLE_PTR1);
	public static native long C_DestroyObject(long CK_SESSION_HANDLE1, long CK_OBJECT_HANDLE1);
	public static native long C_Digest(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DigestEncryptUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DigestFinal(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_DigestInit(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1);
	public static native long C_DigestKey(long CK_SESSION_HANDLE1, long CK_OBJECT_HANDLE1);
	public static native long C_DigestUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	public static native long C_Encrypt(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_EncryptFinal(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_EncryptInit(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	public static native long C_EncryptUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_Finalize(Pointer<? > CK_VOID_PTR1);
	public static native long C_FindObjects(long CK_SESSION_HANDLE1, Pointer<Long > CK_OBJECT_HANDLE_PTR1, long CK_ULONG1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_FindObjectsFinal(long CK_SESSION_HANDLE1);
	public static native long C_FindObjectsInit(long CK_SESSION_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1);
	public static native long C_GenerateKey(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<Long > CK_OBJECT_HANDLE_PTR1);
	public static native long C_GenerateKeyPair(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR2, long CK_ULONG2, Pointer<Long > CK_OBJECT_HANDLE_PTR1, Pointer<Long > CK_OBJECT_HANDLE_PTR2);
	public static native long C_GenerateRandom(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	public static native long C_GetAttributeValue(long CK_SESSION_HANDLE1, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1);
	//public static native long C_GetFunctionList(Pointer<Pointer<CK_FUNCTION_LIST > > CK_FUNCTION_LIST_PTR_PTR1);
	public static native long C_GetFunctionStatus(long CK_SESSION_HANDLE1);
	public static native long C_GetInfo(Pointer<Info> info);
	public static native long C_GetMechanismInfo(long CK_SLOT_ID1, long CK_MECHANISM_TYPE1, Pointer<MechanismInfo > CK_MECHANISM_INFO_PTR1);
	public static native long C_GetMechanismList(long CK_SLOT_ID1, Pointer<Long > CK_MECHANISM_TYPE_PTR1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_GetObjectSize(long CK_SESSION_HANDLE1, long CK_OBJECT_HANDLE1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_GetOperationState(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_GetSessionInfo(long CK_SESSION_HANDLE1, Pointer<SessionInfo > CK_SESSION_INFO_PTR1);
	public static native long C_GetSlotInfo(long slot, Pointer<SlotInfo> slotInfo);
	public static native long C_GetSlotList(byte CK_BBOOL1, Pointer<Long > CK_SLOT_ID_PTR1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_GetTokenInfo(long CK_SLOT_ID1, Pointer<TokenInfo > CK_TOKEN_INFO_PTR1);
	public static native long C_Initialize(Pointer<? > CK_VOID_PTR1);
	public static native long C_InitPIN(long CK_SESSION_HANDLE1, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1);
	public static native long C_InitToken(long CK_SLOT_ID1, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2);
	public static native long C_Login(long CK_SESSION_HANDLE1, long CK_USER_TYPE1, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1);
	public static native long C_Logout(long CK_SESSION_HANDLE1);
	public static native long C_OpenSession(long CK_SLOT_ID1, long CK_FLAGS1, Pointer<? > CK_VOID_PTR1, Pointer<?> CK_NOTIFY1, Pointer<Long > CK_SESSION_HANDLE_PTR1);
	public static native long C_SeedRandom(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	public static native long C_SetAttributeValue(long CK_SESSION_HANDLE1, long CK_OBJECT_HANDLE1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG1);
	public static native long C_SetOperationState(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, long CK_OBJECT_HANDLE1, long CK_OBJECT_HANDLE2);
	public static native long C_SetPIN(long CK_SESSION_HANDLE1, Pointer<Byte > CK_CHAR_PTR1, long CK_ULONG1, Pointer<Byte > CK_CHAR_PTR2, long CK_ULONG2);
	public static native long C_Sign(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_SignEncryptUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_SignFinal(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_SignInit(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	public static native long C_SignRecover(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_SignRecoverInit(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	public static native long C_SignUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	public static native long C_UnwrapKey(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Attribute > CK_ATTRIBUTE_PTR1, long CK_ULONG2, Pointer<Long > CK_OBJECT_HANDLE_PTR1);
	public static native long C_Verify(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, long CK_ULONG2);
	public static native long C_VerifyFinal(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	public static native long C_VerifyInit(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	public static native long C_VerifyRecover(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1, Pointer<Byte > CK_BYTE_PTR2, Pointer<Long > CK_ULONG_PTR1);
	public static native long C_VerifyRecoverInit(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1);
	public static native long C_VerifyUpdate(long CK_SESSION_HANDLE1, Pointer<Byte > CK_BYTE_PTR1, long CK_ULONG1);
	public static native long C_WaitForSlotEvent(long CK_FLAGS1, Pointer<Long > CK_SLOT_ID_PTR1, Pointer<? > CK_VOID_PTR1);
	public static native long C_WrapKey(long CK_SESSION_HANDLE1, Pointer<Mechanism > CK_MECHANISM_PTR1, long CK_OBJECT_HANDLE1, long CK_OBJECT_HANDLE2, Pointer<Byte > CK_BYTE_PTR1, Pointer<Long > CK_ULONG_PTR1);
}