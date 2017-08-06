package javagames.world;

import java.util.Map;

public interface HasIngredients 
{
	public Ingredient[] getIngredientTypes();
	public int getIngredientCount(Ingredient ingredient);
	public void getIngredients(Ingredient[] res, int[] qty);
	public void addIngredients(Ingredient[] res, int[] qty);

}
