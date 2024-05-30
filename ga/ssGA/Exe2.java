///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

public class Exe2 {
    public static void main(String args[]) throws Exception {
        int gn = 512;
        int gl = 1;
        int popsize = 512;
        double tf = (double) gn * gl;
        long MAX_ISTEPS = 50000;

        Problem problem = new ProblemOneMax();
        problem.set_geneN(gn);
        problem.set_geneL(gl);
        problem.set_target_fitness(tf);

        int numRuns = 30;
        int[] iterationsToSolution = new int[numRuns];
        double[][] resultsMatrix = new double[numRuns][3]; // Columna 0: probabilidad de cruce, Columna 1: probabilidad de mutación, Columna 2: mejor aptitud

        // Estudio de distintos parámetros de mutación y cruce
        double[] pcValues = {0.6, 0.7, 0.8};
        double[] pmValues = {0.01, 0.05, 0.1};

        for (double pc : pcValues) {
            for (double pm : pmValues) {
              System.out.println("Running with pc=" + pc + ", pm=" + pm);
              for (int run = 0; run < numRuns; run++) {
                    Algorithm ga = new Algorithm(problem, popsize, gn, gl, pc, pm);

                    for (int step=0; step<MAX_ISTEPS; step++)
                    {  
                      ga.go_one_step();
                      //System.out.print(step); System.out.print("  ");
                      //System.out.println(ga.get_bestf());

                      if(     (problem.tf_known())                    &&
                      (ga.get_solution()).get_fitness()>=problem.get_target_fitness()
                      )
                      { System.out.print("Solution Found! After ");
                        System.out.print(problem.get_fitness_counter());
                        System.out.println(" evaluations");
                        break;
                      }
                    }

                    resultsMatrix[run][0] = pc;
                    resultsMatrix[run][1] = pm;
                    resultsMatrix[run][2] = ga.get_bestf();

                    // Reiniciar el algoritmo para una nueva ejecución
                    ga = new Algorithm(problem, popsize, gn, gl, pc, pm);
                }
                
                // Mostrar resultados para esta combinación de parámetros
                for (int run = 0; run < numRuns; run++) {
                    System.out.println("Run " + (run + 1) + ": pc=" + resultsMatrix[run][0] + ", pm=" + resultsMatrix[run][1] + ", Iterations=" + iterationsToSolution[run] + ", Best Fitness=" + resultsMatrix[run][2]);
                }
            }
        }
    } 
}
