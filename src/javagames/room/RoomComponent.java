package javagames.room;

/*Room Components extend the functionality of the room they are attached to. */

public abstract class RoomComponent 
{
	protected GameRoom room;
	
	public RoomComponent(GameRoom room)
	{
		this.room = room;
	}

	public GameRoom getRoom() {
		return room;
	}

	public void setRoom(GameRoom room) {
		this.room = room;
	}
	
}
