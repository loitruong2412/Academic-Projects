package model;

import java.util.Objects;

/**
 * This class represents a 2D Point that has an x and y coordinate.
 */
public class Point2D {

  private final double x;
  private final double y;

  /**
   * Constructs a 2D point with the given numeric data.
   *
   * @param x the x-coordinate of this point
   * @param y the y-coordinate of this point
   */
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Return the x-coordinate of this point.
   *
   * @return x-coordinate of this point
   */
  public double getX() {
    return this.x;
  }

  /**
   * Return the y-coordinate of this point.
   *
   * @return y-coordinate of this point
   */
  public double getY() {
    return this.y;
  }

  /**
   * Return the toString of a model.Point2D as for example as (x: 25, y: 25).
   *
   * @return String that represents Point 2D
   */
  @Override
  public String toString() {
    return "x: " + this.x + " y: " + this.y;
  }

  /**
   * Returns a boolean if two 2D objects are the same meaning the x and y attributes of both
   * objects are equal.
   *
   * @param obj other object that we are comparing to this 2D Point
   * @return true if obj is a Point 2D object and the x and y attributes are the same as this 2D
   *          Point object
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (! (obj instanceof Point2D)) {
      return false;
    }
    Point2D other2D = (Point2D) obj;

    return (this.x == other2D.getX()) && (this.y == other2D.getY());
  }

  /**
   * Hashcode over written to return the same code if x and y are the same thing.
   *
   * @return int representing the object if two objects located in different memory locations
   *          have the same x and y it will return the same int
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
