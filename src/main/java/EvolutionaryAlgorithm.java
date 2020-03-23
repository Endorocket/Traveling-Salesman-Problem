import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EvolutionaryAlgorithm {

  private final List<City> cities;

  private final BufferedWriter writer;

  private final int populationSize;
  private final int numberOfPopulation;
  private final double elitePercentage;
  private final double crossPercentage;
  private final double mutatePercentage;
  private final int tour;

  public EvolutionaryAlgorithm(List<City> cities, Params params, BufferedWriter writer) {
    this.cities = cities;
    this.populationSize = params.getPopulationSize();
    this.numberOfPopulation = params.getNumberOfPopulation();
    this.crossPercentage = params.getCrossPercentage();
    this.mutatePercentage = params.getMutatePercentage();
    this.tour = params.getTour();
    this.elitePercentage = params.getElitePercentage();
    this.writer = writer;
  }

  public void run() {
    List<List<Individual>> populations = new ArrayList<>(numberOfPopulation);

    List<Individual> firstPopulation = initialize();
    populations.add(firstPopulation);
    System.out.println("Populacja 1:");
    evaluate(firstPopulation, 1);

    for (int i = 0; i < numberOfPopulation - 1; i++) {
      if (i > 0) {
        populations.set(i - 1, null);
      }
      List<Individual> elite = elite(populations.get(i));
      List<Individual> nextPopulation = selection(populations.get(i));
      nextPopulation = crossover(nextPopulation);
      mutation(nextPopulation);
      nextPopulation.addAll(elite);

      System.out.println("\nPopulacja " + (i + 2) + ":");
      evaluate(nextPopulation, i + 2);
      populations.add(nextPopulation);
    }
  }

  private List<Individual> initialize() {
    List<Individual> population = new ArrayList<>(new ArrayList<>(populationSize));
    for (int i = 0; i < populationSize; i++) {
      List<City> shuffledCities = new ArrayList<>(cities);
      Collections.shuffle(shuffledCities);
      Individual individual = new Individual(shuffledCities);
      population.add(individual);
    }
    return population;
  }

  private void evaluate(List<Individual> population, int i) {
    double best = Double.MAX_VALUE;
    double worst = Double.MIN_VALUE;
    double sum = 0;
    double sumOfSquares = 0;

    Individual bestSolution = null;

    for (Individual individual : population) {
      double distance = individual.getDistance();
      sum += distance;
      sumOfSquares += Math.pow(distance, 2);

      if (best > distance) {
        best = distance;
        bestSolution = individual;
      } else if (worst < distance)
        worst = distance;
    }
    double avg = sum / population.size();
    double standardDev = Math.sqrt(sumOfSquares / population.size() - Math.pow(avg, 2));

    System.out.println("Best: " + (int) best);
    System.out.println("Worst: " + (int) worst);
    System.out.println("Avg: " + (int) avg);
    System.out.println("Stand Dev: " + (int) standardDev);

    try {
      writer.write(i + "; " + (int) best + "; " + (int) avg + "; " + (int) worst + ";");
      writer.newLine();
      if (i == numberOfPopulation) {
        writer.write(bestSolution.getInfo());
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<Individual> elite(List<Individual> population) {
    int eliteSize = (int) Math.floor(populationSize * elitePercentage);
    population.sort(Comparator.comparing(Individual::quality).reversed());
    List<Individual> selected = new ArrayList<>(eliteSize);
    for (int i = 0; i < eliteSize; i++) {
      selected.add(population.get(i));
    }
    return new ArrayList<>(selected);
  }

  private List<Individual> selection(List<Individual> population) {
    int selectionSize = (int) Math.ceil(populationSize - (populationSize * elitePercentage));
    List<Individual> selected = new ArrayList<>(selectionSize);
    for (int i = 0; i < selectionSize; i++) {
      List<Individual> tour = singleTour(population);
      selected.add(findBest(tour));
    }
    return selected;
  }

  private List<Individual> singleTour(List<Individual> individuals) {
    List<Individual> selectedToTour = new ArrayList<>(tour);
    for (int j = 0; j < tour; j++) {
      int randomInt = Utils.getRandomInt(individuals.size() - 1);
      selectedToTour.add(individuals.get(randomInt));
    }
    return selectedToTour;
  }

  private Individual findBest(List<Individual> tour) {
    return tour.stream()
        .max(Comparator.comparing(Individual::quality))
        .map(individual -> new Individual(individual.getCities()))
        .get();
  }

  private List<Individual> crossover(List<Individual> individuals) {
    List<Individual> crossed = new ArrayList<>(individuals.size());
    while (!individuals.isEmpty()) {
      Individual firstParent = individuals.remove(Utils.getRandomInt(individuals.size() - 1));
      Individual secondParent = individuals.remove(Utils.getRandomInt(individuals.size() - 1));

      if (Utils.getRandomInt(100) < crossPercentage * 100) {
        for (int j = 0; j < 2; j++) {
          int individualSize = firstParent.getCities().size();
          int p1 = Utils.getRandomInt(individualSize - 4);
          int p2 = Utils.getRandomInt(p1, individualSize);

          List<City> p1Sub = new ArrayList<>(firstParent.getCities().subList(p1, p2));
          List<City> p2Cities = new ArrayList<>(secondParent.getCities());
          p2Cities.removeAll(p1Sub);

          List<City> childCities = new ArrayList<>(individualSize);
          for (int i = 0; i < p1; i++) {
            childCities.add(p2Cities.remove(0));
          }
          childCities.addAll(p1Sub);
          childCities.addAll(p2Cities);

          Individual child = new Individual(childCities);
          crossed.add(child);
        }
      } else {
        Individual firstChild = new Individual(firstParent.getCities());
        Individual secondChild = new Individual(secondParent.getCities());
        crossed.add(firstChild);
        crossed.add(secondChild);
      }
    }
    return crossed;
  }

  private void mutation(List<Individual> individuals) {
    for (Individual individual : individuals) {
      if (Utils.getRandomInt(100) < mutatePercentage * 100) {
        individual.mutate();
      }
    }
  }
}
