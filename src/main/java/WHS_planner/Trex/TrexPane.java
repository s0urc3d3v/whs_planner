package WHS_planner.Trex;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Created by geoffrey_wang on 1/14/17.
 */
public class TrexPane extends Pane{
    private boolean isEnabled = false;
    private boolean isRunning = false;
    private boolean isJummping = false;
    private double height = 0;
    private double vel = 0;
    private Canvas canvas;
    private GraphicsContext gc;
    private Image background;
    private int clearRectSize;
    private int count = 0;

    private int bg1 = 0, bg2 = 2400, text = 200;
    private Image trexJump;
    private double trexX;


    public TrexPane(){
        canvas = new Canvas(200,150);
        this.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        background = new Image("Trex/background.png");
        trexJump = new Image("Trex/small-trexJump.png");

        AnimatedImage trexRun = new AnimatedImage();
        Image[] imageArray = new Image[]{new Image("Trex/small-trex1.png"),new Image("Trex/small-trex2.png")};
        trexRun.frames = imageArray;
        trexRun.duration = 0.175;

        trexX = (canvas.getWidth()-trexJump.getWidth())/2;

        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double time = (currentNanoTime - startNanoTime) / 1000000000.0;
                if(!isEnabled){
                    staticTRex();
//                    drawBackground(0);
//                    gc.drawImage(trexJump,trexX,canvas.getHeight()-trexJump.getHeight());

                    clearRectSize = (int)(trexX - 10);
                }else {
                    clear();
                    if (isRunning) {
                        drawBackground(2);
                        if(height == 0) {
                            gc.drawImage(trexRun.getFrame(time), trexX, canvas.getHeight() - (trexRun.getFrame(time).getHeight()));
                        }
                    }else{
                        drawBackground(0);
                    }
                    if (isJummping == true && height == 0) {
                        vel = 6;
                    }

                    if (vel > 0 || height > 0) {
                        height += vel;
                        gc.drawImage(trexJump, trexX, canvas.getHeight() - trexJump.getHeight() - height);
                        if (height > 0) {
                            vel -= 0.3;
                        } else if (height < 0) {
                            height = 0;
                            vel = 0;
                            if(!isRunning){
                                isRunning = true;
                            }
                        }
                    }
                    if(clearRectSize > 0){
                        clearRectSize -= 2;
                    }

                }
                if(count >= 3){
                    clear();
                    staticTRex();
//                    gc.drawImage(trexJump,trexX,canvas.getHeight()-trexJump.getHeight());
//                    drawBackground(0);
//                    clearRectSize = (int)(trexX - 10);
                    count = 0;
                    this.stop();
                }
                if(clearRectSize > 0) {
                    gc.clearRect(canvas.getWidth() - clearRectSize, 0, clearRectSize, canvas.getHeight());
                    gc.clearRect(0, 0, clearRectSize, canvas.getHeight());
                }
            }
        }.start();

        canvas.setOnMousePressed(event -> {
            if(!isEnabled){
                isEnabled = true;
            }
            isJummping = true;
        });

        canvas.setOnMouseReleased(event -> {
            isJummping = false;
        });
    }

    public void clear(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void staticTRex()
    {
        drawBackground(0);
        gc.drawImage(trexJump,trexX,canvas.getHeight()-trexJump.getHeight());
        clearRectSize = (int)(trexX - 10);
    }

    public void drawBackground(int distance){
        bg1 -= distance;
        bg2 -= distance;
        text -= distance;

        gc.drawImage(background,bg1,canvas.getHeight() - background.getHeight());
        gc.drawImage(background,bg2,canvas.getHeight() - background.getHeight());
        gc.setFont(new Font("Arial",20));
        gc.fillText("S h h h ,   d o n ' t   t e l l   a n y o n e !",text,canvas.getHeight()/4);
        if(bg1 <= -background.getWidth()){
            bg1 = (int)background.getWidth();
        }
        if(bg2 <= -background.getWidth()){
            bg2 = (int)background.getWidth();
        }
        if(text <= -600){
            text = 250;
            count ++;
        }
    }
}