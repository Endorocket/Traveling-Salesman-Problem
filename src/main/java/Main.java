import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {

  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) throws IOException {

    while (true) {
      TspFileReader tspFileReader = new TspFileReader();
      String fileName = chooseFile();
      List<City> cities = tspFileReader.read("src/main/resources/" + fileName + ".tsp");

      Params params = getParams();

      String resultFilePath = "src/main/resources/results/" + fileName + ".csv";
      Files.deleteIfExists(Paths.get(resultFilePath));

      try (BufferedWriter writer = new BufferedWriter(
          new FileWriter(resultFilePath, false)
      )) {
        writer.write(params.toString());
        writer.newLine();

        EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(cities, params, writer);
        algorithm.run();
      }

      System.out.println("Czy kontynować? [Y/n]");
      if (sc.nextLine().equals("n"))
        break;
    }
  }

  private static String chooseFile() {
    while (true) {
      System.out.println("Jaki plik?");
      String berlin52 = "berlin52";
      String kroA100 = "kroA100";
      String kroA150 = "kroA150";
      String kroA200 = "kroA200";
      String fl417 = "fl417";

      System.out.println("1 - " + berlin52);
      System.out.println("2 - " + kroA100);
      System.out.println("3 - " + kroA150);
      System.out.println("4 - " + kroA200);
      System.out.println("5 - " + fl417);

      try {
        int choice = Integer.parseInt(sc.nextLine()); // 1
        switch (choice) {
          case 1:
            return berlin52;
          case 2:
            return kroA100;
          case 3:
            return kroA150;
          case 4:
            return kroA200;
          case 5:
            return fl417;
          default:
            System.out.println("Niepoprawne dane!");
        }
      } catch (Exception e) {
        System.out.println("Niepoprawny format danych!");
      }
    }
  }

  private static Params getParams() {
    while (true) {
      try {
        System.out.println("pop_size:");
        int populationSize = Integer.parseInt(sc.nextLine()); // 1500
        System.out.println("generations:");
        int numberOfPopulation = Integer.parseInt(sc.nextLine()); // 250
        System.out.println("Pe (elite):");
        double elitePercentage = Double.parseDouble(sc.nextLine()); // 0.04
        System.out.println("Px:");
        double crossPercentage = Double.parseDouble(sc.nextLine()); // 0.75
        System.out.println("Pm:");
        double mutatePercentage = Double.parseDouble(sc.nextLine()); // 0.2
        System.out.println("tour:");
        int tour = Integer.parseInt(sc.nextLine()); // 10
        return new Params(populationSize, numberOfPopulation, elitePercentage, crossPercentage, mutatePercentage, tour);
      } catch (Exception e) {
        System.out.println("Niepoprawny format danych!");
      }
    }
  }
}
