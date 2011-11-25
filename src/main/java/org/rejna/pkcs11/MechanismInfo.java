package org.rejna.pkcs11;
import org.bridj.Pointer;
import org.bridj.StructObject;
 
public abstract class MechanismInfo extends StructObject {
	public MechanismInfo() {
		super();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MechanismInfo(Pointer pointer) {
		super(pointer);
	}

	public abstract int minKeySize(); 
	public abstract int maxKeySize(); 
	public abstract int flags();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ulMinKeySize: ").append(minKeySize()).append('\n');
		sb.append("ulMaxKeySize: ").append(maxKeySize()).append('\n');
		sb.append("flags: ").append(new Flags(flags())).append('\n');
		return sb.toString();
	}
}
