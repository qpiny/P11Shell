package org.rejna.pkcs11;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

@Library("xltCk") 
public class Version extends StructObject {
	public Version() {
		super();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Version(Pointer pointer) {
		super(pointer);
	}
	
	@Field(0) 
	public byte getMajor() {
		return this.io.getByteField(this, 0);
	}
	
	@Field(0) 
	public Version setMajor(byte major) {
		this.io.setByteField(this, 0, major);
		return this;
	}
	
	@Field(1) 
	public byte getMinor() {
		return this.io.getByteField(this, 1);
	}
	
	@Field(1) 
	public Version setMinor(byte minor) {
		this.io.setByteField(this, 1, minor);
		return this;
	}
	
	@Override
	public String toString() {
		return getMajor() + "." + getMinor();
	}
}
