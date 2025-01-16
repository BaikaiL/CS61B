package core;

public class Room {
	//分别为房间的宽，高，起始X，和起始Y
	private int roomWidth;
	private int roomHeight;
	private int roomX;
	private int roomY;

	public Room(int roomX, int roomY, int roomWidth, int roomHeight) {
		this.roomWidth = roomWidth;
		this.roomHeight = roomHeight;
		this.roomX = roomX;
		this.roomY = roomY;
	}

	public int getRoomWidth() {
		return roomWidth;
	}

	public int getRoomHeight() {
		return roomHeight;
	}

	public int getRoomX() {
		return roomX;
	}

	public int getRoomY() {
		return roomY;
	}

	public boolean intersects(Room other, int minDistance) {
		return !(this.roomX + this.roomWidth + minDistance <= other.roomX ||
				other.roomX + other.roomWidth + minDistance <= this.roomX ||
				this.roomY + this.roomHeight + minDistance <= other.roomY ||
				other.roomY + other.roomHeight + minDistance <= this.roomY);
	}
}
