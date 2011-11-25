package org.rejna.pkcs11.x32;

import org.bridj.Pointer;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

@Library("xltCk") 
public class Mechanism extends org.rejna.pkcs11.Mechanism {
	public Mechanism() {
		super();
	}
	@SuppressWarnings("rawtypes")
	public Mechanism(Pointer pointer) {
		super(pointer);
	}

	@Field(0) 
	public int mechanism() {
		return this.io.getIntField(this, 0);
	}
	
	@Field(0) 
	public Mechanism mechanism(int mechanism) {
		this.io.setIntField(this, 0, mechanism);
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
	public int parameterLen() {
		return this.io.getIntField(this, 2);
	}

	@Field(2) 
	public Mechanism parameterLen(int parameterLen) {
		this.io.setIntField(this, 2, parameterLen);
		return this;
	}
}
