import java.util.Scanner;
import java.io.*;

public class Project2
{
        //GaussSeidel's method
        static void gaussSeidel(double A[][], double x[],double err)
        {
                int maxIteration = 50;  //set max iterations to 50;
                double maxError = 0;
                int n = x.length;
                //repeat iterations until max iterations
                for(int k=1; k<=maxIteration; k++)
                {
                        maxError = 0.0;                        
                        for(int i=0; i<n; i++)
                        {
                                double sum = 0.0;
                                //calculating the sum
                                for(int j=0; j<n; j++)
                                {
                                        if(i!=j)
                                                sum = sum + A[i][j]*x[j];
                                }
                                //current value of x(i) and calculate 
                                double temp = (A[i][n] - sum)/A[i][i];
                                //calculating error
                                double relativeError = Math.abs((x[i]-temp)/temp);
                                
                                if(relativeError > maxError) 
                                        maxError = relativeError;
                                
                                //updating new value of x[i] in vector x
                                //updating value of x for gaussSeidel method is easier
                                x[i] = temp;
                        }
                        
                        //printing each iteration solution
                        System.out.print("\nIteration_"+k+": [ ");
                        for(int i=0; i<n ;i++)
                        {
                                System.out.print(String.format("%.4f", x[i])+" ");
                        }
                        System.out.print("]");
                        
                        //stop the calculation if converges before max iterations
                        if(maxError < err) 
                        {
                                System.out.println("\nConverged\n");
                                return;
                        }
                }
                System.out.println("Does not converge for max iteration 50 : error was not reached! ");
        }


        //Jacobi's method
        static void jacobi(double A[][], double x[],double err)
        {
                int maxIteration = 50;  //set max iterations to 50;
                double maxError = 0;
                int n = x.length;
                double xTemp[] = new double[n];
                for(int i=0; i<n; i++) 
                        xTemp[i] = 0;
                
                //repeat till max iterations
                for(int k=1; k<=maxIteration; k++)
                {
                        maxError=0.0;
                        for(int i=0; i<n; i++)
                        {
                                double sum = 0.0;
                                //calculating the sum
                                for(int j=0; j<n; j++)
                                {
                                        if(i!=j)
                                                sum = sum + A[i][j]*x[j];
                                }
                                //current value of x(i) and calculate
                                double temp = (A[i][n] - sum)/A[i][i];
                                
                                double relativeError = Math.abs((x[i]-temp)/temp);
                                
                                if(relativeError > maxError) 
                                        maxError = relativeError;
                                
                                //updating this new value of x[i] so in Jacobi's method the values of x don't update with in same the iteration.
                                xTemp[i] = temp;
                        }
                        
                        //after each iteration update the values of x from the xTemp vector
                        for(int i=0; i<n; i++)
                        {
                                x[i] = xTemp[i];
                        }
                        //printing each iteration solution
                        System.out.print("\nIteration_"+k+": [ ");
                        for(int i=0; i<n; i++)
                        {
                                System.out.print(String.format("%.4f", x[i])+" ");
                        }
                        System.out.print("]");
                        //stop the calculation if converges before max iterations
                        if(maxError < err) 
                        {
                                System.out.println("\nConverged\n");
                                return;
                        }
                }
                System.out.println("Does not converge for max iteration 50 : error was not reached! ");
        }
        
        public static void main(String args[])
        {
                int n;
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter the number of linear equations: ");
                n = sc.nextInt();
                
                if(n < 2 || n >10){
                        System.out.println("Enter n between 2 and 10");
                        return;
                }
                
                //allocating memory to the vectors and matrix;
                double A[][] = new double[n][n+1];  //for Augmented matrix A|B
                
                double x[] = new double[n];

                System.out.println("1.Enter Coefficients from command line");
                System.out.println("2.Enter the file name to get the coefficients");
                System.out.println("Select an option: ");
                int option = sc.nextInt();
                switch(option)
                {
                        case 1:
                        		System.out.println("Enter equation by row: ");
                                for(int i=0; i<n ;i++)
                                {
                                        for(int j=0; j<=n; j++)
                                        {
                                                A[i][j]=sc.nextInt();
                                        }
                                }
                                break;
                        case 2:
                                System.out.println("Enter the file name to take the coefficients of linear equations including b: ");
                                try{
                                		//to open file and check file
                                        sc.nextLine();
                                        String filename;
                                        filename = sc.nextLine();
                                        File file = new File(filename); 
                                        BufferedReader br = new BufferedReader(new FileReader(file)); 

                                        String line; 
                                        int i=0;
                                        while ((line = br.readLine()) != null) {
                                                String []tokens = line.split(" ");
                                                for(int j=0; j<n+1; j++)
                                                {
                                                        A[i][j] = Double.parseDouble(tokens[j]);
                                                }
                                                i++;
                                        }
                                }catch(Exception e){
                                        System.out.println("Unable to open the file specified");
                                        return;
                                }
                                break;
                        default :
                                System.out.println("Please select a right option");
                                return;
                }
                System.out.println("Enter the desired stopping error: ");
                double err = sc.nextDouble();
                System.out.println("Enter the starting solution for the iterative methods: ");
                for(int i=0; i<n; i++)
                {
                        System.out.print("X["+(i+1)+"] : ");
                        x[i] = sc.nextDouble();
                }
                
                
                //Print all matrix given
                System.out.println("Displaying All the inputs from the User: ");
                System.out.println("Matrix A:");
                for(int i=0; i<n ;i++)
                {
                        for(int j=0; j<n; j++)
                        {
                                System.out.print(A[i][j]+"\t");
                        }
                        System.out.println();
                }
                
                System.out.println("\nMatrix B: ");
                for(int i=0; i<n ;i++)
                {
                        System.out.println(A[i][n]);
                }
                
                System.out.println("\nStopping error: "+err);
                System.out.print("Starting solution: [ ");
                for(int i=0; i<n ;i++){
                        System.out.print(x[i]+" ");
                }
                System.out.print("]\n\n");
                
                //getting the starting values of x in temp and put it in the data to keep it as it is;
                double temp[]  = new double[n];
                for(int i=0; i<n; i++)
                {
                        temp[i] = x[i];
                }
                System.out.println("--------------------Jocobi method--------------------------");
                System.out.println("Displaying Solutions for each iteration Using Jacobi method : ");
                jacobi(A,temp,err);
                
                System.out.println("--------------------Gauss Seidel method----------------------");
                System.out.println("Displaying Solutions for each iteration Using Gauss Seidel method : ");
                //initializing the values of temp to x vector for GaussSeidel method
                for(int i=0; i<n; i++)
                {
                        temp[i] = x[i];
                }
                gaussSeidel(A,temp,err);
        }
}