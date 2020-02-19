public interface Turtle{
  public Turtle();

  public boolean setLocation(double xCord, double yCord);

  public boolean changeSize(double newSize);

  public double getX();

  public double getY();

  public boolean setAngle(double newAngle);

  public boolean reset();

  public boolean setPenDown(boolean penStatus);

  public List<Pair<integer, integer>> getHistory();

  public boolean clearHistory();
}