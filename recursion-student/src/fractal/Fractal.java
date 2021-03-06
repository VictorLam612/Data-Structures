package fractal;

import fractal.Turtle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;


/**
 * A class that implements some basic fractal drawing methods using recursion.
 * These methods include the Koch curve, tree, Sierpinski triangle and carpet.
 */
public class Fractal {
  private Graphics2D g2d = null;  // a Graphics2D object as canvas for drawing
  private int width = 0;
  private int height = 0;  // image width and height
  private int maxRecursionLevel = 0;  // maximum recursion level
  private String fractalType = "Koch Curve";  // type of fractal
  private Color color = Color.green;  // draw color

  /**
   * setGraphics.
   * @param g graph
   * @param w width
   * @param h  height
   */
  public void setGraphics(Graphics g, int w, int h) {
    g2d = (Graphics2D)g; 
    width = w; 
    height = h;
  }
  
  public void setFractalType(String t) { 
    fractalType = t; 
  }
  
  public void setColor(Color c) { 
    color = c; 
  }
  
  public void setMaxRecursion(int n) { 
    maxRecursionLevel = n;
  }

  // Recursive method for drawing the Koch curve given two points and the recursion level
  private void drawKochCurve(Point2D p1, Point2D p2, int level) {
    if (level == 0) { // base case
      drawLine(p1, p2);
      return;
    }
    double dist = p1.distance(p2) / 3;
    Turtle tortoise = new Turtle(p1, p2);
    tortoise.move(dist);
    Point2D p3 = tortoise.getPosition();
    drawKochCurve(p1, p3, level - 1);
    tortoise.turnLeft(60);
    tortoise.move(dist);
    Point2D p4 = tortoise.getPosition();
    drawKochCurve(p3, p4, level - 1);
    tortoise.turnRight(120);
    tortoise.move(dist);
    Point2D p5 = tortoise.getPosition(); 
    drawKochCurve(p4, p5, level - 1);
    drawKochCurve(p5, p2, level - 1);
  }

  // Recursive method for drawing a fractal Tree given two points and the recursion level
  private void drawTree(Point2D p1, Point2D p2, int level) {
    if (level == 0) { // base case
      drawLine(p1, p2);
      return;
    }
    double dist = p1.distance(p2) / 3;
    Turtle tortoise = new Turtle(p1, p2);
    tortoise.move(dist); //Trunk
    Point2D p3 = tortoise.getPosition(); 
    drawLine(p1, p3); 
    tortoise.turnLeft(60); //Branch L
    tortoise.move(2 * dist);
    Point2D p4 = tortoise.getPosition();
    drawTree(p3, p4, level - 1);
    tortoise.turnLeft(180); //Going back to p3
    tortoise.move(2 * dist);
    tortoise.turnRight(255); //Branch R
    tortoise.move(2 * dist);
    Point2D p5 = tortoise.getPosition();
    drawTree(p3, p5, level - 1);
  }

  // Recursive method for drawing the Sierpinski Triangle given the three points
  // that define the triangle and the recursion level
  private void drawSierpinskiTriangle(Point2D p1, Point2D p2, Point2D p3, int level) {
    if (level == 0) { // base case
      drawTriangle(p1, p2, p3);
      return;
    }
    Point2D p4 = midPoint(p1, p2);
    Point2D p5 = midPoint(p2, p3);
    Point2D p6 = midPoint(p1, p3);
    drawSierpinskiTriangle(p1, p4, p6, level - 1);
    drawSierpinskiTriangle(p4, p2, p5, level - 1);
    drawSierpinskiTriangle(p6, p5, p3, level - 1);
  }
  
  
  //Method to calculate midpoint given two points
  //Returns midpoint of the two points
  public Point2D midPoint(Point2D coordOne, Point2D coordTwo) {
    double coordOnex = coordOne.getX();
    double coordTwox = coordTwo.getX();
    double coordOney = coordOne.getY();
    double coordTwoy = coordTwo.getY();
    Point2D midPoint = new Point2D.Double((coordOnex + coordTwox) / 2, (coordOney + coordTwoy) / 2);
    return midPoint;
  }

  // Recursive method for drawing the Sierpinski Carpet given the lower-left corner
  // of the square (p), the side length (a) of the square, and the recursion level
  private void drawSierpinskiCarpet(Point2D p, double a, int level) {
    if (level == 0) { // base case
      drawRectangle(p, a, a);
      return;
    }
    double pointX = p.getX();
    double pointX1 = p.getX() + 1 * (a / 3);
    double pointX2 = p.getX() + 2 * (a / 3);
    double pointY = p.getY();
    double pointY1 = p.getY() + 1 * (a / 3);
    double pointY2 = p.getY() + 2 * (a / 3);
    final Point2D pointOne = new Point2D.Double(pointX, pointY1);
    final Point2D pointTwo = new Point2D.Double(pointX, pointY2);
    final Point2D pointThree = new Point2D.Double(pointX1, pointY);
    final Point2D pointFour = new Point2D.Double(pointX1, pointY2);
    final Point2D pointFive = new Point2D.Double(pointX2, pointY);
    final Point2D pointSix = new Point2D.Double(pointX2, pointY1);
    final Point2D pointSeven = new Point2D.Double(pointX2, pointY2);
    drawSierpinskiCarpet(p, a / 3, level - 1);
    drawSierpinskiCarpet(pointOne, a / 3, level - 1);
    drawSierpinskiCarpet(pointTwo, a / 3, level - 1);
    drawSierpinskiCarpet(pointThree, a / 3, level - 1);
    drawSierpinskiCarpet(pointFour, a / 3, level - 1);
    drawSierpinskiCarpet(pointFive, a / 3, level - 1);
    drawSierpinskiCarpet(pointSix, a / 3, level - 1);
    drawSierpinskiCarpet(pointSeven, a / 3, level - 1);
  }

  // This method is left for you to experiment with creative fractals
  // designed by yourself. You will NOT be graded on this method 
  void drawMyFractal(/* other parameters that you may need */ int level) {
    if (level == 0) { // base case
      return;
    }
    /* Your creative fractal shape */
  }

  /** The code below provides utility methods.
   *  You should NOT need to modify any code below.
   */
  public void draw() {
    if (g2d == null || width == 0 || height == 0) {
      return;
    }
    g2d.setBackground(Color.black);
    g2d.clearRect(0, 0, width, height);
    g2d.setColor(color);
    if (fractalType.equalsIgnoreCase("Koch Curve")) {
      drawKochCurve(new Point2D.Double(0.0, 0.4), new Point2D.Double(1.0, 0.4), maxRecursionLevel);
    } else if (fractalType.equalsIgnoreCase("Snowflake")) {
      double r = 0.5;
      Point2D p1 = new Point2D.Double(r * Math.cos(Math.toRadians(150)) + 0.5,
          r * Math.sin(Math.toRadians(150)) + 0.5);
      Point2D p2 = new Point2D.Double(r * Math.cos(Math.toRadians(30)) + 0.5,
          r * Math.sin(Math.toRadians(30)) + 0.5);
      Point2D p3 = new Point2D.Double(r * Math.cos(Math.toRadians(-90)) + 0.5,
          r * Math.sin(Math.toRadians(-90)) + 0.5);
      // Snowflake is made of three Koch curves segments
      //  p1____p2
      //    \  /
      //     \/
      //     p3
      drawKochCurve(p1, p2, maxRecursionLevel);
      drawKochCurve(p2, p3, maxRecursionLevel);
      drawKochCurve(p3, p1, maxRecursionLevel);
    } else if (fractalType.equalsIgnoreCase("Tree")) {
      // double the maximum recursion level because tree subdivides very slowly
      drawTree(new Point2D.Double(0.6, 0.1), new Point2D.Double(0.6, 0.9),
          maxRecursionLevel * 2);
    } else if (fractalType.equalsIgnoreCase("Sp Triangle")) {
      double r = 0.5;
      Point2D p1 = new Point2D.Double(r * Math.cos(Math.toRadians(90)) + 0.5,
          r * Math.sin(Math.toRadians(90)) + 0.5);
      Point2D p2 = new Point2D.Double(r * Math.cos(Math.toRadians(-150)) + 0.5,
          r * Math.sin(Math.toRadians(-150)) + 0.5);
      Point2D p3 = new Point2D.Double(r * Math.cos(Math.toRadians(-30)) + 0.5,
          r * Math.sin(Math.toRadians(-30)) + 0.5);
      drawSierpinskiTriangle(p1, p2, p3, maxRecursionLevel);
    } else if (fractalType.equalsIgnoreCase("Sp Carpet")) {
      drawSierpinskiCarpet(new Point2D.Double(0, 0), 1, maxRecursionLevel);
    } else {
      drawMyFractal(/* other parameters that you may need */ maxRecursionLevel);
    }
  }
  
  /** Draw a straight line between two points P1 and P2.
   * The given x and y values of p1 and p2 are assumed to be within [0, 1] (i.e. normalized).
   * This allows our fractals to be resolution-independent. The method below converts the normalized
   * coordinates to integer coordinates based on the actual image resolution. 
   */
  private void drawLine(Point2D p1, Point2D p2) {
    int x1 = (int)(p1.getX() * width);
    int x2 = (int)(p2.getX() * width);
    // flip the Y coordinate
    // because screen Y axis is flipped from mathematical Y axis
    int y1 = (int)((1.0 - p1.getY()) * height);
    int y2 = (int)((1.0 - p2.getY()) * height);
    g2d.drawLine(x1, y1, x2, y2);
  }

  // Draw a solid rectangle given its lower left corner and its size
  private void drawRectangle(Point2D p, double w, double h) {
    int x0 = (int)(p.getX() * width);
    int y0 = (int)((1.0 - p.getY()) * height);
    int x1 = (int)((p.getX() + w) * width);
    int y1 = (int)((1.0 - (p.getY() + h)) * height);
    int[] xpoints = {x0, x0, x1, x1};
    int[] ypoints = {y0, y1, y1, y0};
    g2d.fillPolygon(xpoints, ypoints, 4);
  }

  // Draw a solid triangle given its three vertices
  private void drawTriangle(Point2D p1, Point2D p2, Point2D p3) {
    int[] xpoints = {(int)(p1.getX() * width),
        (int)(p2.getX() * width),
        (int)(p3.getX() * width)};
    int[] ypoints = {(int)((1.0 - p1.getY()) * height),
        (int)((1.0 - p2.getY()) * height),
        (int)((1.0 - p3.getY()) * height)};
    g2d.fillPolygon(xpoints, ypoints, 3);
  }
}
