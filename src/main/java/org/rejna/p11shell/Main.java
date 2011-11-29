package org.rejna.p11shell;

import org.apache.commons.codec.binary.Base64;
import org.rejna.pkcs11.Attribute;
import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.Defs;
import org.rejna.pkcs11.InvalidMechanismException;
import org.rejna.pkcs11.MechanismType;
import org.rejna.pkcs11.P11Exception;
import org.rejna.pkcs11.P11Object;
import org.rejna.pkcs11.PKCS11;

import xltck.XltCkLibrary;

public class Main {


	public static void main(String[] args) {
		PKCS11 p11 = PKCS11.getInstance(32);
	
		try {
			System.out.println(p11.getInfo());		
			int[] slots = p11.getSlotList(true);
			if (slots.length == 1) {
				int slot = slots[0];
				System.out.println("Using slot " + slot);
				//System.out.println(p11.getSlotInfo(slot));
				//System.out.println(p11.getTokenInfo(1));
				//if (false)
				for (MechanismType m : p11.getMechanism(slot)) {
					System.out.println(m);
					System.out.println(p11.getMechanismInfo(slot, m));
				}
				
				try {
					p11.openSession(slot, false);
					try {
						try {
							p11.login("<MyPinCode>");
						} catch (P11Exception e) {
							if (e.getCode() != Defs.CKR_USER_ALREADY_LOGGED_IN)
								throw e;
						}
						
						
						/* first public key */
						/*P11Object publicKey = p11.findObjects(
								p11.createAttribute(AttributeType.CKA_CLASS, Defs.CKO_PUBLIC_KEY),
								p11.createAttribute(AttributeType.CKA_WRAP, true)
								)[0];
						System.out.println("First public key : " + publicKey);
						*/
						/* first private key */
						/*P11Object privateKey = p11.findObjects(
								p11.createAttribute(AttributeType.CKA_CLASS, Defs.CKO_PRIVATE_KEY)
								//p11.createAttribute(AttributeType.CKA_EXTRACTABLE, true)
								)[0];
						System.out.println("First private key : " + privateKey);
						*/
						
						/* create key */
						/*
						P11Object myKey = p11.createObject(
								p11.createAttribute(AttributeType.CKA_CLASS, Defs.CKO_SECRET_KEY),
								p11.createAttribute(AttributeType.CKA_KEY_TYPE, Defs.CKK_DES),
								//p11.createAttribute(AttributeType.CKA_LABEL, "MySecreKey"),
								p11.createAttribute(AttributeType.CKA_TOKEN, true),
								p11.createAttribute(AttributeType.CKA_VALUE, new byte[] {0x11,0x12,0x13, 0x14, 0x15,0x16,0x17,0x18})
								//p11.createAttribute(AttributeType.CKA_PRIVATE, true),
								//p11.createAttribute(AttributeType.CKA_MODIFIABLE, false),
								//p11.createAttribute(AttributeType.CKA_EXTRACTABLE, false)
								);
						*/
						//P11Object myKey = p11.generateKey(p11.createMechanism(MechanismType.CKM_DES_KEY_GEN));
						//System.out.println(Base64.encodeBase64String(p11.wrap(//CKM_RSA_PKCS, CKM_RSA_X_509
						//		p11.createMechanism(MechanismType.CKM_RSA_PKCS), publicKey, myKey, 3000)));
					} catch (P11Exception e) {
						e.printStackTrace();
					} finally {
						p11.logout();
					}
				} catch (P11Exception e) {
					e.printStackTrace();
				} finally {
					try {
						p11.closeSession();
					} catch (P11Exception e) {
						e.printStackTrace();
						p11.closeAllSession(slot);
					}
				}
			}
		} catch (P11Exception e) {
			e.printStackTrace();
		} catch (InvalidMechanismException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				p11.close();
			} catch (P11Exception e) {
				e.printStackTrace();
			}
		}
	}

}
