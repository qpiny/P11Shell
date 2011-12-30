package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

@Library("xltCk") 
public class Mechanism extends StructObject {
	public Mechanism() {
		super();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Mechanism(Pointer pointer) {
		super(pointer);
	}
	
	public Mechanism(MechanismType type) {
		mechanism(type.getValue());
		parameter(null);
		parameterLen(0);
	}
	
	public Mechanism(MechanismType type, long parameter) {
		mechanism(type.getValue());
		parameter(Pointer.pointerToCLong(parameter));
		parameterLen(org.bridj.CLong.SIZE);
	}
	
	@Field(0)
	@CLong
	public long mechanism() {
		return this.io.getCLongField(this, 0);
	}
	
	@Field(0)
	@CLong
	public Mechanism mechanism(long mechanism) {
		this.io.setCLongField(this, 0, mechanism);
		return this;
	}
	
	@Field(1) 
	public Pointer<?> parameter() {
		return this.io.getPointerField(this, 1);
	}
	
	@Field(1) 
	public Mechanism parameter(Pointer<?> parameter) {
		this.io.setPointerField(this, 1, parameter);
		return this;
	}

	@Field(2) 
	@CLong
	public int parameterLen() {
		return (int) this.io.getCLongField(this, 2);
	}

	@Field(2) 
	@CLong
	public Mechanism parameterLen(long parameterLen) {
		this.io.setCLongField(this, 2, parameterLen);
		return this;
	}
}
