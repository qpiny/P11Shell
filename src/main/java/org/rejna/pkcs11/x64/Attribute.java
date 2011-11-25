package org.rejna.pkcs11.x64;

import org.bridj.Pointer;
import org.bridj.ann.Field;
import org.rejna.pkcs11.AttributeType;

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
	public long type64() {
		return this.io.getLongField(this, 0);
	}
	
	public int type() {
		return (int) type64();
	}

	@Field(0) 
	public Attribute type64(long type) {
		this.io.setLongField(this, 0, type);
		return this;
	}
	
	public Attribute type(int type) {
		return type64(type);
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
	public long size64() {
		return this.io.getLongField(this, 2);
	}
	
	public int size() {
		return (int) size64();
	}

	@Field(2) 
	public Attribute size64(long size) {
		this.io.setLongField(this, 2, size);
		return this;
	}
	
	public Attribute size(int size) {
		return size64(size);
	}
}
