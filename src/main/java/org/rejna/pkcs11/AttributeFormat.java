package org.rejna.pkcs11;

import java.nio.charset.Charset;

import org.bridj.Pointer;
import org.bridj.Pointer.StringType;
import org.apache.commons.codec.binary.Base64;

public abstract class AttributeFormat {
	public final static AttributeFormat STRING = new StringFormat();
	public final static AttributeFormat INTEGER = new IntegerFormat();
	public final static AttributeFormat BOOLEAN = new BooleanFormat();
	public final static AttributeFormat BINARY = new BinaryFormat();
	public <T extends P11Enum> AttributeFormat ENUM(P11EnumWrapper<T> e) { return new EnumFormat<T>(e); };
	public abstract int getDefaultSize();
	public abstract Object toObject(Pointer<?> value, int size) throws InvalidAttributeException;
	public abstract String toString(Pointer<?> value, int size) throws InvalidAttributeException;
	public abstract void setAttribute(Attribute attribute, Object value);
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
	public void setAttribute(Attribute attribute, Object value) {
		String s = (String) value;
		attribute.value(Pointer.pointerToString(s, StringType.C, Charset.defaultCharset()));
		attribute.size(s.length());
	}

	@Override
	public Attribute createAttribute(AttributeType type) {
		return PKCS11.getInstance().createAttribute(type, Pointer.allocateBytes(getDefaultSize()));		
	}

	@Override
	public Attribute createAttribute(AttributeType type, int size) {
		return PKCS11.getInstance().createAttribute(type, Pointer.allocateBytes(size));
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
	public void setAttribute(Attribute attribute, Object value) {
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
	public void setAttribute(Attribute attribute, Object value) {
		attribute.value(Pointer.pointerToBoolean((Boolean)value));
		attribute.size(1);
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
		return Base64.encodeBase64String(value.getBytes(size));
	}

	@Override
	public void setAttribute(Attribute attribute, Object value) {
		byte[] b = (byte[])value;
		attribute.value(Pointer.pointerToBytes(b));
		attribute.size(b.length);
	}

	@Override
	public Attribute createAttribute(AttributeType type) {
		return createAttribute(type, getDefaultSize());
	}

	@Override
	public Attribute createAttribute(AttributeType type, int size) {
		return PKCS11.getInstance().createAttribute(type, Pointer.allocateBytes(size));
	}
}

class EnumFormat<T extends P11Enum> extends IntegerFormat {
	private P11EnumWrapper<T> e;
	
	public EnumFormat(P11EnumWrapper<T> e) {
		this.e = e;
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
		return e.valueOf(((Number)toObject(value, size)).intValue()).name();
	}

	@Override
	public void setAttribute(Attribute attribute, Object value) {
		if (value instanceof String) {
			String s = (String) value;
			super.setAttribute(attribute, Integer.valueOf(e.valueOf(s).getValue()));
		}
	}
	
}