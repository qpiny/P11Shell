package org.rejna.pkcs11.x64;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.rejna.pkcs11.Version;

@Library("xltCk") 
public class TokenInfo extends org.rejna.pkcs11.TokenInfo {
	public TokenInfo() {
		super();
	}
	@SuppressWarnings("rawtypes")
	public TokenInfo(Pointer pointer) {
		super(pointer);
	}
	
	@Array({32}) 
	@Field(0) 
	public Pointer<Byte> label() {
		return this.io.getPointerField(this, 0);
	}
	
	@Array({32}) 
	@Field(1) 
	public Pointer<Byte> manufacturerID() {
		return this.io.getPointerField(this, 1);
	}
	
	@Array({16}) 
	@Field(2) 
	public Pointer<Byte> model() {
		return this.io.getPointerField(this, 2);
	}

	@Array({16}) 
	@Field(3) 
	public Pointer<Byte> serialNumber() {
		return this.io.getPointerField(this, 3);
	}

	@Field(4) 
	public long flags64() {
		return this.io.getLongField(this, 4);
	}
	
	public int flags() {
		return (int) flags64();
	}
	 
	@Field(5) 
	public long maxSessionCount64() {
		return this.io.getLongField(this, 5);
	}
	
	public int maxSessionCount() {
		return (int) maxSessionCount64();
	}

	@Field(6) 
	public long sessionCount64() {
		return this.io.getLongField(this, 6);
	}
	
	public int sessionCount() {
		return (int) sessionCount64();
	}

	@Field(7) 
	public long maxRwSessionCount64() {
		return this.io.getLongField(this, 7);
	}
	
	public int maxRwSessionCount() {
		return (int) maxRwSessionCount64();
	}

	@Field(8) 
	public long rwSessionCount64() {
		return this.io.getLongField(this, 8);
	}

	public int rwSessionCount() {
		return (int) rwSessionCount64();
	}
	
	@Field(9) 
	public long maxPinLen64() {
		return this.io.getLongField(this, 9);
	}

	public int maxPinLen() {
		return (int) maxPinLen64();
	}
	
	@Field(10) 
	public long minPinLen64() {
		return this.io.getLongField(this, 10);
	}

	public int minPinLen() {
		return (int) minPinLen64();
	}
	
	@Field(11) 
	public long totalPublicMemory64() {
		return this.io.getLongField(this, 11);
	}
	
	public int totalPublicMemory() {
		return (int) totalPublicMemory64();
	}

	@Field(12) 
	public long freePublicMemory64() {
		return this.io.getLongField(this, 12);
	}

	public int freePublicMemory() {
		return (int) freePublicMemory64();
	}
	
	@Field(13) 
	public long totalPrivateMemory64() {
		return this.io.getLongField(this, 13);
	}
	
	public int totalPrivateMemory() {
		return (int) totalPrivateMemory64();
	}

	@Field(14) 
	public long freePrivateMemory64() {
		return this.io.getLongField(this, 14);
	}
	
	public int freePrivateMemory() {
		return (int) freePrivateMemory64();
	}

	@Field(15) 
	public Version hardwareVersion() {
		return this.io.getNativeObjectField(this, 15);
	}
	
	@Field(16) 
	public Version firmwareVersion() {
		return this.io.getNativeObjectField(this, 16);
	}

	@Array({16}) 
	@Field(17) 
	public Pointer<Byte> utcTime() {
		return this.io.getPointerField(this, 17);
	}
}
