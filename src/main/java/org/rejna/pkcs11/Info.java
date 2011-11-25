package org.rejna.pkcs11;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.rejna.pkcs11.Version;

public abstract class Info extends StructObject {
	public Info() {
		super();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Info(Pointer pointer) {
		super(pointer);
	}

	abstract public Version cryptokiVersion();
	abstract public Pointer<Byte> manufacturerID();
	abstract public int flags();
	abstract public Pointer<Byte> libraryDescription();
	abstract public Version libraryVersion();
	
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
}
