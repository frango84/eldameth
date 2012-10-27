package original;

public class FibonacciAgent {

	public static int fibonacci(int n){

		//local vars declaration
	    int x;
	    int res1;
	    int y;
	    int res2;
	    int res;

	    if (n == 0 || n == 1)
	    	return n;
	    x = n - 1;

	    move();

	    res1 = fibonacci(x);
	    y = n - 2;
	    res2 = fibonacci(y);
	    res = res1 + res2;
	    return res;
	}

	public static void main(String [] args){

		//local vars declaration
		int result;

		result = fibonacci(10);
		System.out.println("RESULT = " + result);
	}

}
