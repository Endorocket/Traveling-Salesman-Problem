import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individual {

  private final List<City> cities;

  private double distance = -1;

  public Individual(List<City> cities) {
    this.cities = new ArrayList<>(cities);
  }

  public List<City> getCities() {
    return cities;
  }

  public double getDistance() {
    if (distance != -1) {
      return distance;
    }

    distance = 0;
    for (int i = 0; i < cities.size() - 1; i++) {
      distance += distance(cities.get(i), cities.get(i + 1));
    }
    distance += distance(cities.get(cities.size() - 1), cities.get(0));
    return distance;
  }

  private double distance(City first, City second) {
    return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2));
  }

  public double quality() {
    return 10_000 / getDistance();
  }

  public void mutate() {
    int p1 = Utils.getRandomInt(cities.size() - 4);
    int p2 = Utils.getRandomInt(p1, cities.size());

    List<City> subListHead = new ArrayList<>(cities.subList(0, p1));

    List<City> subListInverse = new ArrayList<>(cities.subList(p1, p2));
    Collections.reverse(subListInverse);

    List<City> subListTail = new ArrayList<>(cities.subList(p2, cities.size()));

    cities.clear();
    cities.addAll(subListHead);
    cities.addAll(subListInverse);
    cities.addAll(subListTail);
  }

  public String getInfo() {
    StringBuilder sb = new StringBuilder();
    cities.forEach(city -> sb.append(city.getId()).append(","));
    return sb.toString();
  }
}
