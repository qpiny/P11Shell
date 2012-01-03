package org.rejna.pkcs11;

public class Flags {
	private String message;
	
	public Flags(long flags) {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(flags).append(") ");
		if ((flags & 0x00000001) != 0) sb.append("CKF_TOKEN_PRESENT ");
		if ((flags & 0x00000002) != 0) sb.append("CKF_REMOVABLE_DEVICE ");
		if ((flags & 0x00000004) != 0) sb.append("CKF_HW_SLOT ");
		if ((flags & 0x00000001) != 0) sb.append("CKF_RNG ");
		if ((flags & 0x00000002) != 0) sb.append("CKF_WRITE_PROTECTED ");
		if ((flags & 0x00000004) != 0) sb.append("CKF_LOGIN_REQUIRED ");
		if ((flags & 0x00000008) != 0) sb.append("CKF_USER_PIN_INITIALIZED ");
		if ((flags & 0x00000020) != 0) sb.append("CKF_RESTORE_KEY_NOT_NEEDED ");
		if ((flags & 0x00000040) != 0) sb.append("CKF_CLOCK_ON_TOKEN ");
		if ((flags & 0x00000100) != 0) sb.append("CKF_PROTECTED_AUTHENTICATION_PATH ");
		if ((flags & 0x00000200) != 0) sb.append("CKF_DUAL_CRYPTO_OPERATIONS ");
		if ((flags & 0x00000400) != 0) sb.append("CKF_TOKEN_INITIALIZED ");
		if ((flags & 0x00000800) != 0) sb.append("CKF_SECONDARY_AUTHENTICATION ");
		if ((flags & 0x00010000) != 0) sb.append("CKF_USER_PIN_COUNT_LOW ");
		if ((flags & 0x00020000) != 0) sb.append("CKF_USER_PIN_FINAL_TRY ");
		if ((flags & 0x00040000) != 0) sb.append("CKF_USER_PIN_LOCKED ");
		if ((flags & 0x00080000) != 0) sb.append("CKF_USER_PIN_TO_BE_CHANGED ");
		if ((flags & 0x00100000) != 0) sb.append("CKF_SO_PIN_COUNT_LOW ");
		if ((flags & 0x00200000) != 0) sb.append("CKF_SO_PIN_FINAL_TRY ");
		if ((flags & 0x00400000) != 0) sb.append("CKF_SO_PIN_LOCKED ");
		if ((flags & 0x00800000) != 0) sb.append("CKF_SO_PIN_TO_BE_CHANGED ");
		if ((flags & 0x00010000) != 0) sb.append("CKF_USER_PIN_COUNT_LOW ");
		if ((flags & 0x00020000) != 0) sb.append("CKF_USER_PIN_FINAL_TRY ");
		if ((flags & 0x00040000) != 0) sb.append("CKF_USER_PIN_LOCKED ");
		if ((flags & 0x00080000) != 0) sb.append("CKF_USER_PIN_MANUFACT_VALUE ");
		if ((flags & 0x00100000) != 0) sb.append("CKF_SO_PIN_COUNT_LOW ");
		if ((flags & 0x00200000) != 0) sb.append("CKF_SO_PIN_FINAL_TRY ");
		if ((flags & 0x00400000) != 0) sb.append("CKF_SO_PIN_LOCKED ");
		if ((flags & 0x00800000) != 0) sb.append("CKF_SO_PIN_MANUFACT_VALUE ");
		if ((flags & 0x01000000) != 0) sb.append("CKF_SO_PIN_DERIVED ");
		if ((flags & 0x02000000) != 0) sb.append("CKF_SO_CARD ");
		if ((flags & 0x00000002) != 0) sb.append("CKF_RW_SESSION ");
		if ((flags & 0x00000004) != 0) sb.append("CKF_SERIAL_SESSION ");
		if ((flags & 0x00000001) != 0) sb.append("CKF_HW ");
		if ((flags & 0x00000100) != 0) sb.append("CKF_ENCRYPT ");
		if ((flags & 0x00000200) != 0) sb.append("CKF_DECRYPT ");
		if ((flags & 0x00000400) != 0) sb.append("CKF_DIGEST ");
		if ((flags & 0x00000800) != 0) sb.append("CKF_SIGN ");
		if ((flags & 0x00001000) != 0) sb.append("CKF_SIGN_RECOVER ");
		if ((flags & 0x00002000) != 0) sb.append("CKF_VERIFY ");
		if ((flags & 0x00004000) != 0) sb.append("CKF_VERIFY_RECOVER ");
		if ((flags & 0x00008000) != 0) sb.append("CKF_GENERATE ");
		if ((flags & 0x00010000) != 0) sb.append("CKF_GENERATE_KEY_PAIR ");
		if ((flags & 0x00020000) != 0) sb.append("CKF_WRAP ");
		if ((flags & 0x00040000) != 0) sb.append("CKF_UNWRAP ");
		if ((flags & 0x00080000) != 0) sb.append("CKF_DERIVE ");
		if ((flags & 0x00100000) != 0) sb.append("CKF_EC_F_P ");
		if ((flags & 0x00200000) != 0) sb.append("CKF_EC_F_2M ");
		if ((flags & 0x00400000) != 0) sb.append("CKF_EC_ECPARAMETERS ");
		if ((flags & 0x00800000) != 0) sb.append("CKF_EC_NAMEDCURVE ");
		if ((flags & 0x01000000) != 0) sb.append("CKF_EC_UNCOMPRESS ");
		if ((flags & 0x02000000) != 0) sb.append("CKF_EC_COMPRESS ");
		if ((flags & 0x80000000) != 0) sb.append("CKF_EXTENSION ");
		if ((flags & 0x00000001) != 0) sb.append("CKF_LIBRARY_CANT_CREATE_OS_THREADS ");
		if ((flags & 0x00000002) != 0) sb.append("CKF_OS_LOCKING_OK ");
		if ((flags & 1) != 0) sb.append("CKF_DONT_BLOCK ");
		message = sb.toString();
	}
	
	@Override
	public String toString() {
		return message;
	}
}