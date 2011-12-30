package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Struct;

@Library("xltCk") 
@Struct(pack=1)
public class Attribute extends StructObject {

	public Attribute() {
		super();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Attribute(Pointer pointer) {
		super(pointer);
	}
	
	protected Attribute(AttributeType type, Pointer<?> value, long size) {
		super();
		attributeType(type);
		value(value);
		size(size);
	}	

	public AttributeType attributeType() {
		return AttributeType.valueOf((int) type());
	}

	public Attribute attributeType(AttributeType type) {
		type(type.getValue());
		return this;
	}

	public Object object() throws InvalidAttributeException {
		return attributeType().toObject(this);
	}
	
	@Override
	public String toString() {
		try {
			AttributeType type = attributeType();
			return type + "=" + type.toString(this) + "(" + size() + ")";
		} catch (InvalidAttributeException e) {
			return "ERROR: " + e.getMessage();
		}
	}

	public void copyTo(Attribute attribute) {
		attribute.type(type());
		attribute.value(value());
		attribute.size(size());		
	}

	@Field(0) 
	@CLong
	public long type() {
		return this.io.getCLongField(this, 0);
	}

	@Field(0) 
	public Attribute type(long type) {
		this.io.setCLongField(this, 0, type);
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
	@CLong
	public long size() {
		return this.io.getCLongField(this, 2);
	}
	
	@Field(2) 
	public Attribute size(long size) {
		this.io.setCLongField(this, 2, size);
		return this;
	}
}
