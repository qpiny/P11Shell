package org.rejna.pkcs11;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

@Library("xltCk") 
public class TokenInfo extends StructObject {
	
	public TokenInfo() {
		super();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TokenInfo(Pointer pointer) {
		super(pointer);
	}
	/*
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
	*/
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
	
	@Array({32}) 
	@Field(0) 
	public Pointer<Byte> label() {
		return this.io.getPointerField(this, 0);
	}
	
	@Array({32}) 
	@Field(1) 
	public Pointer<Byte> manufacturerID() {
		return this.io.getPointerField(this, 1);
	}
	
	@Array({16}) 
	@Field(2) 
	public Pointer<Byte> model() {
		return this.io.getPointerField(this, 2);
	}

	@Array({16}) 
	@Field(3)
	public Pointer<Byte> serialNumber() {
		return this.io.getPointerField(this, 3);
	}

	@Field(4)
	@CLong
	public long flags() {
		return this.io.getCLongField(this, 4);
	}
	 
	@Field(5)
	@CLong
	public long maxSessionCount() {
		return this.io.getCLongField(this, 5);
	}

	@Field(6)
	@CLong
	public long sessionCount() {
		return this.io.getCLongField(this, 6);
	}

	@Field(7)
	@CLong
	public long maxRwSessionCount() {
		return this.io.getCLongField(this, 7);
	}

	@Field(8)
	@CLong
	public long rwSessionCount() {
		return this.io.getCLongField(this, 8);
	}

	@Field(9)
	@CLong
	public long maxPinLen() {
		return this.io.getCLongField(this, 9);
	}

	@Field(10)
	@CLong
	public long minPinLen() {
		return this.io.getCLongField(this, 10);
	}

	@Field(11)
	@CLong
	public long totalPublicMemory() {
		return this.io.getCLongField(this, 11);
	}

	@Field(12)
	@CLong
	public long freePublicMemory() {
		return this.io.getCLongField(this, 12);
	}

	@Field(13) 
	@CLong
	public long totalPrivateMemory() {
		return this.io.getCLongField(this, 13);
	}

	@Field(14) 
	@CLong
	public long freePrivateMemory() {
		return this.io.getCLongField(this, 14);
	}

	@Field(15) 
	public Version hardwareVersion() {
		return this.io.getNativeObjectField(this, 15);
	}
	
	@Field(16) 
	public Version firmwareVersion() {
		return this.io.getNativeObjectField(this, 16);
	}

	@Array({16}) 
	@Field(17) 
	public Pointer<Byte> utcTime() {
		return this.io.getPointerField(this, 17);
	}
}
