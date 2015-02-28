import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PopulationAnalyze {
	public class Population {
		public String city;
		public String state;
		public int pop2010;
		public int pop2011;
		public int pop2012;
		public double growth = Double.MIN_VALUE;
		public double shrink = Double.MIN_VALUE;

		public Population(String city, String state, int pop2010, int pop2011,
				int pop2012) {
			this.city = city;
			this.state = state;
			this.pop2010 = pop2010;
			this.pop2011 = pop2011;
			this.pop2012 = pop2012;

			growth = (pop2012 - pop2010) * 100.0 / pop2010;
			shrink = (pop2010 - pop2012) * 100.0 / pop2010;
		}

		public Population() {
		}

	}

	public class State {
		public String state;
		public int pop2010;
		public int pop2011;
		public int pop2012;
		public double growth = Double.MIN_VALUE;

		public State(String state) {
			this.state = state;
		}

		public State() {

		}

		public void calculateGrowth() {
			growth = (pop2012 - pop2010) * 100.0 / pop2010;
		}
	}

	public static void main(String[] args) {
		PopulationAnalyze obj = new PopulationAnalyze();
		obj.run();

	}

	public void run() {
		String csvFile = "75f647c2ac77-Metropolitan_Populations_2010-2012_.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		Population[] populations = new Population[5];
		Population[] populationsL = new Population[5];
		
		State[] states = new State[5];
	
		for (int i = 0; i < 5; i++) {
			populations[i] = new Population();
			populationsL[i] = new Population();
			states[i] = new State();
		}

		State temp = new State("");

		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				// use comma as separator
				line = line.replaceAll("\"", "");
				String[] population = line.split(cvsSplitBy);
				String city = population[0];
				String state = population[1];
				int pop2010 = Integer.parseInt(population[2]);
				int pop2011 = Integer.parseInt(population[3]);
				int pop2012 = Integer.parseInt(population[4]);
				Population population1 = new Population(city, state, pop2010,
						pop2011, pop2012);
		
				if (temp.state.equals(state)) {
					temp.pop2010 += pop2010;
					temp.pop2011 += pop2011;
					temp.pop2012 += pop2012;
				} else {
					temp.calculateGrowth();

					if (states[0].growth < temp.growth) {
						for (int i = 4; i >= 1; i--)
							states[i] = states[i - 1];
						states[0] = temp;
					} else if (states[1].growth < temp.growth) {
						for (int i = 4; i >= 2; i--)
							states[i] = states[i - 1];
						states[1] = temp;
					} else if (states[2].growth < temp.growth) {
						for (int i = 4; i >= 3; i--)
							states[i] = states[i - 1];
						states[2] = temp;
					} else if (states[3].growth < temp.growth) {
						states[4] = states[3];
						states[3] = temp;
					} else if (states[4].growth < temp.growth)
						states[4] = temp;
					State st = new State(state);
					st.pop2010 = pop2010;
					st.pop2011 = pop2011;
					st.pop2012 = pop2012;
					temp = st;

				}

				if (populations[0].growth < population1.growth) {
					for (int i = 4; i >= 1; i--)
						populations[i] = populations[i - 1];
					populations[0] = population1;
				} else if (populations[1].growth < population1.growth) {
					for (int i = 4; i >= 2; i--)
						populations[i] = populations[i - 1];
					populations[1] = population1;
				} else if (populations[2].growth < population1.growth) {
					for (int i = 4; i >= 3; i--)
						populations[i] = populations[i - 1];
					populations[2] = population1;
				} else if (populations[3].growth < population1.growth) {
					populations[4] = populations[3];
					populations[3] = population1;
				} else if (populations[4].growth < population1.growth)
					populations[4] = population1;

				if (populationsL[0].shrink < population1.shrink) {
					for (int i = 4; i >= 1; i--)
						populationsL[i] = populationsL[i - 1];
					populationsL[0] = population1;
				} else if (populationsL[1].shrink < population1.shrink) {
					for (int i = 4; i >= 2; i--)
						populationsL[i] = populationsL[i - 1];
					populationsL[1] = population1;
				} else if (populationsL[2].shrink < population1.shrink) {
					for (int i = 4; i >= 3; i--)
						populationsL[i] = populationsL[i - 1];
					populationsL[2] = population1;
				} else if (populationsL[3].shrink < population1.shrink) {
					populationsL[4] = populationsL[3];
					populationsL[3] = population1;
				} else if (populationsL[4].shrink < population1.shrink)
					populationsL[4] = population1;
			}

			System.out.println("List of Cities with higher growth rate");
			System.out.println("---------------------------------------");
			for (int i = 0; i < 5; i++)
				System.out.println(populations[i].city + "\tGrowth Rate : "
						+ populations[i].growth);
			System.out.println();
			System.out.println("List of Cities with higher shrink List");
			System.out.println("---------------------------------------");
			for (int i = 0; i < 5; i++)
				System.out.println(populationsL[i].city + "\tShrink Rate : "
						+ populationsL[i].shrink);
			System.out.println();
			System.out.println("List of States with higher growth List");
			System.out.println("---------------------------------------");
			for (int i = 0; i < 5; i++)
				System.out.println(states[i].state + "\tGrowth Rate : " + states[i].growth);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
