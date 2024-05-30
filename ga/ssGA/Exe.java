///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

public class Exe
{
  public static void main(String args[]) throws Exception
  {
/*     
    // PARAMETERS PPEAKS 
    int    gn         = 512;                           // Gene number
    int    gl         = 1;                            // Gene length
    int    popsize    = 512;                          // Population size
    double pc         = 0.8;                          // Crossover probability
    double pm  = 1.0/(double)((double)gn*(double)gl); // Mutation probability
    double tf         = (double)1 ;              // Target fitness beign sought
    long   MAX_ISTEPS = 50000;
*/
      
    // PARAMETERS ONEMAX
    int    gn         = 2048;                         // Gene number
    int    gl         = 1;                            // Gene length
    int    popsize    = 2048;                          // Population size
    //double pc         = 0.8;                          // Crossover probability
    //double pm  = 1.0/(double)((double)gn*(double)gl); // Mutation probability
    double tf         = (double)gn*gl ;           // Target fitness being sought
    long   MAX_ISTEPS = 250000;
    
    Problem   problem;                             // The problem being solved

    // problem = new ProblemPPeaks(); 

    // Estudio de distintos parámetros de mutación y cruce
    double[] pcValues = {0.8};
    double[] pmValues = {0.00001, 0.0001, 0.001, 0.01};

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

          // Print the solution
          //for(int i=0;i<gn*gl;i++)
            //System.out.print( (ga.get_solution()).get_allele(i) ); System.out.println();
          System.out.println((ga.get_solution()).get_fitness());
        }
      }
    }
  }

}
// END OF CLASS: Exe
