import java.util.List;
import javafx.util.Pair;

public interface Turtle{

  boolean setLocation(double xCord, double yCord);

  boolean changeSize(double newSize);

  double getX();

  double getY();

  boolean setAngle(double newAngle);

  boolean reset();

  boolean setPenDown(boolean penStatus);

  List<Pair<Integer, Integer>> getHistory();

  boolean clearHistory();
}