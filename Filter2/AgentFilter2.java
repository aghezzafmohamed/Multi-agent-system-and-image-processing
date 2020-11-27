package Filter2;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
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

public class AgentFilter2 extends Agent {
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
   	 	        
	   	 	    BufferedImage imageX = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	   			float[ ] masqueSoblX = 
	   			{
	   					-1f, 0f, 1f,
	   					-2f, 0f, 2f,
	   					-1f, 0f, 1f
	   			};
	   	       
	   			Kernel masqueX = new Kernel(3, 3, masqueSoblX);
	   			ConvolveOp opération = new ConvolveOp(masqueX);
	   			opération.filter(img, imageX);
	   			
	   			BufferedImage imageY = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	   			float[ ] masqueSoblY = 
	   			{
	   				    -1f, -2f, 1f,
	   					0f, 0f, 0f,
	   					1f, 2f, 1f
	   			};
	   	       
	   			Kernel masqueY = new Kernel(3, 3, masqueSoblY);
	   			ConvolveOp opération1 = new ConvolveOp(masqueY);
	   			opération1.filter(img, imageY);
	   			
	   			img = imageX;   	 	        
   	 	        
   	 			try {
   	 				ImageIO.write(img, "jpg",new File("imgFilter2.jpg"));    	 		        
 					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
   	 			
   	 			    //Send to master

					ACLMessage reply=aclMessage.createReply();
					reply.setPerformative(ACLMessage.PROPOSE);
					
					ACLMessage msg3=new ACLMessage(ACLMessage.INFORM);
					msg3.setOntology("Filter2");
					msg3.addReceiver(new AID("AgentMaster",AID.ISLOCALNAME));
					msg3.setContent("imgFilter2.jpg");
					myAgent.send(msg3);
				}
				else block();
			}
		});
	}
}
