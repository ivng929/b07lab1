public class Polynomial {
	double [] coefficients;
	public Polynomial() {
		coefficients = new double[] {0};
	}
	public Polynomial(double [] value) {
		coefficients = value;
	}
	
	Polynomial add(Polynomial that) {
		Polynomial result = new Polynomial();
		if (this.coefficients.length >= that.coefficients.length) {
			result.coefficients = this.coefficients;
			for(int i=0; i < that.coefficients.length; i++) {
				result.coefficients[i] += that.coefficients[i];
			}
		}
		else {
			result.coefficients = that.coefficients;
			for(int i=0; i < this.coefficients.length; i++) {
				result.coefficients[i] += this.coefficients[i];
			}
		}
		return result;
	}
	
	double evaluate(double x) {
		double result = 0;
		for(int i=0; i<this.coefficients.length; i++) {
			result = result + (this.coefficients[i] * Math.pow(x, i));
		}
		return result;
	} 
	
	boolean hasRoot(double root) {
		if(this.evaluate(root)==0) {
			return true;
		}
		else {
			return false;
		}
	}
}