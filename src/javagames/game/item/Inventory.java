package javagames.game.item;

import java.util.Map;

public interface Inventory 
{
	/**
	 * Returns the inventory's quantity as a map
	 * 
	 */
	public Map<Item, Integer> toMap();
	
	public Item[] GetItems();
	
	/**
	 * returns the quantity of the specified item in the inventory.
	 */
	public int getQuantity(Item item);
	/**
	 * Adds the specified number of items to the inventory
	 * @param item Type of Item to add.
	 * @param qty Quantity of specified item to add.
	 * @return Total number of item in inventory now, or -1 if failure.
	 */
	public int add(Item item, int qty);
	
	/**
	 * Removes the specified quantity of item from inventory.
	 * @param item Item to remove.
	 * @param qty Quantity to attempt remove
	 * @return Quantity that was actually removed.
	 */
	public int remove(Item item, int qty);
}
