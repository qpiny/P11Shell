package org.rejna.pkcs11;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Library;

@Library("xltCk") 
public abstract class TokenInfo extends StructObject {
	
	public TokenInfo() {
		super();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TokenInfo(Pointer pointer) {
		super(pointer);
	}
	
	public abstract Pointer<Byte> label();
	public abstract Pointer<Byte> manufacturerID();
	public abstract Pointer<Byte> model();
	public abstract Pointer<Byte> serialNumber();
	public abstract int flags();
	public abstract int maxSessionCount();
	public abstract int sessionCount();
	public abstract int maxRwSessionCount();
	public abstract int rwSessionCount();
	public abstract int maxPinLen();
	public abstract int minPinLen();
	public abstract int totalPublicMemory();
	public abstract int freePublicMemory();
	public abstract int totalPrivateMemory();
	public abstract int freePrivateMemory();
	public abstract Version hardwareVersion();
	public abstract Version firmwareVersion();
	public abstract Pointer<Byte> utcTime();
	
	public String getLabelStr() {
		return new String(label().getBytes());
	}
	
	public String getManufacturerIDStr() {
		return new String(manufacturerID().getBytes());
	}

	public String getModelStr() {
		return new String(model().getBytes());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("firmwareVersion: ").append(firmwareVersion()).append('\n'); 
		sb.append("flags: ").append(new Flags(flags())).append('\n'); 
		sb.append("hardwareVersion: ").append(hardwareVersion()).append('\n'); 
		sb.append("label: ").append(getLabelStr()).append('\n');           
		sb.append("manufacturerID: ").append(getManufacturerIDStr()).append('\n');
		sb.append("model: ").append(getModelStr()).append('\n');        
		sb.append("serialNumber: ").append(serialNumber()).append('\n'); 
		sb.append("ulFreePrivateMemory: ").append(freePrivateMemory()).append('\n');
		sb.append("ulFreePublicMemory: ").append(freePublicMemory()).append('\n'); 
		sb.append("ulMaxPinLen: ").append(maxPinLen()).append('\n');
		sb.append("ulMaxRwSessionCount: ").append(maxRwSessionCount()).append('\n');
		sb.append("ulMaxSessionCount: ").append(maxSessionCount()).append('\n');
		sb.append("ulMinPinLen: ").append(minPinLen()).append('\n');
		sb.append("ulRwSessionCount: ").append(rwSessionCount()).append('\n');
		sb.append("ulSessionCount: ").append(sessionCount()).append('\n');
		sb.append("ulTotalPrivateMemory: ").append(totalPrivateMemory()).append('\n');
		sb.append("ulTotalPublicMemory: ").append(totalPublicMemory()).append('\n');
		//sb.append("utcTime: ").append(new Label(tokenInfo.utcTime().getBytes())).append('\n');
		return sb.toString();
	}
}
