package org.rejna.pkcs11.x64;

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

	@Field(0) 
	public SessionInfo slotID(int slotID) {
		this.io.setIntField(this, 0, slotID);
		return this;
	}

	@Field(1) 
	public int state() {
		return this.io.getIntField(this, 1);
	}
	
	@Field(1) 
	public SessionInfo state(int state) {
		this.io.setIntField(this, 1, state);
		return this;
	}
	
	@Field(2) 
	public int flags() {
		return this.io.getIntField(this, 2);
	}
	
	@Field(2) 
	public SessionInfo flags(int flags) {
		this.io.setIntField(this, 2, flags);
		return this;
	}
	
	@Field(3) 
	public int deviceError() {
		return this.io.getIntField(this, 3);
	}
	/**
	 * ulDeviceError was changed from CK_USHORT to CK_ULONG for<br>
	 * v2.0<br>
	 * device-dependent error code<br>
	 * C type : CK_ULONG
	 */
	@Field(3) 
	public SessionInfo deviceError(int ulDeviceError) {
		this.io.setIntField(this, 3, ulDeviceError);
		return this;
	}
}
