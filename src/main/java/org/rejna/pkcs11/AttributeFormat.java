package org.rejna.pkcs11;

import java.nio.charset.Charset;
import java.util.EnumSet;

import org.bridj.Pointer;
import org.bridj.Pointer.StringType;
import org.apache.commons.codec.binary.Hex;

public abstract class AttributeFormat {
	public final static AttributeFormat STRING = new StringFormat();
	public final static AttributeFormat INTEGER = new IntegerFormat();
	public final static AttributeFormat BOOLEAN = new BooleanFormat();
	public final static AttributeFormat BINARY = new BinaryFormat();
	public <T extends Enum<T>> AttributeFormat ENUM(Class<T> c) { return new EnumFormat<T>(c); };
	public abstract int getDefaultSize();
	public abstract Object toObject(Pointer<?> value, int size) throws InvalidAttributeException;
	public abstract String toString(Pointer<?> value, int size) throws InvalidAttributeException;
	public abstract Attribute setAttribute(Attribute attribute, Object value);
	public abstract Attribute createAttribute(AttributeType type);
	public abstract Attribute createAttribute(AttributeType type, int size);
}

class StringFormat extends AttributeFormat {

	@Override
	public int getDefaultSize() {
		return 30;
	}

	@Override
	public Object toObject(Pointer<?> value, int size) {
		return toString(value, size);
	}

	@Override
	public String toString(Pointer<?> value, int size) {
		return new String(value.getBytes(size));
	}
	
	@Override
	public Attribute setAttribute(Attribute attribute, Object value) {
		String s = (String) value;
		attribute.value(Pointer.pointerToString(s, StringType.C, Charset.defaultCharset()));
		attribute.size(s.length());
		return attribute;
	}

	@Override
	public Attribute createAttribute(AttributeType type) {
		return createAttribute(type, getDefaultSize());		
	}

	@Override
	public Attribute createAttribute(AttributeType type, int size) {
		return PKCS11.getInstance().createAttribute(type, new String(new char[size]));
	}
}

class IntegerFormat extends AttributeFormat {

	@Override
	public int getDefaultSize() {
		return 4;
	}

	@Override
	public Object toObject(Pointer<?> value, int size) throws InvalidAttributeException {
		switch(size) {
		case 1: return value.getByte();
		case 2: return value.getShort();
		case 4: return value.getInt();
		case 8: return value.getLong();
		} 
		throw new InvalidAttributeException("Invalid size for int : " + size);
	}

	@Override
	public String toString(Pointer<?> value, int size) throws InvalidAttributeException {
		return toObject(value, size).toString();
	}

	@Override
	public Attribute setAttribute(Attribute attribute, Object value) {
		if (value instanceof Long) {
			attribute.value(Pointer.pointerToLong(((Long)value).longValue()));
			attribute.size(8);				
		}
		else if (value instanceof Integer) {
			attribute.value(Pointer.pointerToInt(((Integer)value).intValue()));
			attribute.size(4);
		}
		else if (value instanceof Short) {
			attribute.value(Pointer.pointerToShort(((Short)value).shortValue()));
			attribute.size(2);				
		}
		else if (value instanceof Long) {
			attribute.value(Pointer.pointerToByte(((Byte)value).byteValue()));
			attribute.size(1);				
		}
		return attribute;
	}

	@Override
	public Attribute createAttribute(AttributeType type) {
		return PKCS11.getInstance().createAttribute(type, Integer.valueOf(0));
	}

	@Override
	public Attribute createAttribute(AttributeType type, int size) {
		return PKCS11.getInstance().createAttribute(type, Pointer.allocateBytes(size));
	}
}

class BooleanFormat extends AttributeFormat {

	@Override
	public int getDefaultSize() {
		return 1;
	}

	@Override
	public Object toObject(Pointer<?> value, int size) {
		if (value == null)
			return null;
		return value.getBoolean();
	}

	@Override
	public String toString(Pointer<?> value, int size) {
		if (value == null)
			return "<not set>";
		return toObject(value, size).toString();
	}

	@Override
	public Attribute setAttribute(Attribute attribute, Object value) {
		attribute.value(Pointer.pointerToBoolean((Boolean)value));
		attribute.size(1);
		return attribute;
	}

	@Override
	public Attribute createAttribute(AttributeType type) {
		return PKCS11.getInstance().createAttribute(type, Boolean.FALSE);
	}

	@Override
	public Attribute createAttribute(AttributeType type, int size) {
		return PKCS11.getInstance().createAttribute(type, Pointer.allocateBytes(size));
	}
}

class BinaryFormat extends AttributeFormat {

	@Override
	public int getDefaultSize() {
		return 80;
	}

	@Override
	public Object toObject(Pointer<?> value, int size) {
		return value.getBytes(size);
	}

	@Override
	public String toString(Pointer<?> value, int size) {
		return Hex.encodeHexString(value.getBytes(size));
	}

	@Override
	public Attribute setAttribute(Attribute attribute, Object value) {
		byte[] b = (byte[])value;
		attribute.value(Pointer.pointerToBytes(b));
		attribute.size(b.length);
		return attribute;
	}

	@Override
	public Attribute createAttribute(AttributeType type) {
		return createAttribute(type, getDefaultSize());
	}

	@Override
	public Attribute createAttribute(AttributeType type, int size) {
		return PKCS11.getInstance().createAttribute(type, new byte[size]);
	}
}

class EnumFormat<T extends Enum<T>> extends IntegerFormat {
	private Class<T> c;
	
	public EnumFormat(Class<T> c) {
		this.c = c;
	}
/*
	@Override
	public Object toObject(Pointer<?> value, int size)
			throws InvalidAttributeException {
		// TODO Auto-generated method stub
		return null;
	}
*/
	@Override
	public String toString(Pointer<?> value, int size)
			throws InvalidAttributeException {
		for (T e : EnumSet.allOf(c)) {
			P11Enum pe = (P11Enum) e; 
			if (pe.getValue() == ((Number)toObject(value, size)).intValue())
				return pe.name(); 
		}
		return "";
	}

	@Override
	public Attribute setAttribute(Attribute attribute, Object value) {
		if (value instanceof String) {
			String s = (String) value;
			for (T e : EnumSet.allOf(c)) {
				P11Enum pe = (P11Enum) e; 
				if (pe.name().equals(s)) {
					super.setAttribute(attribute, pe.getValue());
					break;
				}
			}
		}
		return attribute;
	}
	
}