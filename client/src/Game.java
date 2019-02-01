import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {
    private int p1x, p1y, p2x, p2y;
    private String playerMessage;
    private String enemyMessage;
    private String[] p2Array;

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

        p1x = 0;
        p1y = 0;
        p2x = 0;
        p2y = 0;

        // Listen for and handle mouse movements
        mouseEventHandler();

        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();

        AnimationTimer animationtimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    // Set position-message
                    playerMessage = p1x + "," + p1y;

                    // Exchange positions
                    client.setPlayerMessage(playerMessage);
                    enemyMessage = client.getEnemyMessage();

                    p2Array = enemyMessage.split(",");
                    p2x = Integer.parseInt(p2Array[0]);
                    p2y = Integer.parseInt(p2Array[1]);

                    // Place player
                    player1Ball.setCenterX(p1x);
                    player1Ball.setCenterY(p1y);

                    // Place enemy
                    player2Ball.setCenterX(p2x);
                    player2Ball.setCenterY(p2y);

                    //System.out.println("Player: " + playerMessage);
                    //System.out.println("Enemy: " + enemyMessage);
                } catch (Exception e) {
                    //System.err.println("Exception here, I don't care");
                }
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
