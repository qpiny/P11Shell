package org.rejna.pkcs11;

import org.rejna.pkcs11.AttributeFormat;
import org.rejna.pkcs11.shell.EnumToken;

public enum AttributeType implements P11Enum {
	CKA_CLASS(0x00000000, AttributeFormat.INTEGER) {
		@Override
		public P11EnumWrapper<ObjectType> getPossible() { return ObjectType.P11Enum; }
	},
	CKA_TOKEN(0x00000001, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_PRIVATE(0x00000002, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_LABEL(0x00000003, AttributeFormat.STRING) {
		@Override
		public P11EnumWrapper getPossible() { return null; }
	},
	CKA_APPLICATION(0x00000010, AttributeFormat.STRING) {
		public P11EnumWrapper getPossible() { return null; }
	},
	CKA_VALUE(0x00000011, AttributeFormat.BINARY) {
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	},
	CKA_OBJECT_ID(0x00000012, AttributeFormat.BINARY) {
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	},
	CKA_CERTIFICATE_TYPE(0x00000080, AttributeFormat.INTEGER) { // CKC ?
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	},
	CKA_ISSUER(0x00000081, AttributeFormat.BINARY) {
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	}, // structured
	CKA_SERIAL_NUMBER(0x00000082, AttributeFormat.BINARY) {
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	},
	CKA_AC_ISSUER(0x00000083, AttributeFormat.BINARY) {
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	}, // like CKA_ISSUER ?
	CKA_OWNER(0x00000084, AttributeFormat.INTEGER) { // CKU
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	},
	//CKA_ATTR_TYPES(0x00000085, AttributeFormat.INTEGER),
	CKA_TRUSTED(0x00000086, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_KEY_TYPE(0x00000100, AttributeFormat.INTEGER) {
		@Override
		public P11EnumWrapper<P11Enum> getPossible() { return null; }
	},
//	CKA_SUBJECT(0x00000101),
//	CKA_ID(0x00000102),
	CKA_SENSITIVE(0x00000103, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_ENCRYPT(0x00000104, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_DECRYPT(0x00000105, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_WRAP(0x00000106, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_UNWRAP(0x00000107, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_SIGN(0x00000108, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_SIGN_RECOVER(0x00000109, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_VERIFY(0x0000010A, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_VERIFY_RECOVER(0x0000010B, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_DERIVE(0x0000010C, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
//	CKA_START_DATE(0x00000110),
//	CKA_END_DATE(0x00000111),
//	CKA_MODULUS(0x00000120),
//	CKA_MODULUS_BITS(0x00000121),
//	CKA_PUBLIC_EXPONENT(0x00000122),
//	CKA_PRIVATE_EXPONENT(0x00000123),
//	CKA_PRIME_1(0x00000124),
//	CKA_PRIME_2(0x00000125),
//	CKA_EXPONENT_1(0x00000126),
//	CKA_EXPONENT_2(0x00000127),
//	CKA_COEFFICIENT(0x00000128),
//	CKA_PRIME(0x00000130),
//	CKA_SUBPRIME(0x00000131),
//	CKA_BASE(0x00000132),
//	CKA_PRIME_BITS(0x00000133),
//	CKA_SUBPRIME_BITS(0x00000134),
//	CKA_VALUE_BITS(0x00000160),
//	CKA_VALUE_LEN(0x00000161),
	CKA_EXTRACTABLE(0x00000162, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_LOCAL(0x00000163, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_NEVER_EXTRACTABLE(0x00000164, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	CKA_ALWAYS_SENSITIVE(0x00000165, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	},
	//CKA_KEY_GEN_MECHANISM(0x00000166),
	CKA_MODIFIABLE(0x00000170, AttributeFormat.BOOLEAN) {
		@Override
		public P11EnumWrapper<BooleanType> getPossible() { return BooleanType.P11Enum; }
	};
	//CKA_ECDSA_PARAMS(0x00000180),
	//CKA_EC_PARAMS(0x00000180),
//	CKA_EC_POINT(0x00000181),
//	CKA_SECONDARY_AUTH(0x00000200),
//	CKA_AUTH_PIN_FLAGS(0x00000201),
//	CKA_HW_FEATURE_TYPE(0x00000300),
//	CKA_RESET_ON_INIT(0x00000301),
//	CKA_HAS_RESET(0x00000302),
//	CKA_VENDOR_DEFINED(0x80000000),
//	CKA_IBM_OPAQUE(0x80000001);
	
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
		return format.toString(attribute.value(), attribute.size());
	}
	
	public Object toObject(Attribute attribute) throws InvalidAttributeException {
		return format.toObject(attribute.value(), attribute.size());
	}
	
	public Attribute getAttribute() {
		return format.createAttribute(this);
	}
	
	public Attribute getAttribute(int size) {
		return format.createAttribute(this, size);
	}
	
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
	
	public Attribute fillTemplate(Attribute attribute, String value) {
		P11EnumWrapper<?> possible = getPossible();
		return fillTemplate(attribute, possible == null ? (Object) value : possible.valueOf(value).getValue());
	}
	
	public abstract <T extends P11Enum> P11EnumWrapper<T> getPossible();
	
	public String[] enumToString(Enum<?>[] es) {
		String[] ret = new String[es.length];
		for (int i = 0; i < es.length; i++)
			ret[i] = es[i].name();
		return ret;
	}

	public static final P11EnumWrapper<AttributeType> P11Enum = new  P11EnumWrapper<AttributeType>() {
		@Override public AttributeType valueOf(String str) { return AttributeType.valueOf(str); }
		@Override public AttributeType[] values() { return AttributeType.values(); }
	};
}
