package org.rejna.pkcs11.x32;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Struct;
import org.rejna.pkcs11.Version;

@Library("xltCk")
@Struct(pack=1)
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
	public int flags() {
		return this.io.getIntField(this, 2);
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
