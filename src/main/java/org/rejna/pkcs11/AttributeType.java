package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.javatuples.Pair;
import org.rejna.pkcs11.AttributeFormat;

public enum AttributeType implements P11Enum {
	CKA_CLASS(0x00000000, AttributeFormat.INTEGER) {
		@SuppressWarnings("unchecked")
		@Override public Class<ObjectType> getPossible() { return ObjectType.class; }
	},
	CKA_TOKEN(0x00000001, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_PRIVATE(0x00000002, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_LABEL(0x00000003, AttributeFormat.STRING) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_APPLICATION(0x00000010, AttributeFormat.STRING) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_VALUE(0x00000011, AttributeFormat.BINARY) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_OBJECT_ID(0x00000012, AttributeFormat.BINARY) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_CERTIFICATE_TYPE(0x00000080, AttributeFormat.INTEGER) { // CKC ?
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_ISSUER(0x00000081, AttributeFormat.BINARY) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	}, // structured
	CKA_SERIAL_NUMBER(0x00000082, AttributeFormat.BINARY) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_AC_ISSUER(0x00000083, AttributeFormat.BINARY) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	}, // like CKA_ISSUER ?
	CKA_OWNER(0x00000084, AttributeFormat.INTEGER) { // CKU
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_ATTR_TYPES(0x00000085, AttributeFormat.INTEGER) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_TRUSTED(0x00000086, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_KEY_TYPE(0x00000100, AttributeFormat.INTEGER) {
		@Override public <T extends Enum<T>> Class<T> getPossible() { return null; }
	},
	CKA_SUBJECT(0x00000101, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {	return null;
		}
	},
	CKA_ID(0x00000102, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_SENSITIVE(0x00000103, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_ENCRYPT(0x00000104, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_DECRYPT(0x00000105, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_WRAP(0x00000106, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_UNWRAP(0x00000107, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_SIGN(0x00000108, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_SIGN_RECOVER(0x00000109, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_VERIFY(0x0000010A, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_VERIFY_RECOVER(0x0000010B, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_DERIVE(0x0000010C, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_START_DATE(0x00000110, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_END_DATE(0x00000111, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_MODULUS(0x00000120, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_MODULUS_BITS(0x00000121, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_PUBLIC_EXPONENT(0x00000122, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_PRIVATE_EXPONENT(0x00000123, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_PRIME_1(0x00000124, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},	CKA_PRIME_2(0x00000125, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_EXPONENT_1(0x00000126, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},	CKA_EXPONENT_2(0x00000127, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
CKA_COEFFICIENT(0x00000128, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_PRIME(0x00000130, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},	CKA_SUBPRIME(0x00000131, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},	CKA_BASE(0x00000132, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},	CKA_PRIME_BITS(0x00000133, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},	CKA_SUBPRIME_BITS(0x00000134, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_VALUE_BITS(0x00000160, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_VALUE_LEN(0x00000161, AttributeFormat.BINARY)
 {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_EXTRACTABLE(0x00000162, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_LOCAL(0x00000163, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_NEVER_EXTRACTABLE(0x00000164, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_ALWAYS_SENSITIVE(0x00000165, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	//CKA_KEY_GEN_MECHANISM(0x00000166),
	CKA_MODIFIABLE(0x00000170, AttributeFormat.BOOLEAN) {
		@SuppressWarnings("unchecked")
		@Override
		public Class<BooleanType> getPossible() { return BooleanType.class; }
	},
	CKA_ECDSA_PARAMS(0x00000180, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_EC_PARAMS(0x00000180, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_EC_POINT(0x00000181, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_SECONDARY_AUTH(0x00000200, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_AUTH_PIN_FLAGS(0x00000201, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_HW_FEATURE_TYPE(0x00000300, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_RESET_ON_INIT(0x00000301, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_HAS_RESET(0x00000302, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_VENDOR_DEFINED(0x80000000, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	},
	CKA_IBM_OPAQUE(0x80000001, AttributeFormat.BINARY) {
		@Override
		public <T extends Enum<T>> Class<T> getPossible() {
			return null;
		}
	};
	
	private int value;
	private AttributeFormat format;
	
	private AttributeType(int value, AttributeFormat format) {
		this.value = value;
		this.format = format;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getDefaultSize() {
		return format.getDefaultSize();
	}
	
	public static AttributeType valueOf(int value) {
		for (AttributeType attr : AttributeType.values())
			if (attr.getValue() == value)
				return attr;
		return null;
	}

	public String toString(Attribute attribute) throws InvalidAttributeException {
		return format.toString(attribute.value(), (int) attribute.size());
	}
	
	public Object toObject(Attribute attribute) throws InvalidAttributeException {
		return format.toObject(attribute.value(), (int) attribute.size());
	}
	
	public Attribute createAttribute() {
		return createAttribute(getDefaultSize());
	}
	
	public Attribute createAttribute(int size) {
		return new Attribute(this, Pointer.allocateBytes(size), size);
	}
	
	public Attribute createAttribute(String value) {
		return new Attribute(this, Pointer.pointerToCString(value), value.length());
	}
	
	public Attribute createAttribute(Object obj) {
		Pair<Pointer<?>,Long> p  = format.fromObject(obj);
		return new Attribute(this, p.getValue0(), p.getValue1());
	}
	
	/*
	public Attribute getAttribute() {
		return format.createAttribute(this);
	}
	
	public Attribute getAttribute(int size) {
		return format.createAttribute(this, size);
	}*
	
	public Attribute getAttribute(Object value) {
		return fillTemplate(getAttribute(), value);
	}

	public Attribute getAttribute(String value) {
		return fillTemplate(getAttribute(), value);
	}

	public Attribute fillTemplate(Attribute attribute, Object value) {
		attribute.attributeType(this);
		return format.setAttribute(attribute, value);
	}
	
	public <T extends Enum<T>> Attribute fillTemplate(Attribute attribute, String value) {
		Class<T> possible = getPossible();
		if (possible != null) {
			T e = Enum.valueOf(possible, value);
			return fillTemplate(attribute, ((P11Enum)e).getValue());
		}
		return fillTemplate(attribute, (Object) value);
	}
	*/
	
	public abstract <T extends Enum<T>> Class<T> getPossible();
	
	public String[] enumToString(Enum<?>[] es) {
		String[] ret = new String[es.length];
		for (int i = 0; i < es.length; i++)
			ret[i] = es[i].name();
		return ret;
	}
}
