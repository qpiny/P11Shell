package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;

public class SlotInfo extends StructObject {
	public SlotInfo() {
		super();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SlotInfo(Pointer pointer) {
		super(pointer);
	}
/*
	public abstract Pointer<Byte> slotDescription();
	public abstract Pointer<Byte> manufacturerID();
	public abstract int flags();
	public abstract Version hardwareVersion();
	public abstract Version firmwareVersion();
	*/
	public String slotDescriptionStr() {
		return new String(slotDescription().getBytes());
	}

	public String manufacturerIDStr() {
		return new String(manufacturerID().getBytes());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("firmwareVersion: ").append(firmwareVersion()).append('\n');
		sb.append("flags: ").append(new Flags(flags())).append('\n'); 
		sb.append("hardwareVersion: ").append(hardwareVersion()).append('\n');
		sb.append("manufacturerID: ").append(manufacturerIDStr()).append('\n');                          
		sb.append("slotDescription: ").append(slotDescriptionStr()).append('\n');
		return sb.toString();
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
	@CLong
	public long flags() {
		return this.io.getCLongField(this, 2);
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
