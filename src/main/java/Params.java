public class Params {
  private int populationSize = 1500;
  private int numberOfPopulation = 250;
  private double elitePercentage = 0.04;
  private double crossPercentage = 0.75;
  private double mutatePercentage = 0.2;
  private int tour = 10;

  public Params(int populationSize, int numberOfPopulation, double elitePercentage, double crossPercentage, double mutatePercentage, int tour) {
    this.populationSize = populationSize;
    this.numberOfPopulation = numberOfPopulation;
    this.elitePercentage = elitePercentage;
    this.crossPercentage = crossPercentage;
    this.mutatePercentage = mutatePercentage;
    this.tour = tour;
  }

  public int getPopulationSize() {
    return populationSize;
  }

  public void setPopulationSize(int populationSize) {
    this.populationSize = populationSize;
  }

  public int getNumberOfPopulation() {
    return numberOfPopulation;
  }

  public void setNumberOfPopulation(int numberOfPopulation) {
    this.numberOfPopulation = numberOfPopulation;
  }

  public double getElitePercentage() {
    return elitePercentage;
  }

  public void setElitePercentage(double elitePercentage) {
    this.elitePercentage = elitePercentage;
  }

  public double getCrossPercentage() {
    return crossPercentage;
  }

  public void setCrossPercentage(double crossPercentage) {
    this.crossPercentage = crossPercentage;
  }

  public double getMutatePercentage() {
    return mutatePercentage;
  }

  public void setMutatePercentage(double mutatePercentage) {
    this.mutatePercentage = mutatePercentage;
  }

  public int getTour() {
    return tour;
  }

  public void setTour(int tour) {
    this.tour = tour;
  }

  @Override
  public String toString() {
    return "pop_size: " + populationSize + ", generations: " + numberOfPopulation + ", Px: " + crossPercentage + ", Pm: " + mutatePercentage + ", Tour: " + tour + ", Elite: " + elitePercentage;
  }
}
