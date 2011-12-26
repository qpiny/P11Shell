package org.rejna.p11shell;

import java.io.IOException;
import java.util.Arrays;

import org.rejna.pkcs11.Attribute;
import org.rejna.pkcs11.AttributeType;
import org.rejna.pkcs11.Defs;
import org.rejna.pkcs11.InvalidMechanismException;
import org.rejna.pkcs11.Mechanism;
import org.rejna.pkcs11.MechanismType;
import org.rejna.pkcs11.P11Exception;
import org.rejna.pkcs11.P11Object;
import org.rejna.pkcs11.PKCS11;
import org.rejna.pkcs11.BooleanType;
import org.rejna.shell.EnumToken;
import org.rejna.shell.IntegerToken;
import org.rejna.shell.ShellCommand;
import org.rejna.shell.StaticToken;
import org.rejna.shell.Token;


public enum P11Commands implements ShellCommand<ShellState> {
	GET_INFO(_("info"), _("library")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			System.out.println(state.getPKCS11().getInfo());
		}

		@Override
		public boolean available(ShellState state) {
			return true;
		}
	},
	LIST_SLOT(_("list"), _("slot")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			int[] present = p11.getSlotList(true);
			for (int s : p11.getSlotList(false)) {
				if (Arrays.binarySearch(present, s) < 0)
					System.out.print(" " + s + " ");
				else
					System.out.print(" [" + s + "] ");
			}
			System.out.println();
		}

		@Override
		public boolean available(ShellState state) {
			return true;
		}
	},
	GET_SLOT_INFO(_("info"), _("slot")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			System.out.println(p11.getSlotInfo(slot));
		}

		@Override
		public boolean available(ShellState state) {
			return state.getSlot() != -1;
		}
	},
	GET_TOKEN_INFO(_("info"), _("token")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			System.out.println(p11.getTokenInfo(slot));
		}

		@Override
		public boolean available(ShellState state) {
			return state.getSlot() != -1;
		}
	},
	LIST_MECHANISM(_("list"), _("mechanism")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception, InvalidMechanismException {
			for (MechanismType m : p11.getMechanism(slot)) {
				System.out.println(m);
				System.out.println(p11.getMechanismInfo(slot, m));
			}
		}

		@Override
		public boolean available(ShellState state) {
			return state.getSlot() != -1;
		}
	},
	OPEN_SESSION(_("open"), _("session"), new EnumToken<BooleanType>(BooleanType.class)) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			state.getPKCS11().openSession(slot, ((BooleanType)args[0]).getBoolValue());
			state.setInSession(true);
		}

		@Override
		public boolean available(ShellState state) {
			return state.getSlot() != -1 && !state.isInSession();
		}
	},
	LOGIN(_("login")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception, IOException {
			try {
				state.getPKCS11().login(null);
			} catch (P11Exception e) {
				if (e.getCode() == Defs.CKR_USER_ALREADY_LOGGED_IN) {
					state.setLogged(true);
					return;
				}
			}
			try {
				state.getPKCS11().login(state.getPIN());
			} catch (P11Exception e) {
				if (e.getCode() != Defs.CKR_USER_ALREADY_LOGGED_IN)
					throw e;
				System.out.println("Already logged");
			}
			state.setLogged(true);
		}

		@Override
		public boolean available(ShellState state) {
			return state.isInSession() && !state.isLogged();
		}
	},
	LOGOUT(_("logout")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			p11.logout();
			state.setLogged(false);
		}

		@Override
		public boolean available(ShellState state) {
			return state.isLogged();
		}
	},
	CLOSE_SESSION(_("close"), _("session")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			if (state.isLogged()) {
				p11.logout();
				state.setLogged(false);
			}
			p11.closeSession();
			state.setInSession(false);
		}

		@Override
		public boolean available(ShellState state) {
			return state.isInSession();
		}
	},
	CLOSE_ALL_SESSION(_("close"), _("all"), _("session")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			if (state.isLogged()) {
				p11.logout();
				state.setLogged(false);
			}
			p11.closeAllSession(slot);
			state.setInSession(false);
		}

		@Override
		public boolean available(ShellState state) {
			return state.getSlot() != -1;
		}
	},
	FIND_OBJECTS(_("find"), _("objects")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			for (P11Object o : p11.findObjects(state.getAttributes()))
				System.out.println(o);
		}

		@Override
		public boolean available(ShellState state) {
			return state.isLogged();
		}
	},
	GET_ATTRIBUTE(_("get"), _("attribute"), new IntegerToken()) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws NumberFormatException, P11Exception {
			for (Attribute attribute : state.getAttributes()) {
				p11.getAttribute(new P11Object((Integer)args[0]), attribute);
				System.out.println(attribute);
			}
		}

		@Override
		public boolean available(ShellState state) {
			return state.isLogged();
		}
	},
	SET_ATTRIBUTE(_("set"), _("attribute"), new IntegerToken()) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws NumberFormatException, P11Exception {
			for (Attribute attribute : state.getAttributes())
				p11.setAttribute(new P11Object((Integer)args[0]), attribute);
		}

		@Override
		public boolean available(ShellState state) {
			return state.isLogged();
		}
	},
	WRAP(_("wrap"), new MechanismToken(), new IntegerToken(), new IntegerToken(), new IntegerToken()) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			p11.wrap((Mechanism)args[0],
					new P11Object((Integer)args[1]),
					new P11Object((Integer)args[2]),
					(Integer)args[3]);
		}

		@Override
		public boolean available(ShellState state) {
			System.out.println("wrap is available");
			return true; //state.isLogged();
		}
	},
	GENERATE_KEY(_("generate"), new MechanismToken()) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception {
			p11.generateKey((Mechanism)args[0], state.getAttributes());
		}

		@Override
		public boolean available(ShellState state) {
			return state.isLogged();
		}
	},
	SELECT_SLOT(_("select"), _("slot"), new IntegerToken()) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) {
			state.setSlot((Integer)args[0]);			
		}

		@Override
		public boolean available(ShellState state) {
			return true;
		}
	},
	TEMPLATE_ADD(_("template"), _("add"), new AttributeToken()) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) {
			state.addAttribute((Attribute)args[1]);
		}

		@Override
		public boolean available(ShellState state) {
			return true;
		}
	},
	TEMPLATE_REMOVE(_("template"), _("remove"), new EnumToken<AttributeType>(AttributeType.class)) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) {
			state.removeAttribute((AttributeType)args[0]);
		}

		@Override
		public boolean available(ShellState state) {
			return false;
		}
	},
	TEMPLATE_CLEAR(_("template"), _("clear")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) {
			state.clearAttribute();
		}

		@Override
		public boolean available(ShellState state) {
			return false;
		}
	},
	TEMPLATE_LIST(_("template"), _("list")) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot, Object[] args) {
			for (Attribute attribute : state.getAttributes())
				System.out.println(attribute);
		}

		@Override
		public boolean available(ShellState state) {
			return false;
		}
	},
	GET_ALL_ATTRIBUTES(_("get"), _("all"), _("attributes"), new IntegerToken()) {
		@Override
		public void execute(ShellState state, PKCS11 p11, int slot,
				Object[] args) throws P11Exception, InvalidMechanismException,
				IOException {
			P11Object obj = new P11Object((Integer)args[0]);
			for (AttributeType at : AttributeType.values()) {
				int size = at.getDefaultSize();
				Attribute attribute = at.getAttribute();
				while (true) {
					try {
						p11.getAttribute(obj, attribute);
						System.out.println(attribute);
					} catch (P11Exception e) {
						if (e.getCode() == Defs.CKR_ATTRIBUTE_SENSITIVE)
							System.out.println(attribute.attributeType() + " is sensitive");
						else if (e.getCode() == Defs.CKR_BUFFER_TOO_SMALL) {
							size = 2 * size;
							attribute = at.getAttribute(size);
							continue;
						}
						else if (e.getCode() != Defs.CKR_ATTRIBUTE_TYPE_INVALID)
							System.out.println(attribute.attributeType() + " " + e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				} 
			}
			
		}

		@Override
		public boolean available(ShellState state) {
			return state.isInSession();
		}
	};

	private Token[] tokens;
	
	private P11Commands(Token ... tokens) {
		this.tokens = tokens;
	}
	
	public Token[] getTokens() {
		return tokens;
	}

	static private Token _(String name) {
		return new StaticToken(name);
	}
	
	public abstract void execute(ShellState state, PKCS11 p11, int slot, Object[] args) throws P11Exception, InvalidMechanismException, IOException;
	
	@Override
	public void execute(ShellState state, Object[] args) throws Exception {
		execute(state, state.getPKCS11(), state.getSlot(), args);
	}
}
