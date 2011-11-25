package org.rejna.pkcs11.x32;
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
	public int flags() {
		return this.io.getIntField(this, 4);
	}
	 
	@Field(5) 
	public int maxSessionCount() {
		return this.io.getIntField(this, 5);
	}

	@Field(6) 
	public int sessionCount() {
		return this.io.getIntField(this, 6);
	}

	@Field(7) 
	public int maxRwSessionCount() {
		return this.io.getIntField(this, 7);
	}

	@Field(8) 
	public int rwSessionCount() {
		return this.io.getIntField(this, 8);
	}

	@Field(9) 
	public int maxPinLen() {
		return this.io.getIntField(this, 9);
	}

	@Field(10) 
	public int minPinLen() {
		return this.io.getIntField(this, 10);
	}

	@Field(11) 
	public int totalPublicMemory() {
		return this.io.getIntField(this, 11);
	}

	@Field(12) 
	public int freePublicMemory() {
		return this.io.getIntField(this, 12);
	}

	@Field(13) 
	public int totalPrivateMemory() {
		return this.io.getIntField(this, 13);
	}

	@Field(14) 
	public int freePrivateMemory() {
		return this.io.getIntField(this, 14);
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
