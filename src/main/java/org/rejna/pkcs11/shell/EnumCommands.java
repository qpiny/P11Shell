package org.rejna.pkcs11.shell;

import java.io.IOException;
import java.util.Arrays;

import org.rejna.pkcs11.Attribute;
import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.Defs;
import org.rejna.pkcs11.InvalidMechanismException;
import org.rejna.pkcs11.MechanismType;
import org.rejna.pkcs11.P11Exception;
import org.rejna.pkcs11.P11Object;
import org.rejna.pkcs11.PKCS11;


public enum EnumCommands {
	GET_INFO {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			System.out.println(state.getPKCS11().getInfo());
		}
	},
	LIST_SLOT {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			int[] present = p11.getSlotList(true);
			for (int s : p11.getSlotList(false)) {
				if (Arrays.binarySearch(present, s) < 0)
					System.out.print(" " + s + " ");
				else
					System.out.print(" [" + s + "] ");
			}
			System.out.println();
		}
	},
	GET_SLOT_INFO {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			System.out.println(p11.getSlotInfo(slot));
		}
	},
	GET_TOKEN_INFO {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			System.out.println(p11.getTokenInfo(slot));
		}
	},
	LIST_MECHANISM {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception, InvalidMechanismException {
			for (MechanismType m : p11.getMechanism(slot)) {
				System.out.println(m);
				System.out.println(p11.getMechanismInfo(slot, m));
			}
		}
	},
	OPEN_SESSION {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			state.getPKCS11().openSession(slot, Boolean.parseBoolean(args[2]));
			state.setInSession(true);
		}
	},
	LOGIN {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception, IOException {
			try {
				state.getPKCS11().login(state.getPIN());
			} catch (P11Exception e) {
				if (e.getCode() != Defs.CKR_USER_ALREADY_LOGGED_IN)
					throw e;
				System.out.println("Already logged");
			}
			state.setLogged(true);
		}
	},
	LOGOUT {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			p11.logout();
			state.setLogged(false);
		}
	},
	CLOSE_SESSION {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			if (state.isLogged()) {
				p11.logout();
				state.setLogged(false);
			}
			p11.closeSession();
			state.setInSession(false);
		}
	},
	CLOSE_ALL_SESSION {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			if (state.isLogged()) {
				p11.logout();
				state.setLogged(false);
			}
			p11.closeAllSession(slot);
			state.setInSession(false);
		}
	},
	FIND_OBJECTS {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception {
			for (P11Object o : p11.findObjects(state.getAttributes()))
				System.out.println(o);
		}
	},
	GET_ATTRIBUTE {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws NumberFormatException, P11Exception {
			for (Attribute attribute : state.getAttributes()) {
				p11.getAttribute(new P11Object(Integer.parseInt(args[2])), attribute);
				System.out.println(attribute);
			}
		}
	},
	SET_ATTRIBUTE {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws NumberFormatException, P11Exception {
			for (Attribute attribute : state.getAttributes())
				p11.setAttribute(new P11Object(Integer.parseInt(args[2])), attribute);
		}
	},
	WRAP {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) {
			
		}
	},
	GENERATE_KEY {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) {
			
		}
	},
	SELECT_SLOT {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) {
			state.setSlot(Integer.parseInt(args[2]));			
		}
	},
	TEMPLATE_ADD {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) {
			state.addAttribute(AttributeType.valueOf(args[2]).getAttribute(args[3]));
		}
	},
	TEMPLATE_REMOVE {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) {
			state.removeAttribute(AttributeType.valueOf(args[2]));
		}
	},
	TEMPLATE_CLEAR {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) {
			state.clearAttribute();
		}
	},
	TEMPLATE_LIST {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, String[] args) {
			for (Attribute attribute : state.getAttributes())
				System.out.println(attribute);
		}
	};	


	public abstract void execute(ShellState state, PKCS11 p11, int slot, String[] args) throws P11Exception, InvalidMechanismException, IOException;
	
	public void execute(ShellState state, String[] args) throws P11Exception, InvalidMechanismException, IOException {
		execute(state, state.getPKCS11(), state.getSlot(), args);
	}
/*
  		try {
 
			PKCS11 p11 = state.getPKCS11();
			int slot = state.getSlot();
			switch (this) {
			
			

				case TEMPLATE_ADD:
					AttributeType.valueOf(args[3]);
				case FIND_OBJECTS:
					Iterator<String> crit = Arrays.asList(args).iterator();
					crit.next(); // "find"
					crit.next(); // "object"
					Attribute[] criteria = new Attribute[args.length - 2];
					int index = 0;
					while (crit.hasNext()) {
						String c = crit.next();
					}
					//p11.findObjects(criteria.toArray(new ATtrib));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/
}
