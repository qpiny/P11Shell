package org.rejna.pkcs11.x64;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.rejna.pkcs11.Version;

@Library("xltCk")
//@Struct(pack=1)
public class Info extends org.rejna.pkcs11.Info {
	public Info() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public Info(Pointer pointer) {
		super(pointer);
	}

	@Field(0) 
	public Version cryptokiVersion() {
		return this.io.getNativeObjectField(this, 0);
	}
	
	@Array({32}) 
	@Field(1) 
	public Pointer<Byte> manufacturerID() {
		return this.io.getPointerField(this, 1);
	}
	
	@Field(2) 
	public long flags64() {
		return this.io.getLongField(this, 2);
	}
	
	public int flags() {
		return (int) flags64();
	}
	
	@Array({32}) 
	@Field(3) 
	public Pointer<Byte> libraryDescription() {
		return this.io.getPointerField(this, 3);
	}
	
	@Field(4) 
	public Version libraryVersion() {
		return this.io.getNativeObjectField(this, 4);
	}
}
