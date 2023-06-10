import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Driver {
  public static void main(String [] args) throws Exception {
	  Polynomial p = new Polynomial();
	  System.out.println(p.evaluate(3));
	  
	  double [] c1 = {6, 5};
	  int [] ex1 = {0, 3};
	  Polynomial p1 = new Polynomial(c1, ex1);
	  double [] c2 = {-2, -9};
	  int [] ex2 = {1, 4};
	  Polynomial p2 = new Polynomial(c2, ex2);
	  Polynomial s = p1.add(p2);
	  System.out.println("s(0.1) = " + s.evaluate(0.1));
	  
	  if(s.hasRoot(1))
		  	System.out.println("1 is a root of s");
	  else
		  	System.out.println("1 is not a root of s");
	  
	  Polynomial m = p1.multiply(p2);
	  System.out.println("m(0.1) = " + m.evaluate(0.1));
	  
	  double [] c3 = {-2, -5, 4};
	  int [] ex3 = {0, 2, 3};
	  Polynomial data = new Polynomial(c3, ex3);
	  data.saveToFile("mypolynomial.txt");
	  BufferedReader check = new BufferedReader(new FileReader(".//mypolynomial.txt"));
	  String line = check.readLine();
	  System.out.println(line);
	  
	  Polynomial r = new Polynomial(new File(".//mypolynomial.txt"));
	  System.out.println("r(0.1) = " + r.evaluate(0.1));

	  Polynomial n1 = p1.add(p);
	  System.out.println("n1(0.1) is " + n1.evaluate(0.1));
	  Polynomial n2 = p2.multiply(p);
	  System.out.println("n2(0.1) is " + n2.evaluate(0.1));
  }
}