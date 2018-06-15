package sample;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {
    @Override
    public void start(Stage stage) {
        ArrayList<Circle> circles = new ArrayList<Circle>();
        ArrayList <Rectangle> rectangles = new ArrayList<Rectangle>();
        ArrayList <Integer> X = new ArrayList<Integer>();
        ArrayList <Integer> Y = new ArrayList<Integer>();
        ArrayList <Integer> ves = new ArrayList<Integer>();

        Pane canvas = new Pane();
        Scene scene = new Scene(canvas, 500, 500, Color.ALICEBLUE);
        stage.setTitle("Animated Ball");
        stage.setScene(scene);
        stage.show();

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Random r = new Random();
                int x=r.nextInt(10);
                int y=r.nextInt(10);
                int count;
                count=r.nextInt(10);
                Circle ball = new Circle(7, Color.RED);
                ball.relocate(5, 5);
                ves.add(count);
                Rectangle rect = new Rectangle(30,30, Color.BLUE);
                canvas.getChildren().addAll(ball,rect);

                circles.add(ball);
                rectangles.add(rect);
                X.add(x);
                Y.add(y);

                rect.setLayoutX(r.nextInt(400));
                rect.setLayoutY(r.nextInt(400));

                ball.setLayoutX(r.nextInt(400));
                ball.setLayoutY(r.nextInt(400));


            }
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {

                for(int i=0; i<circles.size(); i++){
                    Circle ball = circles.get(i);
                    int dx = X.get(i);
                    int dy = Y.get(i);

                    ball.setLayoutX(ball.getLayoutX() + dx);
                    ball.setLayoutY(ball.getLayoutY() + dy);

                    Bounds bounds = canvas.getBoundsInLocal();
                    if(ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius()) ||
                            ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius()) ){
                        X.set(i, -dx);
                    }
                    if((ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius())) ||
                            (ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius()))){
                        Y.set(i, -dy);
                    }

                    for(int j=0; j<rectangles.size();j++){
                        Rectangle rect = rectangles.get(j);
                        int c = ves.get(j);
                        if(circles.get(i).getBoundsInParent().intersects(rectangles.get(j).getBoundsInParent()) && c>0){
                            System.out.println(ves);
                            X.set(i, -dx);
                            Y.set(i, -dy);
                            c=c-1;
                            ves.set(j,c);
                            if (c==0){
                                canvas.getChildren().remove(rect);
                            }
                        }
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public static void main(String[] args) {
        launch();
    }
}