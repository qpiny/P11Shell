package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
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
	@CLong
	public long slotID() {
		return this.io.getCLongField(this, 0);
	}

	@Field(1) 
	@CLong
	public long state() {
		return this.io.getCLongField(this, 1);
	}
	
	@Field(2)
	@CLong
	public long flags() {
		return this.io.getCLongField(this, 2);
	}
	
	@Field(3) 
	@CLong
	public long deviceError() {
		return this.io.getCLongField(this, 3);
	}
}
