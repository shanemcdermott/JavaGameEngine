package kobolds;


import javagames.state.StateController;
import javagames.util.GameConstants;
import javagames.util.StateFramework;

public class KoboldsGame extends StateFramework {

	public KoboldsGame()
	{
		super();
		appTitle = "Kobolds!";
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		controller.setState(new KoboldSimState());
	}
	
	public static void main(String[] args) 
	{
		launchApp(new KoboldsGame());
	}

}
