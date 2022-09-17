package com.example.mazegame.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.mazegame.internal.Cell;
import com.example.mazegame.internal.Edge;
import com.example.mazegame.internal.Labyrinth;
import com.example.mazegame.internal.Player;
import com.example.mazegame.R;
import com.example.mazegame.internal.PlayerListener;

import java.util.Timer;
import java.util.TimerTask;

public class DrawView extends View implements PlayerListener {
  private final Paint paint = new Paint();
  private final Integer cellColor;
  private final Integer edgeColor;
  private final Integer nonCellColor;
  private final Integer playerColor;
  private Labyrinth labyrinth;
  private Integer labyrinthRows;
  private Integer labyrinthCols;
  private Player player;
  private Timer timer = new Timer("PlayerTimer");
  private final TimerTask movePlayer = new TimerTask() {
    @Override
    public void run() {
      if (player != null) {
        player.setPosition(player.getX() + 1, player.getY() + 1, labyrinth.getCells());
      }
    }
  };

  public DrawView(Context context, AttributeSet attr) {
    super(context, attr);
    TypedArray typedArray = context.getTheme().obtainStyledAttributes(attr, R.styleable.DrawView, 0, 0);
    cellColor = typedArray.getColor(R.styleable.DrawView_cellColor, Color.BLACK);
    edgeColor = typedArray.getColor(R.styleable.DrawView_edgeColor, Color.WHITE);
    nonCellColor = typedArray.getColor(R.styleable.DrawView_nonCellColor, Color.RED);
    playerColor = typedArray.getColor(R.styleable.DrawView_playerColor, Color.RED);
    labyrinthRows = typedArray.getInteger(R.styleable.DrawView_labyrinthRows, 20);
    labyrinthCols = typedArray.getInteger(R.styleable.DrawView_labyrinthCols, 20);
    labyrinth = new Labyrinth(labyrinthRows, labyrinthCols, 200, 200);
    labyrinth.buildLabyrinth();
    player = new Player(15, 15, labyrinth.getSquareLength()/5);
    player.addListener(this);
    timer.schedule(movePlayer, 0L, 100L);
  }

  @Override
  public void onDraw(Canvas canvas) {
    paint.setStyle(Paint.Style.FILL);
    for (int i = 0; i < labyrinth.getRows(); i++) {
      for (int j = 0; j < labyrinth.getCols(); j++) {
        Cell c = labyrinth.getCells().get(i).get(j);
        paint.setColor(c.isMaze() ? nonCellColor : cellColor);
        canvas.drawRect(c.getX(), c.getY(), c.getX() + labyrinth.getSquareLength(), c.getY() + labyrinth.getSquareLength(), paint);
      }
    }

    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(edgeColor);
    for (Edge e : labyrinth.getEdges()) {
      if (e.isVisible()) {
        float x0 = e.getX0() * labyrinth.getSquareLength();
        float y0 = e.getY0() * labyrinth.getSquareLength();
        float x = e.getX() * labyrinth.getSquareLength();
        float y = e.getY() * labyrinth.getSquareLength();
        canvas.drawLine(x0 - 2, y0 - 2, x - 2, y - 2, paint);
        canvas.drawLine(x0 - 1, y0 - 1, x - 1, y - 1, paint);
        canvas.drawLine(x0, y0, x, y, paint);
        canvas.drawLine(x0 + 1, y0 + 1, x + 1, y + 1, paint);
        canvas.drawLine(x0 + 2, y0 + 2, x + 2, y + 2, paint);
      }
    }

    paint.setStyle(Paint.Style.FILL);
    paint.setColor((player.isColliding()) ? Color.BLUE : playerColor);
    canvas.drawCircle(player.getX(), player.getY(), player.getRadius(), paint);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    labyrinth.setCanvasWidth(w);
    labyrinth.setCanvasHeight(h);
    labyrinth.resizeCells();
    labyrinth.resizeEdges();
    player.setRadius(labyrinth.getSquareLength()/5);
    invalidate();
  }

  @Override
  public void playerPositionChanged(int oldX, int oldY, int x, int y) {
    invalidate();
  }
}
