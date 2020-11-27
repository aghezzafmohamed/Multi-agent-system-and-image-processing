package Filter1;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class AgentFilter1 extends Agent {
	protected void setup() {
		System.out.println("Création de l'agent : "+this.getAID().getName());
		addBehaviour(new CyclicBehaviour(){
			@Override
			public void action() {
				// TODO Auto-generated method stub 
				ACLMessage aclMessage = receive();
				if(aclMessage!=null) {
					String file = aclMessage.getContent();
	    	       		 BufferedImage img = null;
	    	 	        try {
	    	 				img = ImageIO.read(new File(file));
	    	 			} catch (IOException e) {
	    	 				// TODO Auto-generated catch block
	    	 				e.printStackTrace();
	    	 			}
	    	 			BufferedImage imageGris = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);    	 			
	    	 			Graphics2D surfaceImg = imageGris.createGraphics();
	    	 			surfaceImg.drawImage(img, null, null);	      
	    	 			img = imageGris;
	    	 			ImageIcon icon = new ImageIcon(img);
	    	 			try {
	    	 				ImageIO.write(imageGris, "jpg",new File("imgFilter1.jpg"));    	 		        
	  					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    	 			
	    	 			//send to master

						ACLMessage reply=aclMessage.createReply();
						reply.setPerformative(ACLMessage.PROPOSE);
						
						ACLMessage msg3=new ACLMessage(ACLMessage.INFORM);
						msg3.setOntology("Filter1");
						msg3.addReceiver(new AID("AgentMaster",AID.ISLOCALNAME));
						msg3.setContent("imgFilter1.jpg");
						myAgent.send(msg3);
												
				}
				else block();
			}
		});
	}
}
