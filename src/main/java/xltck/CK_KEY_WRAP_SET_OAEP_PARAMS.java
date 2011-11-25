package xltck;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : xltCk.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("xltCk") 
public class CK_KEY_WRAP_SET_OAEP_PARAMS extends StructObject {
	public CK_KEY_WRAP_SET_OAEP_PARAMS() {
		super();
	}
	public CK_KEY_WRAP_SET_OAEP_PARAMS(Pointer pointer) {
		super(pointer);
	}
	/**
	 * block contents byte<br>
	 * C type : CK_BYTE
	 */
	@Field(0) 
	public byte bBC() {
		return this.io.getByteField(this, 0);
	}
	/**
	 * block contents byte<br>
	 * C type : CK_BYTE
	 */
	@Field(0) 
	public CK_KEY_WRAP_SET_OAEP_PARAMS bBC(byte bBC) {
		this.io.setByteField(this, 0, bBC);
		return this;
	}
	/**
	 * extra data<br>
	 * C type : CK_BYTE_PTR
	 */
	@Field(1) 
	public Pointer<Byte > pX() {
		return this.io.getPointerField(this, 1);
	}
	/**
	 * extra data<br>
	 * C type : CK_BYTE_PTR
	 */
	@Field(1) 
	public CK_KEY_WRAP_SET_OAEP_PARAMS pX(Pointer<Byte > pX) {
		this.io.setPointerField(this, 1, pX);
		return this;
	}
	/**
	 * length of extra data in bytes<br>
	 * C type : CK_ULONG
	 */
	@Field(2) 
	public int ulXLen() {
		return this.io.getIntField(this, 2);
	}
	/**
	 * length of extra data in bytes<br>
	 * C type : CK_ULONG
	 */
	@Field(2) 
	public CK_KEY_WRAP_SET_OAEP_PARAMS ulXLen(int ulXLen) {
		this.io.setIntField(this, 2, ulXLen);
		return this;
	}
}
