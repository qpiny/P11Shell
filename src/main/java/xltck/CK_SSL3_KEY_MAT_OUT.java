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
public class CK_SSL3_KEY_MAT_OUT extends StructObject {
	public CK_SSL3_KEY_MAT_OUT() {
		super();
	}
	public CK_SSL3_KEY_MAT_OUT(Pointer pointer) {
		super(pointer);
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(0) 
	public int hClientMacSecret() {
		return this.io.getIntField(this, 0);
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(0) 
	public CK_SSL3_KEY_MAT_OUT hClientMacSecret(int hClientMacSecret) {
		this.io.setIntField(this, 0, hClientMacSecret);
		return this;
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(1) 
	public int hServerMacSecret() {
		return this.io.getIntField(this, 1);
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(1) 
	public CK_SSL3_KEY_MAT_OUT hServerMacSecret(int hServerMacSecret) {
		this.io.setIntField(this, 1, hServerMacSecret);
		return this;
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(2) 
	public int hClientKey() {
		return this.io.getIntField(this, 2);
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(2) 
	public CK_SSL3_KEY_MAT_OUT hClientKey(int hClientKey) {
		this.io.setIntField(this, 2, hClientKey);
		return this;
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(3) 
	public int hServerKey() {
		return this.io.getIntField(this, 3);
	}
	/// C type : CK_OBJECT_HANDLE
	@Field(3) 
	public CK_SSL3_KEY_MAT_OUT hServerKey(int hServerKey) {
		this.io.setIntField(this, 3, hServerKey);
		return this;
	}
	/// C type : CK_BYTE_PTR
	@Field(4) 
	public Pointer<Byte > pIVClient() {
		return this.io.getPointerField(this, 4);
	}
	/// C type : CK_BYTE_PTR
	@Field(4) 
	public CK_SSL3_KEY_MAT_OUT pIVClient(Pointer<Byte > pIVClient) {
		this.io.setPointerField(this, 4, pIVClient);
		return this;
	}
	/// C type : CK_BYTE_PTR
	@Field(5) 
	public Pointer<Byte > pIVServer() {
		return this.io.getPointerField(this, 5);
	}
	/// C type : CK_BYTE_PTR
	@Field(5) 
	public CK_SSL3_KEY_MAT_OUT pIVServer(Pointer<Byte > pIVServer) {
		this.io.setPointerField(this, 5, pIVServer);
		return this;
	}
}
