package org.rejna.pkcs11;

public class P11Exception extends Exception {
	private static final long serialVersionUID = -8168968308841240635L;
	private int code;
	private String message;

	public P11Exception(int code) {
		this.code = code;
		switch(code) {
		case 0x00000000: message = "CKR_OK"; break;
		case 0x00000001: message = "CKR_CANCEL"; break;
		case 0x00000002: message = "CKR_HOST_MEMORY"; break;
		case 0x00000003: message = "CKR_SLOT_ID_INVALID"; break;
		case 0x00000005: message = "CKR_GENERAL_ERROR"; break;
		case 0x00000006: message = "CKR_FUNCTION_FAILED"; break;
		case 0x00000007: message = "CKR_ARGUMENTS_BAD"; break;
		case 0x00000008: message = "CKR_NO_EVENT"; break;
		case 0x00000009: message = "CKR_NEED_TO_CREATE_THREADS"; break;
		case 0x0000000A: message = "CKR_CANT_LOCK"; break;
		case 0x00000010: message = "CKR_ATTRIBUTE_READ_ONLY"; break;
		case 0x00000011: message = "CKR_ATTRIBUTE_SENSITIVE"; break;
		case 0x00000012: message = "CKR_ATTRIBUTE_TYPE_INVALID"; break;
		case 0x00000013: message = "CKR_ATTRIBUTE_VALUE_INVALID"; break;
		case 0x00000020: message = "CKR_DATA_INVALID"; break;
		case 0x00000021: message = "CKR_DATA_LEN_RANGE"; break;
		case 0x00000030: message = "CKR_DEVICE_ERROR"; break;
		case 0x00000031: message = "CKR_DEVICE_MEMORY"; break;
		case 0x00000032: message = "CKR_DEVICE_REMOVED"; break;
		case 0x00000040: message = "CKR_ENCRYPTED_DATA_INVALID"; break;
		case 0x00000041: message = "CKR_ENCRYPTED_DATA_LEN_RANGE"; break;
		case 0x00000050: message = "CKR_FUNCTION_CANCELED"; break;
		case 0x00000051: message = "CKR_FUNCTION_NOT_PARALLEL"; break;
		case 0x00000054: message = "CKR_FUNCTION_NOT_SUPPORTED"; break;
		case 0x00000060: message = "CKR_KEY_HANDLE_INVALID"; break;
		case 0x00000062: message = "CKR_KEY_SIZE_RANGE"; break;
		case 0x00000063: message = "CKR_KEY_TYPE_INCONSISTENT"; break;
		case 0x00000064: message = "CKR_KEY_NOT_NEEDED"; break;
		case 0x00000065: message = "CKR_KEY_CHANGED"; break;
		case 0x00000066: message = "CKR_KEY_NEEDED"; break;
		case 0x00000067: message = "CKR_KEY_INDIGESTIBLE"; break;
		case 0x00000068: message = "CKR_KEY_FUNCTION_NOT_PERMITTED"; break;
		case 0x00000069: message = "CKR_KEY_NOT_WRAPPABLE"; break;
		case 0x0000006A: message = "CKR_KEY_UNEXTRACTABLE"; break;
		case 0x00000070: message = "CKR_MECHANISM_INVALID"; break;
		case 0x00000071: message = "CKR_MECHANISM_PARAM_INVALID"; break;
		case 0x00000082: message = "CKR_OBJECT_HANDLE_INVALID"; break;
		case 0x00000090: message = "CKR_OPERATION_ACTIVE"; break;
		case 0x00000091: message = "CKR_OPERATION_NOT_INITIALIZED"; break;
		case 0x000000A0: message = "CKR_PIN_INCORRECT"; break;
		case 0x000000A1: message = "CKR_PIN_INVALID"; break;
		case 0x000000A2: message = "CKR_PIN_LEN_RANGE"; break;
		case 0x000000A3: message = "CKR_PIN_EXPIRED"; break;
		case 0x000000A4: message = "CKR_PIN_LOCKED"; break;
		case 0x000000B0: message = "CKR_SESSION_CLOSED"; break;
		case 0x000000B1: message = "CKR_SESSION_COUNT"; break;
		case 0x000000B3: message = "CKR_SESSION_HANDLE_INVALID"; break;
		case 0x000000B4: message = "CKR_SESSION_PARALLEL_NOT_SUPPORTED"; break;
		case 0x000000B5: message = "CKR_SESSION_READ_ONLY"; break;
		case 0x000000B6: message = "CKR_SESSION_EXISTS"; break;
		case 0x000000B7: message = "CKR_SESSION_READ_ONLY_EXISTS"; break;
		case 0x000000B8: message = "CKR_SESSION_READ_WRITE_SO_EXISTS"; break;
		case 0x000000C0: message = "CKR_SIGNATURE_INVALID"; break;
		case 0x000000C1: message = "CKR_SIGNATURE_LEN_RANGE"; break;
		case 0x000000D0: message = "CKR_TEMPLATE_INCOMPLETE"; break;
		case 0x000000D1: message = "CKR_TEMPLATE_INCONSISTENT"; break;
		case 0x000000E0: message = "CKR_TOKEN_NOT_PRESENT"; break;
		case 0x000000E1: message = "CKR_TOKEN_NOT_RECOGNIZED"; break;
		case 0x000000E2: message = "CKR_TOKEN_WRITE_PROTECTED"; break;
		case 0x000000F0: message = "CKR_UNWRAPPING_KEY_HANDLE_INVALID"; break;
		case 0x000000F1: message = "CKR_UNWRAPPING_KEY_SIZE_RANGE"; break;
		case 0x000000F2: message = "CKR_UNWRAPPING_KEY_TYPE_INCONSISTENT"; break;
		case 0x00000100: message = "CKR_USER_ALREADY_LOGGED_IN"; break;
		case 0x00000101: message = "CKR_USER_NOT_LOGGED_IN"; break;
		case 0x00000102: message = "CKR_USER_PIN_NOT_INITIALIZED"; break;
		case 0x00000103: message = "CKR_USER_TYPE_INVALID"; break;
		case 0x00000104: message = "CKR_USER_ANOTHER_ALREADY_LOGGED_IN"; break;
		case 0x00000105: message = "CKR_USER_TOO_MANY_TYPES"; break;
		case 0x00000110: message = "CKR_WRAPPED_KEY_INVALID"; break;
		case 0x00000112: message = "CKR_WRAPPED_KEY_LEN_RANGE"; break;
		case 0x00000113: message = "CKR_WRAPPING_KEY_HANDLE_INVALID"; break;
		case 0x00000114: message = "CKR_WRAPPING_KEY_SIZE_RANGE"; break;
		case 0x00000115: message = "CKR_WRAPPING_KEY_TYPE_INCONSISTENT"; break;
		case 0x00000120: message = "CKR_RANDOM_SEED_NOT_SUPPORTED"; break;
		case 0x00000121: message = "CKR_RANDOM_NO_RNG"; break;
		case 0x00000130: message = "CKR_DOMAIN_PARAMS_INVALID"; break;
		case 0x00000150: message = "CKR_BUFFER_TOO_SMALL"; break;
		case 0x00000160: message = "CKR_SAVED_STATE_INVALID"; break;
		case 0x00000170: message = "CKR_INFORMATION_SENSITIVE"; break;
		case 0x00000180: message = "CKR_STATE_UNSAVEABLE"; break;
		case 0x00000190: message = "CKR_CRYPTOKI_NOT_INITIALIZED"; break;
		case 0x00000191: message = "CKR_CRYPTOKI_ALREADY_INITIALIZED"; break;
		case 0x000001A0: message = "CKR_MUTEX_BAD"; break;
		case 0x000001A1: message = "CKR_MUTEX_NOT_LOCKED"; break;
		case 0x80000000: message = "CKR_VENDOR_DEFINED"; break;
		}
	}
	
	public int getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		String s = getClass().getName();
        return (message != null) ? (s + ": " + message) : s;
	}
}