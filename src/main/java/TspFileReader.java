import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TspFileReader {

  public List<City> read(String path) throws IOException {
    List<City> cities = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      List<String> lines = reader.lines().collect(Collectors.toList());
      boolean eof = false;

      for (int i = 6; !eof; i++) {
        String line = lines.get(i);
        if (line.equalsIgnoreCase("EOF")) {
          eof = true;
        } else {
          String[] split = line.split(" ");
          int id = Integer.parseInt(split[0]);
          double x = Double.parseDouble(split[1]);
          double y = Double.parseDouble(split[2]);
          cities.add(new City(id, x, y));
        }
      }
    }
    return cities;
  }
}
