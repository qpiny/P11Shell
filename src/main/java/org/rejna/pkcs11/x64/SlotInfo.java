package org.rejna.pkcs11.x64;

import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.rejna.pkcs11.Version;

@Library("xltCk")
//@Struct(pack=1)
public class SlotInfo extends org.rejna.pkcs11.SlotInfo {
	
	public SlotInfo() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public SlotInfo(Pointer pointer) {
		super(pointer);
	}

	@Array({64}) 
	@Field(0) 
	public Pointer<Byte> slotDescription() {
		return this.io.getPointerField(this, 0);
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
	
	@Field(3) 
	public Version hardwareVersion() {
		return this.io.getNativeObjectField(this, 3);
	}

	@Field(4) 
	public Version firmwareVersion() {
		return this.io.getNativeObjectField(this, 4);
	}
}
