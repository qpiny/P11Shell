package org.rejna.pkcs11;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Struct;
import org.rejna.pkcs11.Version;

@Library("xltCk")
public class Info extends StructObject {
	public Info() {
		super();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Info(Pointer pointer) {
		super(pointer);
	}

	/*
	abstract public Version cryptokiVersion();
	abstract public Pointer<Byte> manufacturerID();
	abstract public int flags();
	abstract public Pointer<Byte> libraryDescription();
	abstract public Version libraryVersion();
	*/
	
	public String manufacturerIDStr() {
		return new String(manufacturerID().getBytes());
	}
	
	public String libraryDescriptionStr() {
		return new String(libraryDescription().getBytes());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("cryptokiVersion: ").append(cryptokiVersion()).append('\n');
		sb.append("flags: ").append(new Flags(flags())).append('\n'); 
		sb.append("libraryDescription: ").append(libraryDescriptionStr()).append('\n');              
		sb.append("libraryVersion: ").append(libraryVersion()).append('\n'); 
		sb.append("manufacturerID: ").append(manufacturerIDStr()).append('\n');
		return sb.toString();
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
	@CLong
	public long flags() {
		return this.io.getCLongField(this, 2);
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
