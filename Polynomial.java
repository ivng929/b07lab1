import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class Polynomial{
	
	private double[] coefficient;
	private int[] exponent;
	
	public Polynomial() {
		coefficient = null;
		exponent = null;
	}
	
	public Polynomial(double[] init_coefficient, int[] init_exponent) {
		coefficient = init_coefficient;
		exponent = init_exponent;
	}
	
	public Polynomial(File file) throws Exception {
		BufferedReader b = new BufferedReader(new FileReader(file));
		String line = b.readLine();
		String [] negativeTerms = line.split("\\-");
		String nLine = "";
		for (String nterm:negativeTerms) {
			if (nterm != "") {
			nLine = nLine + "+-" + nterm; } 
		}
		String [] terms = nLine.split("\\+");
		coefficient = new double [terms.length];
		exponent = new int [terms.length];
		int index = 0;
		for (String term:terms) {
			if(term != "") {
			String [] nums = term.split("x");
			int i=0;
			for (String num:nums) {
			  if (num != "") {
				if (i==0) {
					if (num == "") {this.coefficient[index] = 1;}
					else {this.coefficient[index] = Double.parseDouble(num);}
				}
				else {
					if (num == "") {this.exponent[index] = 0;}
					else {this.exponent[index] = Integer.parseInt(num);}
				}
				i++;
			  }
			}
			index++;
		  }
		}
	}
	
	public Polynomial add(Polynomial that) {
		if (coefficient == null && that.coefficient == null) {
			Polynomial theSum = new Polynomial();
			return theSum;
		}
		if (coefficient == null) {
			Polynomial theSum = new Polynomial(that.coefficient, that.exponent);
			return theSum;
		}
		if (that.coefficient == null) {
			Polynomial theSum = new Polynomial(coefficient, exponent);
			return theSum;
		}
		
		int len = exponent.length;
		for (int l=0; l<that.exponent.length; l++) {
			int exist = 0;
			for (int k=0; k<exponent.length; k++) {
				if (that.exponent[l] == exponent[k]) {
					exist = 1;
				}
			}
			if (exist == 0) {
				len++;
			}
		}
		
		int[] result_exp = new int [len];
		int r=0;
		for (int i=0; i<exponent.length; i++) {
			result_exp[i] = exponent[i];
			r++;
		}	
		int j=0;
		while (j<that.exponent.length) {
			int exist2=0;
			for (int z=0; z<exponent.length; z++) {
				if (that.exponent[j]==exponent[z]) {exist2=1;}
			}
			if (exist2==0) {
				result_exp[r] = that.exponent[j];
				r++;
			}
			j++;
		}
		
		int len2 = result_exp.length;
		double[] result_coef = new double [len2];
		
		for (int n=0; n<result_exp.length; n++) {
			for (int c=0; c<exponent.length; c++) {
				if (result_exp[n]==exponent[c]) {
					result_coef[n] = coefficient[c];
				}
			}
		}
		for (int n=0; n<result_exp.length; n++) {
			for (int d=0; d<that.exponent.length; d++) {
				if (result_exp[n]==that.exponent[d]) {
					result_coef[n] = result_coef[n] + that.coefficient[d];
				}
			}
		}
		
		Polynomial theSum = new Polynomial(result_coef, result_exp);
		return theSum;
	}
	
	public double evaluate(double x) {
		if (coefficient == null) {
			return 0;
		}
		double result = 0;
		for (int i = 0; i<coefficient.length; i++) {
			result = result + coefficient[i]*(Math.pow(x, exponent[i]));
		}
		return result;
	}
	
	public boolean hasRoot(double r) {
		double evaluation = this.evaluate(r);
		if (evaluation == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Polynomial multiply(Polynomial that) {
		if (this.coefficient == null || that.coefficient == null) {
			Polynomial empty = new Polynomial();
			return empty;
		}
		
		Polynomial result = new Polynomial();
		Polynomial p;
		for (int i=0; i<this.exponent.length; i++) {
			int[] expo = new int[that.exponent.length];
			double[] co = new double[that.exponent.length];
			for (int j=0; j<that.exponent.length; j++) {
				expo[j] = that.exponent[j]+this.exponent[i];
			}
			for (int h=0; h<that.coefficient.length; h++) {
				co[h] = that.coefficient[h]*this.coefficient[i];
			}
			p = new Polynomial(co, expo);
			result = result.add(p);
		}
		return result;
	}
	
	public void saveToFile(String filename) throws Exception {
		String toSave = "";
		if (this.coefficient != null) {
			if (exponent[0] != 0) {
			toSave = coefficient[0] + "x" + exponent[0]; }
			else {toSave = "" + coefficient[0];}
			for (int i=1; i<this.coefficient.length; i++) {
				if (exponent[i] != 0) {
					if (this.coefficient[i] >= 0) {
						toSave = toSave + "+" + coefficient[i] + "x" + exponent[i];}
					else {
						toSave = toSave + coefficient[i] + "x" + exponent[i];}
				}
				else {
					toSave = toSave+ "+" + coefficient[i];
				}
			}
		}
		String location = ".//"+ filename;
		FileWriter writer = new FileWriter(location);
		writer.write(toSave);
		writer.flush();
		writer.close();
	}
}