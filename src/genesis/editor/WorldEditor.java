package genesis.editor;

import javagames.util.StateFramework;

public class WorldEditor extends StateFramework 
{

	protected static String[] params;
	
	public WorldEditor()
	{
		super();
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
		processParams();
	}
	
	public static void main(String[] args) 
	{
		params = args;
		launchApp(new WorldEditor());
	}

	protected void processParams()
	{
		if(params.length>0)
		{
			System.out.println(params[0]);
		}
		else
			controller.setState(new EditorState());
	}
	
}
