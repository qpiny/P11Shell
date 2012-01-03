package org.rejna.pkcs11;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;
 
public class MechanismInfo extends StructObject {
	public MechanismInfo() {
		super();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MechanismInfo(Pointer pointer) {
		super(pointer);
	}
/*
	public abstract int minKeySize(); 
	public abstract int maxKeySize(); 
	public abstract int flags();
*/
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ulMinKeySize: ").append(minKeySize()).append('\n');
		sb.append("ulMaxKeySize: ").append(maxKeySize()).append('\n');
		sb.append("flags: ").append(new Flags(flags())).append('\n');
		return sb.toString();
	}
	
	@Field(0) 
	@CLong
	public long minKeySize() {
		return this.io.getCLongField(this, 0);
	}
	
	@Field(1) 
	@CLong
	public long maxKeySize() {
		return this.io.getCLongField(this, 1);
	}
	
	@Field(2)
	@CLong
	public long flags() {
		return this.io.getCLongField(this, 2);
	}
}
