public class objectPlacer {
    GamePanel game;
    public objectPlacer(GamePanel game){
        this.game = game;
    }

    public void place(){
        game.safe[0] = new SafeObject(576, 1104, 50, 100000);
        game.safe[0].setX(12 * game.gameTileSize());
        game.safe[0].setY(23*game.gameTileSize());
        System.out.println(game.safe[0].getGlobalX());
        System.out.println(game.safe[0].getGlobalY());
    }
}
