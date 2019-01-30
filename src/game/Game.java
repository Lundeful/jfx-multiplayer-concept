package game;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {
    private int p1x, p1y, p2x, p2y;
    private double screenHeight;
    private double screenWidth;

    @FXML
    private ImageView p1;

    @FXML
    private Circle player1Ball;

    @FXML
    private Circle player2Ball;

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    @FXML
    private AnchorPane ap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        screenHeight = 600;
        screenWidth = 600;

        // Listen for and handle mouse movements
        mouseEventHandler();

        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();


        AnimationTimer animationtimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // System.out.println(p1x + " " + p1y);
                player1Ball.setCenterX(p1x);
                player1Ball.setCenterY(p1y);
                client.setP1x(p1x);
                client.setP1y(p1y);

                player2Ball.setCenterX(p2x);
                player2Ball.setCenterY(p2y);
            }
        };
        animationtimer.start();
    }

    private void mouseEventHandler() {
        ap.setOnMouseMoved(
                mouseEvent -> {
                    p1x = (int)Math.round(mouseEvent.getSceneX());
                    p1y = (int)Math.round(mouseEvent.getSceneY());
                }
        );
    }
}
