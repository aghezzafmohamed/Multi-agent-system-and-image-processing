package Filter3;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class AgentFilter3 extends Agent {
	protected void setup() {
		System.out.println("Création de l'agent : "+this.getAID().getName());
		addBehaviour(new CyclicBehaviour(){
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage aclMessage = receive();
				if(aclMessage!=null) {
					String file = aclMessage.getContent();
	   	       		BufferedImage monImage = null;
	   	 	        try {
	   	 	        	monImage = ImageIO.read(new File(file));
	   	 			} catch (IOException e) {
	   	 				// TODO Auto-generated catch block
	   	 				e.printStackTrace();
	   	 			}					
					try {	

						int [][] pixel= new int[monImage.getWidth()][monImage.getHeight()];
			  	  		int x,y,g;
			  				  	
			  	//Conversion en Gris
				for (int i = 0; i < monImage.getWidth(); i++) {
					for (int j = 0; j < monImage.getHeight(); j++) {
	
					Color pixelcolor= new Color(monImage.getRGB(i,j));	
					int r=pixelcolor.getRed();
					int gb=pixelcolor.getGreen();
					int b=pixelcolor.getBlue();
					
					int hy=(r+gb+b)/3;	  
						
					int rgb=new Color(hy,hy,hy).getRGB();
					
					// changer la couleur de pixel avec la nouvelle couleur inversée
					monImage.setRGB(i, j, rgb);

					}
				}	

					// parcourir les pixels de l'image
				for (int i = 0; i < monImage.getWidth(); i++) 
				{
					for (int j = 0; j < monImage.getHeight(); j++) 
					{
					// recuperer couleur de chaque pixel
					Color pixelcolor= new Color(monImage.getRGB(i, j));
					
					// recuperer les valeur rgb (rouge ,vert ,bleu) de cette couleur
					 pixel[i][j]=monImage.getRGB(i, j);
				
					}
			    }

				for (int i = 1; i < monImage.getWidth()-2; i++) 
				{
					for (int j = 1; j < monImage.getHeight()-2; j++) 
					{
														
						x=-pixel[i][j]+pixel[i][j+2];
						y=pixel[i][j]-pixel[i+2][j];            
						g=Math.abs(x)+Math.abs(y);	
						pixel[i][j]=g;
				}
			    }

					for (int i = 0; i < monImage.getWidth(); i++) {
					for (int j = 0; j < monImage.getHeight(); j++) {
				
					Color pixelcolor= new Color(pixel[i][j]);
				
					int r=pixelcolor.getRed();
					int gb=pixelcolor.getGreen();
					int b=pixelcolor.getBlue();		
					int rgb=new Color(r,gb,b).getRGB();
					
					// changer la couleur de pixel avec la nouvelle couleur inversée
					monImage.setRGB(i, j, rgb);

					}
				}	
					// enregistrement d'imag		
	   	 			try {
	   	 				ImageIO.write(monImage, "jpg",new File("imgFilter3.jpg"));    	 		        
	 					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	   	 			
	   	 			    //Send to master

						ACLMessage reply=aclMessage.createReply();
						reply.setPerformative(ACLMessage.PROPOSE);
						
						ACLMessage msg3=new ACLMessage(ACLMessage.INFORM);
						msg3.setOntology("Filter3");
						msg3.addReceiver(new AID("AgentMaster",AID.ISLOCALNAME));
						msg3.setContent("imgFilter3.jpg");
						myAgent.send(msg3);
				}
				 catch (Exception e) {
					System.err.println("erreur -> "+e.getMessage());
				}
				}
				else block();
			}
		});
	}
}
