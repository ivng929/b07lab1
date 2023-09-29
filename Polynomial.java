import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

public class Polynomial {
	double [] coefficients;
	int [] exponents;
	
	public Polynomial() {
	}
	
	public Polynomial(double [] c, int [] e) {
		this.coefficients = c;
		this.exponents = e;
	}
	
	public Polynomial(File file) throws Exception {
		BufferedReader my_file = new BufferedReader(new FileReader(file));
		String line = my_file.readLine();
		//replace "-" with "+-"
		String [] minus_parts = line.split("\\-");
		String new_line = "";
		for(String mp:minus_parts) {
			if(mp != "") {
				new_line = new_line + "+-" + mp;
			}
		}
		// split each part by "+"
		String [] parts = new_line.split("\\+");
		this.coefficients = new double [parts.length];
		this.exponents = new int [parts.length];
		int index = 0;
		for(String part:parts) {
			if(part != "") {
				String [] values = part.split("x");
				if(values.length == 1) {
					this.coefficients[index] = Double.parseDouble(values[0]);
					this.exponents[index] = 0;
					index++;
				}
				if(values.length == 2) {
					if(values[0] == "") {
						this.coefficients[index] = 1;
					}
					else {
						this.coefficients[index] = Double.parseDouble(values[0]);
					}
					if(values[1] == "") {
						this.exponents[index] = 1;
					}
					else {
						this.exponents[index] = Integer.parseInt(values[1]);
					}
					index++;
				}
			}
			
		}
	}
	
	public Polynomial add(Polynomial other) {
		Polynomial result;
		if(this.exponents == null && other.exponents == null) {
			result = new Polynomial();
			return result;
		}
		if(this.exponents == null) {
			result = new Polynomial(other.coefficients, other.exponents);
			return result;
		}
		if(other.exponents == null) {
			result = new Polynomial(this.coefficients, this.exponents);
			return result;
		}
		
		// otherwise, find the length of the result
		int result_len = this.exponents.length;
		for(int i=0; i<other.exponents.length; i++) {
			boolean not_in_this = true;
			for(int j = 0; j<this.exponents.length; j++) {
				if(this.exponents[j] == other.exponents[i]) {
					not_in_this = false;
				}
			}
			if(not_in_this) {
				result_len++;
			}
		}
		
		// initialize result
		int [] result_exponents = new int [result_len];
		int result_exp_index = 0;
		for(int i=0; i<this.exponents.length; i++) {
			result_exponents[i] = this.exponents[i];
			result_exp_index++;
		}
		for(int i=0; i<other.exponents.length; i++) {
			boolean not_in_this = true;
			for(int j = 0; j<this.exponents.length; j++) {
				if(this.exponents[j] == other.exponents[i]) {
					not_in_this = false;
				}
			}
			if(not_in_this) {
				result_exponents[result_exp_index] = other.exponents[i];
				result_exp_index++;
			}
		}
		
		//sorting result_exponents
		int temp;
		for(int i = result_exponents.length - 1; i >= 0; i=i-1) {
			for(int j = 0; j < i; j++) {
				if(result_exponents[j] > result_exponents[j+1]) {
					temp = result_exponents[j];
					result_exponents[j] = result_exponents[j + 1];
					result_exponents[j + 1] = temp;
				}
			}
		}
		
		// setting result coefficients
		double [] result_coefficients = new double [result_exponents.length];
		for(int i=0; i<result_exponents.length; i++) {
			for(int j = 0; j<this.exponents.length; j++) {
				if(result_exponents[i] == this.exponents[j]) {
					result_coefficients[i] = this.coefficients[j];
				}
			}
		}
		
		for(int i=0; i<result_exponents.length; i++) {
			for(int j = 0; j<other.exponents.length; j++) {
				if(result_exponents[i] == other.exponents[j]) {
					result_coefficients[i] += other.coefficients[j];
				}
			}
		}
		
		//clearing all zero coefficients in result
		int final_len = result_coefficients.length;
		for(int i=0; i<result_coefficients.length; i++) {
			if(result_coefficients[i]==0) {
				final_len+=-1;
			}
		}
		double [] final_cef = new double [final_len];
		int [] final_exp = new int [final_len];
		int index = 0;
		for(int i=0; i<result_coefficients.length && index < final_len; i++) {
			if(result_coefficients[i]!=0) {
				final_cef[index] = result_coefficients[i];
				final_exp[index] = result_exponents[i];
				index++;
			}
		}
		Polynomial add_result = new Polynomial(final_cef, final_exp);
		return add_result;
	}
	
	public Polynomial multiply(Polynomial other) {
		Polynomial result;
		if(this.exponents == null || other.exponents == null) {
			result = new Polynomial();
			return result;
		}
		result = new Polynomial();
		Polynomial a;
		for(int i=0; i<this.exponents.length; i++) {
			double [] cef = new double [other.coefficients.length];
			int [] exp = new int [other.exponents.length];
			for(int j=0; j<other.coefficients.length; j++) {
				cef[j] = this.coefficients[i]*other.coefficients[j];
			}
			for(int k=0; k<other.exponents.length; k++) {
				exp[k] = this.exponents[i] + other.exponents[k];
			}
			a = new Polynomial(cef, exp);
			result = result.add(a);
		}
		return result;
	}
	
	public double evaluate(double x) {
		if (this.exponents == null) {
			return 0;
		}
		double result = 0;
		for(int i=0; i<this.coefficients.length; i++) {
			result = result + (this.coefficients[i] * Math.pow(x, exponents[i]));
		}
		return result;
	} 
	
	public boolean hasRoot(double root) {
		if(this.evaluate(root)==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void saveToFile(String name) throws Exception {
		String content = "";
		if(this.coefficients != null) {
			// first part
			if(this.exponents[0] != 0) {
				content = coefficients[0] + "x" + exponents[0];
			}
			else {
				content = coefficients[0] + "";
			}
			// other parts
			for(int i=1; i<this.coefficients.length; i++) {
				if(this.exponents[i] != 0) {
					if(this.coefficients[i] > 0) {
					    content = content + "+" + coefficients[i] + "x" + exponents[i];
					}
					else if(this.coefficients[i] < 0) {
						content = content + coefficients[i] + "x" + exponents[i];
					}
				}
				else {
					if(this.coefficients[i] > 0) {
						content = content + "+" + coefficients[i];
					}
					else if(this.coefficients[i] < 0) {
						content = content + "" + coefficients[i];
					}
				}
			}
		}
		File output = new File(name);
		PrintStream ps = new PrintStream(output);
		ps.print(content);
		ps.close();
	}
}