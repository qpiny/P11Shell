package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

@Library("xltCk") 
public abstract class Mechanism extends StructObject {
	public Mechanism() {
		super();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Mechanism(Pointer pointer) {
		super(pointer);
	}

	@Field(0) 
	public int mechanism() {
		return this.io.getIntField(this, 0);
	}
	
	public abstract Mechanism mechanism(int mechanism); 
	public abstract Pointer<?> parameter();
	public abstract Mechanism parameter(Pointer<?> parameter);
	public abstract int parameterLen();
	public abstract Mechanism parameterLen(int parameterLen);
}
