package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWorldGenerator {
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static final long SEED = 1915518;
	private static final Random RANDOM = new Random(SEED);

	public static void main(String[] args) {
		TERenderer ter = new TERenderer();
		ter.initialize(WIDTH, HEIGHT);

		TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
		generateRandomWorld(randomTiles);
		generateWall(randomTiles);

		ter.renderFrame(randomTiles);
	}

	/**
	 * 在已经生成房间和走廊的基础上，
	 * 使用墙将房间的走廊包围
	 **/
	public static void generateWall(TETile[][] tiles){
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				// 检查当前格子是否为房间或走廊
				if (tiles[x][y] == Tileset.FLOOR) {
					// 检查当前格子的上方
					if (y > 0 && tiles[x][y - 1] == Tileset.NOTHING) {
						tiles[x][y - 1] = Tileset.WALL;
					}
					// 检查当前格子的下方
					if (y < tiles[0].length - 1 && tiles[x][y + 1] == Tileset.NOTHING) {
						tiles[x][y + 1] = Tileset.WALL;
					}
					// 检查当前格子的左方
					if (x > 0 && tiles[x - 1][y] == Tileset.NOTHING) {
						tiles[x - 1][y] = Tileset.WALL;
					}
					// 检查当前格子的右方
					if (x < tiles.length - 1 && tiles[x + 1][y] == Tileset.NOTHING) {
						tiles[x + 1][y] = Tileset.WALL;
					}
				}
			}
		}
	}

	/**
	 * 生成房间和走廊
	 **/
	public static void generateRandomWorld(TETile[][] tiles) {
		// 填充背景
		fillWithRandomTiles(tiles, Tileset.NOTHING);

		// 记录已经生成的房间
		List<Room> rooms = new ArrayList<Room>();
		Room room;

		// 生成房间
		int numRooms = RANDOM.nextInt(5) + 5; // 随机生成5到10个房间
		for (int i = 0; i < numRooms; i++) {
			int roomWidth, roomHeight, roomX, roomY;
			do{
				roomWidth = RANDOM.nextInt(5) + 5; // 房间宽度在5到10之间
				roomHeight = RANDOM.nextInt(5) + 5; // 房间高度在5到10之间
				roomX = RANDOM.nextInt(WIDTH - roomWidth - 4) + 2; // 确保房间不在边缘且至少间隔1格
				roomY = RANDOM.nextInt(HEIGHT - roomHeight - 4) + 2; // 确保房间不在边缘且至少间隔1格
				room = new Room(roomX, roomY, roomWidth, roomHeight);
			}while (overlapsWithExistingRooms(rooms,room));
			rooms.add(room);

			// 填充房间
			fillRoom(tiles, room,Tileset.FLOOR);

		}
	}

	/**
	 * 判断生成的房间是否与已存在的房间有重叠
	 * **/
	private static boolean overlapsWithExistingRooms(List<Room> rooms, Room room) {
		for (Room r : rooms) {
			if (room.intersects(r,1)) { // 考虑至少间隔1格
				return true; // 如果重叠，返回true
			}
		}
		return false; // 如果不重叠，返回false
	}



	/**
	 * 初始化title数组，将所有瓷砖初始化为NOTHING
	 */
	public static void fillWithRandomTiles(TETile[][] tiles, TETile tile) {
		int height = tiles[0].length;
		int width = tiles.length;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = tile;
			}
		}
	}

	/**
	 * 将room对于的瓷砖转换为floor
	 * */
	public static void fillRoom(TETile[][] tiles, Room room, TETile tile) {
		int x = room.getRoomX();
		int y= room.getRoomY();
		int width = room.getRoomWidth();
		int height = room.getRoomHeight();

		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				tiles[i][j] = tile;
			}
		}
	}
}