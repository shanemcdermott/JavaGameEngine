package javagames.world;

import java.util.Map;
import java.util.Set;

public class IngredientObject implements HasIngredients
{

	private Map<Ingredient,Integer> ingredients;
	
	public IngredientObject(Map<Ingredient, Integer> ingredients) 
	{
		this.ingredients=ingredients;
	}

	@Override
	public Ingredient[] getIngredientTypes() 
	{
		return ingredients.keySet().toArray(null);
	}
	
	@Override
	public int getIngredientCount(Ingredient ingredient) 
	{
		return ingredients.get(ingredient);
	}

	@Override
	public void addIngredients(Ingredient[] res, int[] qty) 
	{
		for(int i = 0; i < res.length; i++)
		{
			if(ingredients.containsKey(res[i]))
			{
				this.ingredients.put(res[i], qty[i] + ingredients.get(res[i]));
			}
			else
				this.ingredients.put(res[i], qty[i]);
		}
	}

	@Override
	public void getIngredients(Ingredient[] res, int[] qty) 
	{
		if(res.length != ingredients.size() || qty.length != res.length) return;
		
		Ingredient[] ing = getIngredientTypes();
		for(int i =0; i < ing.length;i++)
		{
			res[i] = ing[i];
			qty[i] = getIngredientCount(res[i]);
		}
		
	}

}
