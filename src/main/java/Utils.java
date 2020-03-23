public class Utils {

  public static int getRandomInt(int end) {
    return getRandomInt(0, end);
  }

  public static int getRandomInt(int start, int end) {
    return (int) (Math.random() * (end + 1 - start) + start);
  }
}
