package ga.ssGA;

import ga.ssGA.Algorithm;
import ga.ssGA.Problem;
import ga.ssGA.ProblemOneMax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Exe3
{
 public static void main(String args[]) throws Exception
 {
    // PARAMETERS ONEMAX
    int    gn         = 512;                         // Gene number
    int    gl         = 1;                            // Gene length
    int    popsize    = 512;                          // Population size
    double tf         = (double)gn*gl ;           // Target fitness being sought
    long   MAX_ISTEPS = 50000;
    
    Problem   problem;                             // The problem being solved

    // problem = new ProblemPPeaks(); 

    // Estudio de distintos parámetros de mutación y cruce
    double[] pcValues = {0.6, 0.7, 0.8, 0.9};
    double[] pmValues = {1.0/(double)((double)gn*(double)gl),0.01, 0.05, 0.1, 0.2};
    
    Map<String, ArrayList<Double>> resultsMap = new HashMap<>();

    for (double pc : pcValues) {
      for (double pm : pmValues) {
        
        System.out.println("Running with pc=" + pc + ", pm=" + pm);
        for (int run = 0; run < 30; run++) {
          problem = new ProblemOneMax();
              
          problem.set_geneN(gn);
          problem.set_geneL(gl);
          problem.set_target_fitness(tf);
          Algorithm ga;          // The ssGA being used
          ga = new Algorithm(problem, popsize, gn, gl, pc, pm);
          for (int step=0; step<MAX_ISTEPS; step++)
          {  
            ga.go_one_step();
            System.out.print(step); System.out.print("  ");
            System.out.println(ga.get_bestf());

            if(     (problem.tf_known())                    &&
            (ga.get_solution()).get_fitness()>=problem.get_target_fitness()
            )
            { System.out.print("Solution Found! After ");
              System.out.print(problem.get_fitness_counter());
              System.out.println(" evaluations");
              break;
            }

          }

          // Save the result for each run
          String key = "pc=" + pc + ", pm=" + pm;
          if (!resultsMap.containsKey(key)) {
            resultsMap.put(key, new ArrayList<>());
          }
          resultsMap.get(key).add((ga.get_solution()).get_fitness());
        }
      }
    }

    // Print the average results for each combination of parameters
    for (String key : resultsMap.keySet()) {
      ArrayList<Double> results = resultsMap.get(key);
      Collections.sort(results);
      double average = results.stream().mapToDouble(d -> d).average().orElse(Double.NaN);
      System.out.println("Average fitness for " + key + ": " + average);
    }

 }

}