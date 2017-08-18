package javagames.game;

import javagames.world.HasIngredients;
import javagames.world.Ingredient;
import javagames.world.IngredientObject;

public class Construct extends SpriteObject implements HasIngredients, Destroyable
{
	private IngredientObject ingredients;
	
	public Construct(IngredientObject resources) 
	{
		super();
		this.ingredients = resources;
		setName("Construct");
	}

	public Construct(Construct toCopy)
	{
		super(toCopy);
		this.ingredients = toCopy.getIngredients();
	}
	
	@Override
	public Ingredient[] getIngredientTypes() 
	{
		return ingredients.getIngredientTypes();
	}

	@Override
	public int getIngredientCount(Ingredient ingredient) 
	{
		return ingredients.getIngredientCount(ingredient);
	}

	@Override
	public void destroy(GameObject source)
	{
		if(source instanceof HasIngredients)
		{
			HasIngredients r = (HasIngredients)source;
			Ingredient[] res = getIngredientTypes();
			int[] qty = new int[res.length];
			for(int i =0; i< res.length;i++)
				qty[i]= getIngredientCount(res[i]);
			
			r.addIngredients(res,qty);
		}
	}

	@Override
	public void addIngredients(Ingredient[] res, int[] qty) 
	{
		this.ingredients.addIngredients(res,qty);
	}

	@Override
	public void getIngredients(Ingredient[] res, int[] qty) 
	{
		this.ingredients.getIngredients(res, qty);
	}

	public IngredientObject getIngredients()
	{
		return ingredients;
	}
}
