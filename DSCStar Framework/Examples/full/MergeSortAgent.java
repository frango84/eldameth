package full;

import original.ObjectForMergeSort;
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

public class MergeSortAgent extends Agent {
	private DSCStarBehaviour order;
	private DSCStarBehaviour mergeSort;
	private DSCStarBehaviour main;

	protected void setup() {
		try {
			getContentManager().registerLanguage(new SLCodec(),
					FIPANames.ContentLanguage.FIPA_SL0);
			getContentManager()
					.registerOntology(MobilityOntology.getInstance());
			order = new DSCStarBehaviour(this, "ORDER", MessageTemplate
					.MatchLanguage("DSC_STAR"), false, new VarOrder());
			mergeSort = new DSCStarBehaviour(this, "MERGESORT", MessageTemplate
					.MatchLanguage("DSC_STAR"), false, new VarMergeSort());
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
			Behaviour s20 = new SimpleStateBehaviour(this, "S20");
			Behaviour s21 = new SimpleStateBehaviour(this, "S21");
			Behaviour s22 = new SimpleStateBehaviour(this, "S22");
			Behaviour s23 = new SimpleStateBehaviour(this, "S23");
			Behaviour s24 = new SimpleStateBehaviour(this, "S24");
			Behaviour s25 = new SimpleStateBehaviour(this, "S25");
			Behaviour s26 = new SimpleStateBehaviour(this, "S26");
			Behaviour s27 = new SimpleStateBehaviour(this, "S27");
			Behaviour s28 = new SimpleStateBehaviour(this, "S28");
			Behaviour s29 = new SimpleStateBehaviour(this, "S29");
			Behaviour s30 = new SimpleStateBehaviour(this, "S30");
			Behaviour s31 = new SimpleStateBehaviour(this, "S31");
			Behaviour s32 = new SimpleStateBehaviour(this, "S32");
			Behaviour s33 = new SimpleStateBehaviour(this, "S33");
			Behaviour s34 = new SimpleStateBehaviour(this, "S34");
			Behaviour s35 = new SimpleStateBehaviour(this, "S35");
			Behaviour s36 = new SimpleStateBehaviour(this, "S36");
			Behaviour s37 = new SimpleStateBehaviour(this, "S37");
			Behaviour s38 = new SimpleStateBehaviour(this, "S38");
			Behaviour s39 = new SimpleStateBehaviour(this, "S39");
			Behaviour s40 = new SimpleStateBehaviour(this, "S40");
			Behaviour s41 = new SimpleStateBehaviour(this, "S41");
			Behaviour s42 = new SimpleStateBehaviour(this, "S42");
			Behaviour s43 = new SimpleStateBehaviour(this, "S43");
			Behaviour s44 = new SimpleStateBehaviour(this, "S44");
			Behaviour s45 = new SimpleStateBehaviour(this, "S45");
			Behaviour s46 = new SimpleStateBehaviour(this, "S46");
			Behaviour s47 = new SimpleStateBehaviour(this, "S47");
			Behaviour s48 = new SimpleStateBehaviour(this, "S48");
			Behaviour s49 = new SimpleStateBehaviour(this, "S49");
			Behaviour s50 = new SimpleStateBehaviour(this, "S50");
			Behaviour s51 = new SimpleStateBehaviour(this, "S51");
			Behaviour s52 = new SimpleStateBehaviour(this, "S52");
			Behaviour s53 = new SimpleStateBehaviour(this, "S53");
			Behaviour s54 = new SimpleStateBehaviour(this, "S54");
			Behaviour s55 = new SimpleStateBehaviour(this, "S55");
			Behaviour s56 = new SimpleStateBehaviour(this, "S56");
			Behaviour s57 = new SimpleStateBehaviour(this, "S57");
			Behaviour s58 = new SimpleStateBehaviour(this, "S58");
			Behaviour s59 = new SimpleStateBehaviour(this, "S59");
			Behaviour s60 = new SimpleStateBehaviour(this, "S60");
			Behaviour s61 = new SimpleStateBehaviour(this, "S61");
			order.addInitialState(s1);
			order.addState(s2);
			order.addState(s3);
			order.addState(s4);
			order.addState(s5);
			order.addState(s6);
			order.addState(s7);
			order.addState(s8);
			mergeSort.addInitialState(s9);
			mergeSort.addState(s10);
			mergeSort.addState(s11);
			mergeSort.addState(s12);
			mergeSort.addState(s13);
			mergeSort.addState(s14);
			mergeSort.addState(s15);
			mergeSort.addState(s16);
			mergeSort.addState(s17);
			mergeSort.addState(s18);
			mergeSort.addState(s19);
			mergeSort.addState(s20);
			mergeSort.addState(s21);
			mergeSort.addState(s22);
			mergeSort.addState(s23);
			mergeSort.addState(s24);
			mergeSort.addState(s25);
			mergeSort.addState(s26);
			mergeSort.addState(s27);
			mergeSort.addState(s28);
			mergeSort.addState(s29);
			mergeSort.addState(s30);
			mergeSort.addState(s31);
			mergeSort.addState(s32);
			mergeSort.addState(s33);
			mergeSort.addState(s34);
			mergeSort.addState(s35);
			mergeSort.addState(s36);
			mergeSort.addState(s37);
			mergeSort.addState(s38);
			main.addInitialState(s39);
			main.addState(s40);
			main.addState(s41);
			main.addState(s42);
			main.addState(s43);
			main.addState(s44);
			main.addState(s45);
			main.addState(s46);
			main.addState(s47);
			main.addState(s48);
			main.addState(s49);
			main.addState(s50);
			main.addState(s51);
			main.addState(s52);
			main.addState(s53);
			main.addState(s54);
			main.addState(s55);
			main.addState(s56);
			main.addState(s57);
			main.addState(s58);
			main.addState(s59);
			main.addState(s60);
			main.addFinalState(s61);
			java.util.ArrayList<DSCStarBehaviour> otherStates = new java.util.ArrayList<DSCStarBehaviour>();
			otherStates.add(order);
			otherStates.add(mergeSort);
			DSCStarBehaviour root = DSCStarBehaviour.createRootForDSCStar(main,
					otherStates, new VarRoot());
			DSCStarNormalTransition t1 = new DSCStarNormalTransition("T1", s1,
					s2) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("CPE_ORDER"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					CPE_ORDER cpe = (CPE_ORDER) msg;
					varOrder.v = cpe.v;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					varOrder.half = varOrder.v.length / 2 + 1;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					varOrder.leftV = new Object[varOrder.half];
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t4 = new DSCStarNormalTransition("T4", s4,
					s5) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					varOrder.rightV = new Object[varOrder.half];
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							mergeSort);
					postEvent(call);
					CPE_MERGESORT cpe = new CPE_MERGESORT();
					cpe.v = varOrder.v;
					cpe.l = 0;
					cpe.h = varOrder.v.length - 1;
					cpe.leftV = varOrder.leftV;
					cpe.rightV = varOrder.rightV;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t6 = new DSCStarNormalTransition("T6", s6,
					s7) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("RPE_MERGESORT"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarStarTransition t7 = new DSCStarStarTransition(order,
					mergeSort);
			DSCStarNormalTransition t8 = new DSCStarNormalTransition("T8", s7,
					s8) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarOrder varOrder = (VarOrder) sourceVariablesAndParameters;
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, order);
					postEvent(returnFrom);
					DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE, order);
					postEvent(rpe);
				}
			};
			DSCStarNormalTransition t9 = new DSCStarNormalTransition("T9", s9,
					s10) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("CPE_MERGESORT"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					CPE_MERGESORT cpe = (CPE_MERGESORT) msg;
					varMergeSort.v = cpe.v;
					varMergeSort.l = cpe.l;
					varMergeSort.h = cpe.h;
					varMergeSort.leftV = cpe.leftV;
					varMergeSort.rightV = cpe.rightV;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t10 = new DSCStarNormalTransition("T10",
					s10, s11) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (varMergeSort.l >= varMergeSort.h) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t11 = new DSCStarNormalTransition("T11",
					s11, s12) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, mergeSort);
					postEvent(returnFrom);
					DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE,
							mergeSort);
					postEvent(rpe);
				}
			};
			DSCStarNormalTransition t12 = new DSCStarNormalTransition("T12",
					s10, s13) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(varMergeSort.l >= varMergeSort.h)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t13 = new DSCStarNormalTransition("T13",
					s13, s14) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.middle = (varMergeSort.l + varMergeSort.h) / 2;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t14 = new DSCStarNormalTransition("T14",
					s14, s15) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							mergeSort);
					postEvent(call);
					CPE_MERGESORT cpe = new CPE_MERGESORT();
					cpe.v = varMergeSort.v;
					cpe.l = varMergeSort.l;
					cpe.h = varMergeSort.middle;
					cpe.leftV = varMergeSort.leftV;
					cpe.rightV = varMergeSort.rightV;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t15 = new DSCStarNormalTransition("T15",
					s15, s16) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("RPE_MERGESORT"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarStarTransition t16 = new DSCStarStarTransition(mergeSort,
					mergeSort);
			DSCStarNormalTransition t17 = new DSCStarNormalTransition("T17",
					s16, s17) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							mergeSort);
					postEvent(call);
					CPE_MERGESORT cpe = new CPE_MERGESORT();
					cpe.v = varMergeSort.v;
					cpe.l = varMergeSort.middle + 1;
					cpe.h = varMergeSort.h;
					cpe.leftV = varMergeSort.leftV;
					cpe.rightV = varMergeSort.rightV;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t18 = new DSCStarNormalTransition("T18",
					s17, s18) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("RPE_MERGESORT"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.left = 0;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t20 = new DSCStarNormalTransition("T20",
					s19, s20) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.right = 0;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t21 = new DSCStarNormalTransition("T21",
					s20, s21) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.sizeL = varMergeSort.middle - varMergeSort.l
							+ 1;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t22 = new DSCStarNormalTransition("T22",
					s21, s22) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.sizeR = varMergeSort.h - varMergeSort.middle;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t23 = new DSCStarNormalTransition("T23",
					s22, s23) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					System.arraycopy(varMergeSort.v, varMergeSort.l,
							varMergeSort.leftV, 0, varMergeSort.sizeL);
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t24 = new DSCStarNormalTransition("T24",
					s23, s24) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					System.arraycopy(varMergeSort.v, varMergeSort.middle + 1,
							varMergeSort.rightV, 0, varMergeSort.sizeR);
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t25 = new DSCStarNormalTransition("T25",
					s24, s25) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if ((varMergeSort.left < varMergeSort.sizeL)
								&& (varMergeSort.right < varMergeSort.sizeR)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t26 = new DSCStarNormalTransition("T26",
					s25, s26) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (((Comparable) varMergeSort.leftV[varMergeSort.left])
								.compareTo(varMergeSort.rightV[varMergeSort.right]) <= 0) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t27 = new DSCStarNormalTransition("T27",
					s26, s27) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.leftV[varMergeSort.left++];
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t28 = new DSCStarNormalTransition("T28",
					s25, s28) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(((Comparable) varMergeSort.leftV[varMergeSort.left])
								.compareTo(varMergeSort.rightV[varMergeSort.right]) <= 0)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t29 = new DSCStarNormalTransition("T29",
					s28, s29) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.rightV[varMergeSort.right++];
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t30 = new DSCStarNormalTransition("T30",
					s27, s30) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t31 = new DSCStarNormalTransition("T31",
					s29, s30) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t32 = new DSCStarNormalTransition("T32",
					s30, s24) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t33 = new DSCStarNormalTransition("T33",
					s24, s31) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!((varMergeSort.left < varMergeSort.sizeL) && (varMergeSort.right < varMergeSort.sizeR))) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t34 = new DSCStarNormalTransition("T34",
					s31, s32) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (varMergeSort.left < varMergeSort.sizeL) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t35 = new DSCStarNormalTransition("T35",
					s32, s33) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.leftV[varMergeSort.left++];
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t36 = new DSCStarNormalTransition("T36",
					s33, s31) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t37 = new DSCStarNormalTransition("T37",
					s31, s34) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(varMergeSort.left < varMergeSort.sizeL)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t38 = new DSCStarNormalTransition("T38",
					s34, s35) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (varMergeSort.right < varMergeSort.sizeR) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t39 = new DSCStarNormalTransition("T39",
					s35, s36) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.rightV[varMergeSort.right++];
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t40 = new DSCStarNormalTransition("T40",
					s36, s34) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t41 = new DSCStarNormalTransition("T41",
					s34, s37) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(varMergeSort.right < varMergeSort.sizeR)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t42 = new DSCStarNormalTransition("T42",
					s37, s38) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMergeSort varMergeSort = (VarMergeSort) sourceVariablesAndParameters;
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, mergeSort);
					postEvent(returnFrom);
					DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE,
							mergeSort);
					postEvent(rpe);
				}
			};
			DSCStarNormalTransition t43 = new DSCStarNormalTransition("T43",
					s39, s40) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					CPE_MAIN cpe = (CPE_MAIN) msg;
					varMain.args = cpe.args;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t44 = new DSCStarNormalTransition("T44",
					s40, s41) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.result = "";
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t45 = new DSCStarNormalTransition("T45",
					s41, s42) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.v = new ObjectForMergeSort[50];
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t46 = new DSCStarNormalTransition("T46",
					s42, s43) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.forIndex = 0;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t47 = new DSCStarNormalTransition("T47",
					s43, s44) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (varMain.forIndex < varMain.v.length) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t48 = new DSCStarNormalTransition("T48",
					s44, s45) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.charIndex = (int) (Math.random() * 26);
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t49 = new DSCStarNormalTransition("T49",
					s45, s46) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.v[varMain.forIndex] = new ObjectForMergeSort(
							varRoot.chars[varMain.charIndex]);
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t50 = new DSCStarNormalTransition("T50",
					s46, s43) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.forIndex++;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t51 = new DSCStarNormalTransition("T51",
					s43, s47) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(varMain.forIndex < varMain.v.length)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t52 = new DSCStarNormalTransition("T52",
					s47, s48) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.result += "NOT ORDERED ARRAY:\n";
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t53 = new DSCStarNormalTransition("T53",
					s48, s49) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.forIndex = 0;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t54 = new DSCStarNormalTransition("T54",
					s49, s50) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (varMain.forIndex < varMain.v.length) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t55 = new DSCStarNormalTransition("T55",
					s50, s51) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.result += varMain.v[varMain.forIndex].name + " ";
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t56 = new DSCStarNormalTransition("T56",
					s51, s49) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.forIndex++;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t57 = new DSCStarNormalTransition("T57",
					s49, s52) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(varMain.forIndex < varMain.v.length)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t58 = new DSCStarNormalTransition("T58",
					s52, s53) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							order);
					postEvent(call);
					CPE_ORDER cpe = new CPE_ORDER();
					cpe.v = varMain.v;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t59 = new DSCStarNormalTransition("T59",
					s53, s54) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getName()
									.equalsIgnoreCase("RPE_ORDER"))) {
						return true;
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarStarTransition t60 = new DSCStarStarTransition(main, order);
			DSCStarNormalTransition t61 = new DSCStarNormalTransition("T61",
					s54, s55) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.result += "\nORDERED ARRAY:\n";
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t62 = new DSCStarNormalTransition("T62",
					s55, s56) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.forIndex = 0;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t63 = new DSCStarNormalTransition("T63",
					s56, s57) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (varMain.forIndex < varMain.v.length) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t64 = new DSCStarNormalTransition("T64",
					s57, s58) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.result += varMain.v[varMain.forIndex].name + " ";
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t65 = new DSCStarNormalTransition("T65",
					s58, s56) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					varMain.forIndex++;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t66 = new DSCStarNormalTransition("T66",
					s56, s59) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					if ((msg != null)
							&& (msg instanceof DSCStarEvent)
							&& (((DSCStarEvent) msg).getType() == DSCStarEvent.E)) {
						if (!(varMain.forIndex < varMain.v.length)) {
							return true;
						}
					}
					return false;
				}

				public void action(
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t67 = new DSCStarNormalTransition("T67",
					s59, s60) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
					VarMain varMain = (VarMain) sourceVariablesAndParameters;
					System.out.println(varMain.result);
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t68 = new DSCStarNormalTransition("T68",
					s60, s61) {
				public boolean trigger(
						Behaviour source,
						ACLMessage msg,
						DSCStarVariablesAndParameters sourceVariablesAndParameters,
						DSCStarVariablesAndParameters rootVariablesAndParameters) {
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
					VarRoot varRoot = (VarRoot) rootVariablesAndParameters;
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
			root.addTransition(t20);
			root.addTransition(t21);
			root.addTransition(t22);
			root.addTransition(t23);
			root.addTransition(t24);
			root.addTransition(t25);
			root.addTransition(t26);
			root.addTransition(t27);
			root.addTransition(t28);
			root.addTransition(t29);
			root.addTransition(t30);
			root.addTransition(t31);
			root.addTransition(t32);
			root.addTransition(t33);
			root.addTransition(t34);
			root.addTransition(t35);
			root.addTransition(t36);
			root.addTransition(t37);
			root.addTransition(t38);
			root.addTransition(t39);
			root.addTransition(t40);
			root.addTransition(t41);
			root.addTransition(t42);
			root.addTransition(t43);
			root.addTransition(t44);
			root.addTransition(t45);
			root.addTransition(t46);
			root.addTransition(t47);
			root.addTransition(t48);
			root.addTransition(t49);
			root.addTransition(t50);
			root.addTransition(t51);
			root.addTransition(t52);
			root.addTransition(t53);
			root.addTransition(t54);
			root.addTransition(t55);
			root.addTransition(t56);
			root.addTransition(t57);
			root.addTransition(t58);
			root.addTransition(t59);
			root.addTransition(t60);
			root.addTransition(t61);
			root.addTransition(t62);
			root.addTransition(t63);
			root.addTransition(t64);
			root.addTransition(t65);
			root.addTransition(t66);
			root.addTransition(t67);
			root.addTransition(t68);
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

	private class VarRoot extends DSCStarVariablesAndParameters {
		public String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z" };

		public VarRoot() {
		}
	}

	private class VarOrder extends DSCStarVariablesAndParameters {
		public Comparable[] v;
		public int half;
		public Object[] leftV;
		public Object[] rightV;

		public VarOrder() {
		}
	}

	private class VarMergeSort extends DSCStarVariablesAndParameters {
		public Comparable[] v;
		public int l;
		public int h;
		public Object[] leftV;
		public Object[] rightV;
		public int middle;
		public int left;
		public int right;
		public int sizeL;
		public int sizeR;

		public VarMergeSort() {
		}
	}

	private class VarMain extends DSCStarVariablesAndParameters {
		public String[] args;
		public ObjectForMergeSort[] v;
		public int forIndex;
		public int charIndex;
		public String result;

		public VarMain() {
		}
	}

	private class CPE_ORDER extends DSCStarEvent {
		public Comparable[] v;

		public CPE_ORDER() {
			super(DSCStarEvent.CPE, order);
		}
	}

	private class CPE_MERGESORT extends DSCStarEvent {
		public Comparable[] v;
		public int l;
		public int h;
		public Object[] leftV;
		public Object[] rightV;

		public CPE_MERGESORT() {
			super(DSCStarEvent.CPE, mergeSort);
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
