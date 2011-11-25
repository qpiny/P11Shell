package org.rejna.pkcs11;

import org.bridj.Pointer;
import org.bridj.StructObject;

public abstract class SlotInfo extends StructObject {
	public SlotInfo() {
		super();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SlotInfo(Pointer pointer) {
		super(pointer);
	}

	public abstract Pointer<Byte> slotDescription();
	public abstract Pointer<Byte> manufacturerID();
	public abstract int flags();
	public abstract Version hardwareVersion();
	public abstract Version firmwareVersion();
	
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
}
