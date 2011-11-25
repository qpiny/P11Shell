package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.bridj.StructObject;

public abstract class Attribute extends StructObject {

	abstract public int type();
	abstract public Attribute type(int type);
	abstract public Pointer<?> value();
	abstract public Attribute value(Pointer<?> value);
	abstract public int size();
	abstract public Attribute size(int size);

	public Attribute() {
		super();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Attribute(Pointer pointer) {
		super(pointer);
	}
	
	public Attribute(AttributeType type, Object value) {
		super();
		type.fillTemplate(this, value);
	}	

	public AttributeType attributeType() {
		return AttributeType.valueOf(type());
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
}
