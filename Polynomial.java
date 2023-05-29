public class Polynomial{
	
	private double[] coef;
	
	public Polynomial() {
		coef = new double[1];
		coef[0] = 0;
	}
	
	public Polynomial(double[] init_coef) {
		coef = init_coef;
	}
	
	public Polynomial add(Polynomial B) {
		int len;
		Polynomial s;
		if(coef.length >= B.coef.length) {
			len = B.coef.length;
			s = new Polynomial(coef);
		}
		else{
			len = coef.length;
			s = new Polynomial(B.coef);
		}
		for (int i = 0; i<len; i++) {
			s.coef[i] = coef[i] + B.coef[i];
		}
		return s;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i<coef.length; i++) {
			result = result + coef[i]*(Math.pow(x, i));
		}
		return result;
	}
	
	public boolean hasRoot(double r) {
		double eva = this.evaluate(r);
		if (eva == 0){
			return true;
		}
		else{
			return false;
		}
	}
}