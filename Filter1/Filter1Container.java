package Filter1;


import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;

public class Filter1Container {

	public static void main(String[] args) {
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImp = new ProfileImpl(false);
			profileImp.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer agentContainer = runtime.createAgentContainer(profileImp);
			AgentController agentController = agentContainer.createNewAgent("AgentFilter1", "Filter1.AgentFilter1", new Object[] {});
			agentController.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
