package org.rejna.pkcs11.x64;

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
	public long mechanism64() {
		return this.io.getLongField(this, 0);
	}
	
	public int mechanism() {
		return (int) mechanism64();
	}
	
	@Field(0) 
	public Mechanism mechanism64(long mechanism) {
		this.io.setLongField(this, 0, mechanism);
		return this;
	}
	
	public Mechanism mechanism(int mechanism) {
		return mechanism64(mechanism);
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
	public long parameterLen64() {
		return this.io.getLongField(this, 2);
	}
	
	public int parameterLen() {
		return (int) parameterLen();
	}

	@Field(2) 
	public Mechanism parameterLen64(long parameterLen) {
		this.io.setLongField(this, 2, parameterLen);
		return this;
	}
	
	public Mechanism parameterLen(int parameterLen) {
		return parameterLen64(parameterLen);
	}
}
