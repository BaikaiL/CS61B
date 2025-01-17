package gameoflife;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.FileUtils;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Am implementation of Conway's Game of Life using StdDraw.
 * Credits to Erik Nelson, Jasmine Lin and Elana Ho for
 * creating the assignment.
 */
public class GameOfLife {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final String SAVE_FILE = "src/save.txt";
    private long prevFrameTimestep;
    private TERenderer ter;
    private Random random;
    private TETile[][] currentState;
    private int width;
    private int height;

    /**
     * Initializes our world.
     * @param seed
     *
     * 通过seed的值初始化世界
     */
    public GameOfLife(long seed) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        ter = new TERenderer();
        ter.initialize(width, height);
        random = new Random(seed);
        TETile[][] randomTiles = new TETile[width][height];
        fillWithRandomTiles(randomTiles);
        currentState = randomTiles;
    }

    /**
     * Constructor for loading in the state of the game from the
     * given filename and initializing it.
     * @param filename
     *
     * 通过给定的文件来加载游戏并初始化
     */
    public GameOfLife(String filename) {
        this.currentState = loadBoard(filename);
        ter = new TERenderer();
        ter.initialize(width, height);
    }

    /**
     * Constructor for loading in the state of the game from the
     * given filename and initializing it. For testing purposes only, so
     * do not modify.
     * @param filename
     */
    public GameOfLife(String filename, boolean test) {
        this.currentState = loadBoard(filename);
    }

    /**
     * Initializes our world without using StdDraw. For testing purposes only,
     * so do not modify.
     * @param seed
     */
    public GameOfLife(long seed, boolean test) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        random = new Random(seed);
        TETile[][] randomTiles = new TETile[width][height];
        fillWithRandomTiles(randomTiles);
        currentState = randomTiles;
    }

    /**
     * Initializes our world with a given TETile[][] without using StdDraw.
     * For testing purposes only, so do not modify.
     * @param tiles
     * @param test
     */
    public GameOfLife(TETile[][] tiles, boolean test) {
        TETile[][] transposeState = transpose(tiles);
        this.currentState = flip(transposeState);
        this.width = tiles[0].length;
        this.height = tiles.length;
    }

    /**
     * Flips the matrix along the x-axis.
     * 沿 x 轴翻转矩阵。
     * @param tiles
     * @return
     */
    private TETile[][] flip(TETile[][] tiles) {
        int w = tiles.length;
        int h = tiles[0].length;

        TETile[][] rotateMatrix = new TETile[w][h];
        int y = h - 1;
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                rotateMatrix[i][y] = tiles[i][j];
            }
            y--;
        }
        return rotateMatrix;
    }

    /**
     * Transposes the tiles.
     * 平移瓷砖。
     * @param tiles
     * @return 和tiles完全相同的新TETtile数组
     */
    private TETile[][] transpose(TETile[][] tiles) {
        int w = tiles[0].length;
        int h = tiles.length;

        TETile[][] transposeState = new TETile[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                transposeState[x][y] = tiles[y][x];
            }
        }
        return transposeState;
    }

    /**
     * Runs the game. You don't have to worry about how this method works.
     * DO NOT MODIFY THIS METHOD!
     */
    public void runGame() {
        boolean paused = false;
        long evoTimestamp = System.currentTimeMillis();
        long pausedTimestamp = System.currentTimeMillis();
        long clickTimestamp = System.currentTimeMillis();
        while (true) {
            if (!paused && System.currentTimeMillis() - evoTimestamp > 250) {
                evoTimestamp = System.currentTimeMillis();
                currentState = nextGeneration(currentState);
            }
            if (System.currentTimeMillis() - prevFrameTimestep > 17) {
                prevFrameTimestep = System.currentTimeMillis();

                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                int tileX = (int) mouseX;
                int tileY = (int) mouseY;

                TETile currTile = currentState[tileX % width][tileY % height];

                if (StdDraw.isMousePressed() && System.currentTimeMillis() - clickTimestamp > 250) {
                    clickTimestamp = System.currentTimeMillis();
                    if (currTile == Tileset.CELL) {
                        currentState[tileX][tileY] = Tileset.NOTHING;
                    } else {
                        currentState[tileX][tileY] = Tileset.CELL;
                    }
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && System.currentTimeMillis() - pausedTimestamp > 500) {
                    pausedTimestamp = System.currentTimeMillis();
                    paused = !paused;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                    saveBoard();
                    System.exit(0);
                }
                ter.renderFrame(currentState);
            }
        }
    }


    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * 用随机瓦片填充给定的二维瓦片数组。
     * @param tiles
     */
    public void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = randomTile();
            }
        }
    }

    /**
     * Fills the 2D array of tiles with NOTHING tiles.
     * 用空瓦片填充给定的二维瓦片数组。
     * @param tiles
     */
    public void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Selects a random tile, with a 50% change of it being a CELL
     * and a 50% change of being NOTHING.
     * 随机选择一块瓷砖，50% 的可能是一个细胞，50% 的可能什么都不是。
     */
    private TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
        int tileNum = random.nextInt(2);
        return switch (tileNum) {
            case 0 -> Tileset.CELL;
            default -> Tileset.NOTHING;
        };
    }

    /**
     * Returns the current state of the board.
     * @return
     */
    public TETile[][] returnCurrentState() {
        return currentState;
    }

    /**
     * At each timestep, the transitions will occur based on the following rules:
     *  1.Any live cell with fewer than two live neighbors dies, as if by underpopulation.
     *  2.Any live cell with two or three neighbors lives on to the next generation.
     *  3.Any live cell with more than three neighbors dies, as if by overpopulation,
     *  4.Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     * 在每个时间步，将根据以下规则发生转变：
     *      * 1.任何邻居少于两个的活细胞都会死亡，就像繁殖不足一样。
     *      * 2.任何有两个或三个邻居的活细胞都会延续到下一代。
     *      * 3.任何拥有三个以上邻居的活细胞都会死亡，就像人口过剩一样、
     *      * 4.任何死亡细胞有三个活邻居都会变成活细胞，就像繁殖一样。
     * @param tiles
     * @return
     */
    public TETile[][] nextGeneration(TETile[][] tiles) {
        TETile[][] nextGen = new TETile[width][height];
        // The board is filled with Tileset.NOTHING
        fillWithNothing(nextGen);

        // 实现此方法，以便发生所描述的转换。
        // 当前状态用 TETiles[][] tiles 表示，下一个状态用 TETile[][] nextGen 表示。
        // 状态/演变应该以 TETile[][] nextGen 返回。
        int countAlive = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                TETile tile = tiles[x][y];
                countAlive = countAliveCell(tiles,x,y);
                // 如果是活细胞
                if (tile == Tileset.CELL) {
                    // 规则1和规则3
                    if(countAlive < 2 || countAlive > 3) {
                        nextGen[x][y] = Tileset.NOTHING;
                    }
                    // 规则2
                    else{
                        nextGen[x][y] = Tileset.CELL;
                    }
                }
                // 如果是死细胞，根据规则4更新
                else if (tile == Tileset.NOTHING){
                    if (countAlive == 3) {
                        nextGen[x][y] = Tileset.CELL;
                    }
                    else {
                        nextGen[x][y] = Tileset.NOTHING;
                    }
                }
            }
        }

        return nextGen;
    }

    // 返回指定tile附近存活的细胞数
    private int countAliveCell(TETile[][] tiles, int x, int y) {
        int count = 0;
        // 遍历该点附近的邻居，从正上方开始顺时针遍历
        int[] nextX = {0,1,1,1,0,-1,-1,-1};
        int[] nextY = {1,1,0,-1,-1,-1,0,1};
        int new_x;
        int new_y;
        for(int i = 0; i < nextX.length; i++) {
            new_x = x + nextX[i];
            new_y = y + nextY[i];
            if(isInBounds(new_x, new_y) && isAliveCell(tiles[new_x][new_y])) {
                count++;
            }
        }
        return count;
    }

    private boolean isAliveCell(TETile tile){
        return tile == Tileset.CELL;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Helper method for saveBoard without rendering and running the game.
     * @param tiles
     */
    public void saveBoard(TETile[][] tiles) {
        TETile[][] transposeState = transpose(tiles);
        this.currentState = flip(transposeState);
        this.width = tiles[0].length;
        this.height = tiles.length;
        saveBoard();
    }

    /**
     * Saves the state of the current state of the board into the
     * save.txt file (make sure it's saved into this specific file).
     * 0 represents NOTHING, 1 represents a CELL.
     */
    public void saveBoard() {

        StringBuilder content = new StringBuilder();
        content.append(width).append(" ").append(height).append("\n");
        StringBuilder line = new StringBuilder();
        TETile[][] save = flip(currentState);
        save = transpose(save);
        for (int x = 0; x < save.length; x++) {
            for (int y = 0; y < save[0].length; y++) {
                if(save[x][y] == Tileset.NOTHING) {
                    line.append(0);
                }
                else if(save[x][y] == Tileset.CELL) {
                    line.append(1);
                }
            }
            line.append("\n");
            content.append(line);
            line.setLength(0);
        }


        FileUtils.writeFile(SAVE_FILE, content.toString());




    }

    /**
     * Loads the board from filename and returns it in a 2D TETile array.
     * 0 represents NOTHING, 1 represents a CELL.
     */
    public TETile[][] loadBoard(String filename) {

        String content = FileUtils.readFile(filename);


        String[] lines = content.split("\n");
        String line = lines[0];

        int height = Integer.parseInt(line.split(" ")[0]);
        int width = Integer.parseInt(line.split(" ")[1]);
        TETile[][] tiles = new TETile[width][height];


        for(int y = 1; y < lines.length; y++) {
            line = lines[y];
            for(int x = 0; x < line.length(); x++) {
                if(line.charAt(x) == '0') {
                    tiles[x][y-1] = Tileset.NOTHING;
                }
                else if(line.charAt(x) == '1') {
                    tiles[x][y-1] = Tileset.CELL;
                }
            }
        }
        tiles = transpose(tiles);
        tiles = flip(tiles);

        return tiles;
    }

    /**
     * This is where we run the program. DO NOT MODIFY THIS METHOD!
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            // Read in the board from a file.
            if (args[0].equals("-l")) {
                GameOfLife g = new GameOfLife(args[1]);
                g.runGame();
            }
            System.out.println("Verify your program arguments!");
            System.exit(0);
        } else {
            long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
            GameOfLife g = new GameOfLife(seed);
            g.runGame();
        }
    }
}
