package ksb;

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
			order.addInitialState(s1);
			order.addState(s2);
			order.addState(s3);
			order.addState(s4);
			order.addState(s5);
			mergeSort.addInitialState(s6);
			mergeSort.addState(s7);
			mergeSort.addState(s8);
			mergeSort.addState(s9);
			mergeSort.addState(s10);
			mergeSort.addState(s11);
			mergeSort.addState(s12);
			mergeSort.addState(s13);
			mergeSort.addState(s14);
			mergeSort.addState(s15);
			mergeSort.addState(s16);
			main.addInitialState(s17);
			main.addState(s18);
			main.addState(s19);
			main.addState(s20);
			main.addFinalState(s21);
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
					varOrder.leftV = new Object[varOrder.half];
					varOrder.rightV = new Object[varOrder.half];
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
			DSCStarStarTransition t4 = new DSCStarStarTransition(order,
					mergeSort);
			DSCStarNormalTransition t5 = new DSCStarNormalTransition("T5", s4,
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
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, order);
					postEvent(returnFrom);
					DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE, order);
					postEvent(rpe);
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
			DSCStarNormalTransition t7 = new DSCStarNormalTransition("T7", s7,
					s8) {
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
			DSCStarNormalTransition t8 = new DSCStarNormalTransition("T8", s8,
					s9) {
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
			DSCStarNormalTransition t9 = new DSCStarNormalTransition("T9", s7,
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
			DSCStarStarTransition t12 = new DSCStarStarTransition(mergeSort,
					mergeSort);
			DSCStarNormalTransition t13 = new DSCStarNormalTransition("T13",
					s12, s13) {
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
			DSCStarNormalTransition t14 = new DSCStarNormalTransition("T14",
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
			DSCStarNormalTransition t15 = new DSCStarNormalTransition("T15",
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
					varMergeSort.left = 0;
					varMergeSort.right = 0;
					varMergeSort.sizeL = varMergeSort.middle - varMergeSort.l
							+ 1;
					varMergeSort.sizeR = varMergeSort.h - varMergeSort.middle;
					System.arraycopy(varMergeSort.v, varMergeSort.l,
							varMergeSort.leftV, 0, varMergeSort.sizeL);
					System.arraycopy(varMergeSort.v, varMergeSort.middle + 1,
							varMergeSort.rightV, 0, varMergeSort.sizeR);
					move();
					DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);
					postEvent(e);
				}
			};
			DSCStarNormalTransition t16 = new DSCStarNormalTransition("T16",
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
					while ((varMergeSort.left < varMergeSort.sizeL)
							&& (varMergeSort.right < varMergeSort.sizeR)) {
						if (((Comparable) varMergeSort.leftV[varMergeSort.left])
								.compareTo(varMergeSort.rightV[varMergeSort.right]) <= 0)
							varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.leftV[varMergeSort.left++];
						else
							varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.rightV[varMergeSort.right++];
					}
					while (varMergeSort.left < varMergeSort.sizeL)
						varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.leftV[varMergeSort.left++];
					while (varMergeSort.right < varMergeSort.sizeR)
						varMergeSort.v[varMergeSort.l++] = (Comparable) varMergeSort.rightV[varMergeSort.right++];
					DSCStarEvent returnFrom = new DSCStarEvent(
							DSCStarEvent.RETURN_FROM, mergeSort);
					postEvent(returnFrom);
					DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE,
							mergeSort);
					postEvent(rpe);
				}
			};
			DSCStarNormalTransition t17 = new DSCStarNormalTransition("T17",
					s17, s18) {
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
			DSCStarNormalTransition t18 = new DSCStarNormalTransition("T18",
					s18, s19) {
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
					varMain.v = new ObjectForMergeSort[50];
					for (varMain.forIndex = 0; varMain.forIndex < varMain.v.length; varMain.forIndex++) {
						varMain.charIndex = (int) (Math.random() * 26);
						varMain.v[varMain.forIndex] = new ObjectForMergeSort(
								varRoot.chars[varMain.charIndex]);
					}
					varMain.result += "NOT ORDERED ARRAY:\n";
					for (varMain.forIndex = 0; varMain.forIndex < varMain.v.length; varMain.forIndex++) {
						varMain.result += varMain.v[varMain.forIndex].name
								+ " ";
					}
					DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL,
							order);
					postEvent(call);
					CPE_ORDER cpe = new CPE_ORDER();
					cpe.v = varMain.v;
					postEvent(cpe);
				}
			};
			DSCStarNormalTransition t19 = new DSCStarNormalTransition("T19",
					s19, s20) {
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
			DSCStarStarTransition t20 = new DSCStarStarTransition(main, order);
			DSCStarNormalTransition t21 = new DSCStarNormalTransition("T21",
					s20, s21) {
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
					for (varMain.forIndex = 0; varMain.forIndex < varMain.v.length; varMain.forIndex++) {
						varMain.result += varMain.v[varMain.forIndex].name
								+ " ";
					}
					System.out.println(varMain.result);
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
