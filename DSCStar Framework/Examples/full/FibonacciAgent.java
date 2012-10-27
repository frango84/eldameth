package full;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;
import jade.content.onto.*;
import jade.content.onto.basic.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.JADEAgentManagement.*;
import jade.domain.mobility.*;
import jade.wrapper.*;

public class FibonacciAgent extends Agent {
	private DSCStarBehaviour fibonacci;
	private DSCStarBehaviour main;

	protected void setup() {
		try {
			getContentManager().registerLanguage(new SLCodec(),
					FIPANames.ContentLanguage.FIPA_SL0);
			getContentManager()
					.registerOntology(MobilityOntology.getInstance());
			fibonacci = new DSCStarBehaviour(this, "FIBONACCI", MessageTemplate
					.MatchLanguage("DSC_STAR"), false, new VarFibonacci());
			main = new DSCStarBehaviour(this, "MAIN", MessageTemplate
					.MatchLanguage("DSC_STAR"), false, new VarMain());
			Behaviour s1 = new SimpleStateBehaviour(this, "S1");
			Behaviour s2 = new SimpleStateBehaviour(this, "S2");
			Behaviour s3 = new SimpleStateBehaviour(this, "S3");
			Behaviour s4 = new SimpleStateBehaviour(this, "S4");
			Behaviour s5 = new SimpleStateBehaviour(this, "S5");
			Behaviour s6 = new SimpleStateBehaviour(this, "S6");
			Behaviour s7 = new SimpleStateBehaviour(this, "S7");
			Behaviour s8 = new SimpleStateBehaviour(this, "S8");
			Behaviour s9 = new SimpleStateBehaviour(this, "S9");
			Behaviour s10 = new SimpleStateBehaviour(this, "S10");
			Behaviour s11 = new SimpleStateBehaviour(this, "S11");
			Behaviour s12 = new SimpleStateBehaviour(this, "S12");
			Behaviour s13 = new SimpleStateBehaviour(this, "S13");
			Behaviour s14 = new SimpleStateBehaviour(this, "S14");
			Behaviour s15 = new SimpleStateBehaviour(this, "S15");
			Behaviour s16 = new SimpleStateBehaviour(this, "S16");
			Behaviour s17 = new SimpleStateBehaviour(this, "S17");
			Behaviour s18 = new SimpleStateBehaviour(this, "S18");
			Behaviour s19 = new SimpleStateBehaviour(this, "S19");
			fibonacci.addInitialState(s1);
			fibonacci.addState(s2);
			fibonacci.addState(s3);
			fibonacci.addState(s4);
			fibonacci.addState(s5);
			fibonacci.addState(s6);
			fibonacci.addState(s7);
			fibonacci.addState(s8);
			fibonacci.addState(s9);
			fibonacci.addState(s10);
			fibonacci.addState(s11);
			fibonacci.addState(s12);
			fibonacci.addState(s13);
			main.addInitialState(s14);
			main.addState(s15);
			main.addState(s16);
			main.addState(s17);
			main.addState(s18);
			main.addFinalState(s19);
			java.util.ArrayList<DSCStarBehaviour> otherStates = new java.util.ArrayList<DSCStarBehaviour>();
			otherStates.add(fibonacci);
			DSCStarBehaviour root = DSCStarBehaviour.createRootForDSCStar(main,
					otherStates, null);
			DSCStarNormalTransition t1 = new DSCStarNormalTransition("T1", s1,
					s2) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("CPE_FIBONACCI"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					CPE_FIBONACCI cpe = (CPE_FIBONACCI) msg;
					varFibonacci.n = cpe.n;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t2 = new DSCStarNormalTransition("T2", s2,
					s3) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (varFibonacci.n == 0 || varFibonacci.n == 1) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t3 = new DSCStarNormalTransition("T3", s3,
					s4) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, fibonacci);
					postEvent(returnFrom);
					RPE_FIBONACCI rpe = new RPE_FIBONACCI();
					rpe.result = varFibonacci.n;
					postEvent(rpe);
				}
			};
			DSCStarNormalTransition t4 = new DSCStarNormalTransition("T4", s2,
					s5) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(varFibonacci.n == 0 || varFibonacci.n == 1)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t5 = new DSCStarNormalTransition("T5", s5,
					s6) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					varFibonacci.x = varFibonacci.n - 1;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t6 = new DSCStarNormalTransition("T6", s6,
					s7) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							fibonacci);
					postEvent(call);
					CPE_FIBONACCI cpe = new CPE_FIBONACCI();
					cpe.n = varFibonacci.x;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t7 = new DSCStarNormalTransition("T7", s7,
					s8) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("RPE_FIBONACCI"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					RPE_FIBONACCI rpe = (RPE_FIBONACCI) msg;
					varFibonacci.res1 = rpe.result;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarStarTransition t8 = new DSCStarStarTransition(fibonacci,
					fibonacci);
			DSCStarNormalTransition t9 = new DSCStarNormalTransition("T9", s8,
					s9) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					varFibonacci.y = varFibonacci.n - 2;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t10 = new DSCStarNormalTransition("T10",
					s9, s10) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							fibonacci);
					postEvent(call);
					CPE_FIBONACCI cpe = new CPE_FIBONACCI();
					cpe.n = varFibonacci.y;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t11 = new DSCStarNormalTransition("T11",
					s10, s11) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("RPE_FIBONACCI"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					RPE_FIBONACCI rpe = (RPE_FIBONACCI) msg;
					varFibonacci.res2 = rpe.result;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t12 = new DSCStarNormalTransition("T12",
					s11, s12) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					varFibonacci.res = varFibonacci.res1 + varFibonacci.res2;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t13 = new DSCStarNormalTransition("T13",
					s12, s13) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarFibonacci varFibonacci = (VarFibonacci) sourceVariablesAndParameters;
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, fibonacci);
					postEvent(returnFrom);
					RPE_FIBONACCI rpe = new RPE_FIBONACCI();
					rpe.result = varFibonacci.res;
					postEvent(rpe);
				}
			};
			DSCStarNormalTransition t14 = new DSCStarNormalTransition("T14",
					s14, s15) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("CPE_MAIN"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					CPE_MAIN cpe = (CPE_MAIN) msg;
					varMain.args = cpe.args;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t15 = new DSCStarNormalTransition("T15",
					s15, s16) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							fibonacci);
					postEvent(call);
					CPE_FIBONACCI cpe = new CPE_FIBONACCI();
					cpe.n = 10;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t16 = new DSCStarNormalTransition("T16",
					s16, s17) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("RPE_FIBONACCI"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					RPE_FIBONACCI rpe = (RPE_FIBONACCI) msg;
					varMain.result = rpe.result;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarStarTransition t17 = new DSCStarStarTransition(main,
					fibonacci);
			DSCStarNormalTransition t18 = new DSCStarNormalTransition("T18",
					s17, s18) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					System.out.println("RESULT = " + varMain.result);
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t19 = new DSCStarNormalTransition("T19",
					s18, s19) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, main);
					postEvent(returnFrom);
					DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE, main);
					postEvent(rpe);
				}
			};
			root.addTransition(t1);
			root.addTransition(t2);
			root.addTransition(t3);
			root.addTransition(t4);
			root.addTransition(t5);
			root.addTransition(t6);
			root.addTransition(t7);
			root.addTransition(t8);
			root.addTransition(t9);
			root.addTransition(t10);
			root.addTransition(t11);
			root.addTransition(t12);
			root.addTransition(t13);
			root.addTransition(t14);
			root.addTransition(t15);
			root.addTransition(t16);
			root.addTransition(t17);
			root.addTransition(t18);
			root.addTransition(t19);
			addBehaviour(root);
			Object args[] = getArguments();
			CPE_MAIN cpeMain = new CPE_MAIN();
			cpeMain.args = (String[]) args[0];
			postEvent(cpeMain);
		} catch (Exception e) {
			e.printStackTrace();
			doDelete();
		}
	}

	protected void afterMove() {
		getContentManager().registerLanguage(new SLCodec(),
				FIPANames.ContentLanguage.FIPA_SL0);
		getContentManager().registerOntology(MobilityOntology.getInstance());
	}

	protected void afterClone() {
		getContentManager().registerLanguage(new SLCodec(),
				FIPANames.ContentLanguage.FIPA_SL0);
		getContentManager().registerOntology(MobilityOntology.getInstance());
	}

	public void postEvent(DSCStarEvent msg) {
		msg.setLanguage("DSC_STAR");
		postMessage(msg);
	}

	private void move() {
	}

	private void checkpoint() {
	}

	private void persistence() {
	}

	private class VarFibonacci extends DSCStarVariablesAndParameters {
		public int n;
		public int x;
		public int res1;
		public int y;
		public int res2;
		public int res;

		public VarFibonacci() {
		}
	}

	private class VarMain extends DSCStarVariablesAndParameters {
		public String[] args;
		public int result;

		public VarMain() {
		}
	}

	private class CPE_FIBONACCI extends DSCStarEvent {
		public int n;

		public CPE_FIBONACCI() {
			super(DSCStarEvent.CPE, fibonacci);
		}
	}

	private class RPE_FIBONACCI extends DSCStarEvent {
		public int result;

		public RPE_FIBONACCI() {
			super(DSCStarEvent.RPE, fibonacci);
		}
	}

	private class CPE_MAIN extends DSCStarEvent {
		public String[] args;

		public CPE_MAIN() {
			super(DSCStarEvent.CPE, main);
		}
	}

	private class SimpleStateBehaviour extends Behaviour {
		public SimpleStateBehaviour(Agent anAgent, String aName) {
			super(anAgent);
			this.myAgent = anAgent;
			setBehaviourName(aName);
		}

		public void action() {
		}

		public boolean done() {
			return true;
		}
	}
}
