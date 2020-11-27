package Master;



import java.util.Map;

import javax.swing.UIManager;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class AgentMaster extends GuiAgent {
	private MasterGUI gui;
	protected void setup() {
	      try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	               /* if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }*/
	            	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
		System.out.println("Création de l'agent : "+this.getAID().getName());
		gui = new MasterGUI();
		gui.setVisible(true);
		
		gui.setContainerAgent(this);
		addBehaviour(new CyclicBehaviour(){
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage aclMessage = receive();
				if(aclMessage!=null) {
					if(aclMessage.getOntology().equals("Filter1")) {
					gui.showMessage(aclMessage.getContent(),1);
					}
					if(aclMessage.getOntology().equals("Filter2")) {
					gui.showMessage(aclMessage.getContent(),2);
					}
					if(aclMessage.getOntology().equals("Filter3")) {
					gui.showMessage(aclMessage.getContent(),3);
					}
				}
				else block();
			}
			
		});
	}
	@Override
	protected void onGuiEvent(GuiEvent ev) {
		// TODO Auto-generated method stub
		switch(ev.getType()) {
		case 1:
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			
			Map<String, Object> params = (Map<String, Object>) ev.getParameter(0);
			String message=(String)params.get("file");
			aclMessage.addReceiver(new AID("AgentFilter1", AID.ISLOCALNAME));
			aclMessage.addReceiver(new AID("AgentFilter2", AID.ISLOCALNAME));
			aclMessage.addReceiver(new AID("AgentFilter3", AID.ISLOCALNAME));
			aclMessage.setContent(message);
			send(aclMessage);
			break;
		default:
			break;
		}
	}
}
