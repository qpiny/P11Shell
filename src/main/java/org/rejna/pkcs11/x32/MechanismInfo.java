package org.rejna.pkcs11.x32;
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
	public int minKeySize() {
		return this.io.getIntField(this, 0);
	}
	
	@Field(1) 
	public int maxKeySize() {
		return this.io.getIntField(this, 1);
	}
	
	@Field(2) 
	public int flags() {
		return this.io.getIntField(this, 2);
	}
}
