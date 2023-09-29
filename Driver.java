import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

public class Driver {
  public static void main(String [] args) throws Exception {
	  //check non-arg constructor
	  Polynomial p = new Polynomial();
	  System.out.println(p.evaluate(4));
	  
	  //check method add()
	  double [] c1 = {2, -1, 3};
	  int [] ex1 = {0, 1, 2};
	  Polynomial p1 = new Polynomial(c1, ex1);
	  double [] c2 = {-5, -1};
	  int [] ex2 = {1, 3};
	  Polynomial p2 = new Polynomial(c2, ex2);
	  Polynomial s = p1.add(p2);
	  System.out.println("s(0.1) = " + s.evaluate(0.1));
	  //expected s(0.1): 1.429
	  
	  //check method hasRoot()
	  if(s.hasRoot(1))
		  	System.out.println("1 is a root of s");
	  else
		  	System.out.println("1 is not a root of s");
	  
	  //check method multiply()
	  Polynomial m = p1.multiply(p2);
	  System.out.println("m(0.1) = " + m.evaluate(0.1));
	  //expected m(0.1): -0.96693
	  
	  //check saveToFile
	  double [] c3 = {-2, 5,-1, 4};
	  int [] ex3 = {0, 2, 3, 5};
	  Polynomial p3 = new Polynomial(c3, ex3);
	  p3.saveToFile("myfile.txt");
	  BufferedReader myfile = new BufferedReader(new FileReader(".//myfile.txt"));
	  String line = myfile.readLine();
	  System.out.println(line);
	  
	  //check constructor with File arg
	  Polynomial r = new Polynomial(new File(".//myfile.txt"));
	  System.out.println("r(0.1) = " + r.evaluate(0.1));
	  //expected r(0.1): -1.95096
	  
  }
}