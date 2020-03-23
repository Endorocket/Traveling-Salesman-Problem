import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {

    TspFileReader tspFileReader = new TspFileReader();
    String fileName = "kroA100";
    List<City> cities = tspFileReader.read("src/main/resources/" + fileName + ".tsp");

    int populationSize = 1500;
    int numberOfPopulation = 250;
    double elitePercentage = 0.04;
    double crossPercentage = 0.75;
    double mutatePercentage = 0.2;
    int tour = 10;

    String resultFilePath = "src/main/resources/results/" + fileName + ".csv";
    Files.deleteIfExists(Paths.get(resultFilePath));

    try (BufferedWriter writer = new BufferedWriter(
        new FileWriter(resultFilePath, false)
    )) {
      EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(cities, populationSize, numberOfPopulation, elitePercentage, crossPercentage, mutatePercentage, tour, writer);
      algorithm.run();
    }
  }
}
