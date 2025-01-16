package core;
import java.util.Random;
import tileengine.Tileset;
import tileengine.TERenderer;
import tileengine.TETile;
public class World {

    // build your own world!
    private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static final long SEED = 2873123;
	private static final Random RANDOM = new Random(SEED);


	public static void fillWithRandomTiles(TETile[][] tiles) {
		int height = tiles[0].length;
		int width = tiles.length;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = randomTile();
			}
		}
	}


	private static TETile randomTile() {
		// The following call to nextInt() uses a bound of 3 (this is not a seed!) so
		// the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
		int tileNum = RANDOM.nextInt(3);
		return switch (tileNum) {
			case 0 -> Tileset.WALL;
			case 1 -> Tileset.FLOOR;
			default -> Tileset.NOTHING;
		};
	}

	public static void main(String[] args) {
		TERenderer ter = new TERenderer();
		ter.initialize(WIDTH, HEIGHT);

		TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
		fillWithRandomTiles(randomTiles);

		ter.renderFrame(randomTiles);
	}

}
