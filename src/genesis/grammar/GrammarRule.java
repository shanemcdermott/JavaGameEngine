package genesis.grammar;

import java.util.Map;

public interface GrammarRule 
{
	
	public void init(Map<String,Object> params);
	
	public boolean canEnter();
	
	public void iterate(int n);
	
	public void execute();

	public boolean isComplete();
	public boolean isFailed();
	
	public Map<String,Object> getResults();
	
}
