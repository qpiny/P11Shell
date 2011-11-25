package org.rejna.pkcs11.x32;

import org.bridj.Pointer;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Struct;
import org.rejna.pkcs11.AttributeType;

@Library("xltCk") 
@Struct(pack=1)
public class Attribute extends org.rejna.pkcs11.Attribute {

	public Attribute() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public Attribute(Pointer pointer) {
		super(pointer);
	}

	public Attribute(AttributeType type, Object value) {
		super();
		type.fillTemplate(this, value);
	}

	@Field(0) 
	public int type() {
		return this.io.getIntField(this, 0);
	}

	@Field(0) 
	public Attribute type(int type) {
		this.io.setIntField(this, 0, type);
		return this;
	}

	@Field(1) 
	public Pointer<?> value() {
		return this.io.getPointerField(this, 1);
	}

	@Field(1) 
	public Attribute value(Pointer<?> value) {
		this.io.setPointerField(this, 1, value);
		return this;
	}

	@Field(2) 
	public int size() {
		return this.io.getIntField(this, 2);
	}
	
	@Field(2) 
	public Attribute size(int size) {
		this.io.setIntField(this, 2, size);
		return this;
	}
}
