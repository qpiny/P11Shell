package org.rejna.pkcs11;

import java.util.EnumSet;

import org.bridj.CLong;
import org.bridj.Pointer;
import org.javatuples.Pair;
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
	public abstract Pair<Pointer<?>,Long> fromObject(Object value);
	public abstract Pair<Pointer<?>, Long> fromString(String value);
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
	public Pair<Pointer<?>,Long> fromObject(Object value) {
		String s = value.toString();
		return new Pair<Pointer<?>, Long>(Pointer.pointerToCString(s), (long) s.length());
	}

	@Override
	public Pair<Pointer<?>, Long> fromString(String value) {
		return fromObject(value);
	}
}

class IntegerFormat extends AttributeFormat {

	@Override
	public int getDefaultSize() {
		return CLong.SIZE;
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
	public Pair<Pointer<?>,Long> fromObject(Object value) {
		if (value instanceof Long)
			return new Pair<Pointer<?>, Long>(Pointer.pointerToLong(((Long)value).longValue()), 8L);
		else if (value instanceof Integer)
			return new Pair<Pointer<?>, Long>(Pointer.pointerToInt(((Integer)value).intValue()), 4L);
		else if (value instanceof Short)
			return new Pair<Pointer<?>, Long>(Pointer.pointerToShort(((Short)value).shortValue()), 2L);
		else if (value instanceof Byte)
			return new Pair<Pointer<?>, Long>(Pointer.pointerToByte(((Short)value).byteValue()), 1L);
		else if (value instanceof CLong)
			return new Pair<Pointer<?>, Long>(Pointer.pointerToCLong(((Long)value).longValue()), (long) Long.SIZE);
		return null;
	}

	@Override
	public Pair<Pointer<?>, Long> fromString(String value) {
		return fromObject(new CLong(Long.parseLong(value)));
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
/*
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
*/
	@Override
	public Pair<Pointer<?>,Long> fromObject(Object value) {
		return new Pair<Pointer<?>, Long>(Pointer.pointerToBoolean((Boolean)value), 1L);
	}

	@Override
	public Pair<Pointer<?>, Long> fromString(String value) {
		return fromObject(Boolean.parseBoolean(value));
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
/*
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
*/
	@Override
	public Pair<Pointer<?>,Long> fromObject(Object value) {
		byte[] b = (byte[])value;
		return new Pair<Pointer<?>, Long>(Pointer.pointerToBytes(b), (long) b.length);
	}

	@Override
	public Pair<Pointer<?>, Long> fromString(String value) {
		// TODO Auto-generated method stub
		return null;
	}
}

class EnumFormat<T extends Enum<T>> extends IntegerFormat {
	private Class<T> c;
	
	public EnumFormat(Class<T> c) {
		this.c = c;
	}
	
	@Override
	public Pair<Pointer<?>,Long> fromObject(Object value) {
		P11Enum pe = (P11Enum) value;
		return super.fromObject(new CLong(pe.getValue()));
	}

	@Override
	public Pair<Pointer<?>, Long> fromString(String value) {
		for (T e : EnumSet.allOf(c))
			if (e.name().equals(value))
				return super.fromObject(new CLong(((P11Enum) e).getValue()));
		return null;
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
/*
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
	*/
}