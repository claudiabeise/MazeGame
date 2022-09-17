package com.example.mazegame.internal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class Labyrinth {
  private final Random random = new Random(Instant.now().getEpochSecond());
  private int rows;
  private int cols;
  private int canvasWidth;
  private int canvasHeight;
  private int squareLength;
  private Vector<Vector<Cell>> cells;
  private Vector<Edge> edges;

  public Vector<Vector<Cell>> getCells() {
    return cells;
  }

  public void setCells(Vector<Vector<Cell>> cells) {
    this.cells = cells;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getCols() {
    return cols;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public int getCanvasWidth() {
    return canvasWidth;
  }

  public void setCanvasWidth(int canvasWidth) {
    this.canvasWidth = canvasWidth;
    squareLength = Math.min(canvasWidth, canvasHeight) / cols;
  }

  public int getCanvasHeight() {
    return canvasHeight;
  }

  public void setCanvasHeight(int canvasHeight) {
    this.canvasHeight = canvasHeight;
    squareLength = Math.min(canvasWidth, canvasHeight) / rows;
  }

  public Vector<Edge> getEdges() {
    return edges;
  }

  public int getSquareLength() {
    return squareLength;
  }

  public Labyrinth(int rows, int cols, int canvasWidth, int canvasHeight) {
    this.rows = rows;
    this.cols = cols;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    squareLength = Math.min(canvasWidth, canvasHeight) / Math.min(rows, cols);
  }

  public void buildLabyrinth() {
    createCells(rows, cols);
    createEdges(rows, cols);
    createLabyrinthAldousBroder();
    updateCellEdges();
  }

  private void updateCellEdges() {
    cells.parallelStream()
      .flatMap(Vector::parallelStream)
      .map(Cell::getEdges)
      .flatMap(Set::parallelStream)
      .forEach(edge -> {
        int index = edges.indexOf(edge);
        edge.setVisible(edges.get(index).isVisible());
        edge.setLength(edges.get(index).getLength());
      });
  }

  public void createEdges(int rows, int cols) {
    this.edges = new Vector<>();
    for (int i = 0; i < rows + 1; i++) {
      for (int j = 0; j < cols; j++) {
        Edge e = new Edge(i, j, i, (j + 1), getSquareLength());
        if (i - 1 >= 0) {
          e.getCells().add(cells.get(i - 1).get(j));
          cells.get(i - 1).get(j).getEdges().add(e);
        }
        if (i < rows) {
          e.getCells().add(cells.get(i).get(j));
          cells.get(i).get(j).getEdges().add(e);
        }
        edges.add(e);
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols + 1; j++) {
        Edge e = new Edge(i, j, (i + 1), j, getSquareLength());
        if (j - 1 >= 0) {
          e.getCells().add(cells.get(i).get(j - 1));
          cells.get(i).get(j - 1).getEdges().add(e);
        }
        if (j < cols) {
          e.getCells().add(cells.get(i).get(j));
          cells.get(i).get(j).getEdges().add(e);
        }
        edges.add(e);
      }
    }
  }

  private void createCells(int rows, int cols) {
    this.cells = new Vector<>(rows);

    int x = 0;
    int y = 0;
    for (int i = 0; i < rows; i++, y += getSquareLength()) {
      Vector<Cell> row = new Vector<>(cols);
      for (int j = 0; j < cols; j++, x += getSquareLength()) {
        row.add(new Cell(x, y, i, j, false));
      }
      this.cells.add(row);
    }
  }

  private void createLabyrinthAldousBroder() {
    int remaining = (rows * cols) - 1;
    Cell current = cells.get(random.nextInt(rows)).get(random.nextInt(cols));
    current.setVisited(true);
    while (remaining > 0) {
      Cell next = getRandomNeighbor(current);
      if (!next.isVisited()) {
        Edge wall = getWall(current, next);
        wall.setVisible(false);
        next.setVisited(true);
        remaining--;
      }
      current = next;
    }
  }

  public void resizeCells() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Cell c = cells.get(i).get(j);
        c.setX(j * getSquareLength());
        c.setY(i * getSquareLength());
      }
    }
  }

  public void resizeEdges() {
    for (Edge edge : edges) {
      edge.setLength(getSquareLength());
    }
    updateCellEdges();
  }

  private Edge getWall(Cell a, Cell b) {
    for (Edge e : edges) {
      if (e.getCells().contains(a) && e.getCells().contains(b)) {
        return e;
      }
    }
    throw new RuntimeException(String.format("Could not find wall between %s and %s", a.toString(), b.toString()));
  }

  private Cell getRandomNeighbor(Cell cell) {
    Vector<Cell> neighbors = getCellNeighbors(cell);
    Cell neighbor = neighbors.get(random.nextInt(neighbors.size()));
    return cells.get(neighbor.getRow()).get(neighbor.getCol());
  }

  private Vector<Edge> getWalls(Cell cell) {
    Edge e1 = new Edge(cell.getRow(), cell.getCol(), cell.getRow(), cell.getCol() + 1, getSquareLength());
    Edge e2 = new Edge(cell.getRow(), cell.getCol(), cell.getRow() + 1, cell.getCol(), getSquareLength());
    Edge e3 = new Edge(cell.getRow(), cell.getCol() + 1, cell.getRow() + 1, cell.getCol() + 1, getSquareLength());
    Edge e4 = new Edge(cell.getRow() + 1, cell.getCol(), cell.getRow() + 1, cell.getCol() + 1, getSquareLength());
    List<Edge> list = new ArrayList<>();
    list.add(e1);
    list.add(e2);
    list.add(e3);
    list.add(e4);
    return edges.stream()
      .filter((edge) -> list.contains(edge) && edge.isVisible()).collect(Collectors.toCollection(Vector::new));
  }

  private Vector<Cell> getCellNeighbors(Cell cell) {
    int row = cell.getRow();
    int col = cell.getCol();
    Vector<Cell> walls = new Vector<>();
    if (row > 0 && row < rows - 1 && col > 0 && col < cols - 1) {
      walls.add(cells.get(row - 1).get(col));
      walls.add(cells.get(row).get(col + 1));
      walls.add(cells.get(row).get(col - 1));
      walls.add(cells.get(row + 1).get(col));
    } else if (row == 0 && col > 0 && col < cols - 1) {
      walls.add(cells.get(row).get(col + 1));
      walls.add(cells.get(row).get(col - 1));
      walls.add(cells.get(row + 1).get(col));
    } else if (row == rows - 1 && col > 0 && col < cols - 1) {
      walls.add(cells.get(row - 1).get(col));
      walls.add(cells.get(row).get(col + 1));
      walls.add(cells.get(row).get(col - 1));
    } else if (row > 0 && row < rows - 1 && col == 0) {
      walls.add(cells.get(row - 1).get(col));
      walls.add(cells.get(row).get(col + 1));
      walls.add(cells.get(row + 1).get(col));
    } else if (row > 0 && row < rows - 1 && col == cols - 1) {
      walls.add(cells.get(row - 1).get(col));
      walls.add(cells.get(row).get(col - 1));
      walls.add(cells.get(row + 1).get(col));
    } else if (row == 0 && col == 0) {
      walls.add(cells.get(row).get(col + 1));
      walls.add(cells.get(row + 1).get(col));
    } else if (row == rows - 1 && col == 0) {
      walls.add(cells.get(row - 1).get(col));
      walls.add(cells.get(row).get(col + 1));
    } else if (row == 0 && col == cols - 1) {
      walls.add(cells.get(row).get(col - 1));
      walls.add(cells.get(row + 1).get(col));
    } else if (row == rows - 1 && col == cols - 1) {
      walls.add(cells.get(row - 1).get(col));
      walls.add(cells.get(row).get(col - 1));
    }

    return walls;
  }
}
