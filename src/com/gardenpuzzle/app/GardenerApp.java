package com.gardenpuzzle.app;

public class GardenerApp {
    private GameController gameController;

    public GardenerApp() {
        this.gameController = new GameController();
    }

    public void start() {
        gameController.startGame();
    }

    public static void main(String[] args) {
        GardenerApp app = new GardenerApp();
        app.start();
    }
}