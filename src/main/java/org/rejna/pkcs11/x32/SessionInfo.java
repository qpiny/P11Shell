package org.rejna.pkcs11.x32;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

@Library("xltCk") 
public class SessionInfo extends StructObject {
	public SessionInfo() {
		super();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SessionInfo(Pointer pointer) {
		super(pointer);
	}
	
	@Field(0) 
	public int slotID() {
		return this.io.getIntField(this, 0);
	}

	@Field(1) 
	public int state() {
		return this.io.getIntField(this, 1);
	}
	
	@Field(2) 
	public int flags() {
		return this.io.getIntField(this, 2);
	}
	
	@Field(3) 
	public int deviceError() {
		return this.io.getIntField(this, 3);
	}
}
