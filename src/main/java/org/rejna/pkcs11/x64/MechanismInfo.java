package org.rejna.pkcs11.x64;
import org.bridj.Pointer;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

@Library("xltCk") 
public class MechanismInfo extends org.rejna.pkcs11.MechanismInfo {
	public MechanismInfo() {
		super();
	}
	@SuppressWarnings("rawtypes")
	public MechanismInfo(Pointer pointer) {
		super(pointer);
	}

	@Field(0) 
	public long minKeySize64() {
		return this.io.getLongField(this, 0);
	}
	
	public int minKeySize() {
		return (int) minKeySize64();
	}
	
	@Field(1) 
	public long maxKeySize64() {
		return this.io.getLongField(this, 1);
	}
	
	public int maxKeySize() {
		return (int) maxKeySize64();
	}
	
	@Field(2) 
	public long flags64() {
		return this.io.getLongField(this, 2);
	}
	
	public int flags() {
		return (int) flags64();
	}
}
