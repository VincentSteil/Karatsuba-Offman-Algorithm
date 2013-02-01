package imult;
import java.io.File;
/*
 * Class StudentCode: class for students to implement 
 * the specific methods required by the assignment:
 * add()
 * sub()
 * koMul()
 * Vincent Steil
 * s1008380
 */

public class StudentCode {
  public static BigInt add(BigInt A, BigInt B) {
		
		int lengthResult = Math.max(A.length(), B.length())+1;
		
		
		BigInt result = new BigInt(lengthResult);				//Initialise the output BigInt
					
		DigitCarry dc = new DigitCarry();						//Initialise the carry as global variable for first use

		for (int i = 0; i<lengthResult; i++) {
			Unsigned digitA = A.getDigit(i);
			Unsigned digitB = B.getDigit(i);
			dc = Arithmetic.addDigits(digitA, digitB, dc.carry());
			result.setDigit(i, dc.digit()); 	
		}
		 
		return result;
	  	  
  }
  public static BigInt sub(BigInt A, BigInt B) {
		
		int lengthResult = Math.max(A.length(), B.length())+1;
		
		
		BigInt result = new BigInt(lengthResult);				//Initialise the output BigInt
					
		DigitCarry dc = new DigitCarry();						//Initialise the carry as global variable for first use

		for (int i = 0; i<lengthResult; i++) {
			Unsigned digitA = A.getDigit(i);
			Unsigned digitB = B.getDigit(i);
			dc = Arithmetic.subDigits(digitA, digitB, dc.carry());
			result.setDigit(i, dc.digit()); 	
		}
		 
		return result;
  }
  
 
  
  public static BigInt koMul(BigInt A, BigInt B) {
	  	
	  	int n = Math.max(A.length(), B.length());

	  	if (n < 2) {
	  		DigitCarry dc = Arithmetic.mulDigits(A.getDigit(0), B.getDigit(0));
	  		BigInt result = new BigInt(2);
	  		result.setDigit(0, dc.digit());
	  		result.setDigit(1, dc.carry());
	  		return result;
	  	}
	  	
	  	int floor = n/2 -1;
		int ceiling = floor +1;
		
		BigInt a0 = A.split(0, floor);
		BigInt a1 = A.split(ceiling, n-1);

		BigInt b0 = B.split(0, floor);
		BigInt b1 = B.split(ceiling, n-1);

		BigInt l = koMul(a0, b0);
		BigInt h = koMul(a1, b1);
		
		BigInt a = add(a0, a1);
		BigInt b = add(b0, b1);
		BigInt hplusl = add(h, l);
		BigInt m = sub(koMul(a, b), hplusl);
	
		m.lshift(floor + 1);
		h.lshift(2*(floor + 1));

		BigInt result = add(l, (add(m, h)));
		
		return result;
	  
	  	  
  }
   public static BigInt koMulOpt(BigInt A, BigInt B) {
	   
		int n = Math.max(A.length(), B.length());
	  	
		if (n <= 10) {
			BigInt result = Arithmetic.schoolMul(A, B);
			return result;
		}
		else {
		  	if (n < 2) {
		  		DigitCarry dc = Arithmetic.mulDigits(A.getDigit(0), B.getDigit(0));
		  		BigInt result = new BigInt(2);
		  		result.setDigit(0, dc.digit());
		  		result.setDigit(1, dc.carry());
		  	}
		  	
		  	int floor = n/2 -1;
			int ceiling = floor +1;
			
			BigInt a0 = A.split(0, floor);
			BigInt a1 = A.split(ceiling, n-1);

			BigInt b0 = B.split(0, floor);
			BigInt b1 = B.split(ceiling, n-1);
			
			BigInt l = koMulOpt(a0, b0);
			BigInt h = koMulOpt(a1, b1);
			
			BigInt a = add(a0, a1);
			BigInt b = add(b0, b1);
			BigInt hplusl = add(h, l);
			BigInt m = sub(koMulOpt(a, b), hplusl);
			
			m.lshift(floor+1);
			h.lshift(2*(floor+1));
			
			BigInt result = add(l, (add(m, h)));
			
			return result;
		  
		  	  
		}
	  	
	  
  }
  
  public static void main(String argv[]) {
	 
	//remember to reset BigInt.java to base 10k
	  
	  	BigInt a = new BigInt("1 0 0");//1 2 3 4");						//Test for BigInt.add
	  	BigInt b = new BigInt("1 0 0");// 6 7 8");
	  	//a.split(0, 0).print();//a.length()/2).print();
	  	
	  	//BigInt result = add(a, b);
	  	//result.print();
	  	//System.out.println("The above should return  in base 10.");
	  	
	  	
	  	
	  	//BigInt result2 = sub(b, a);
	  	//result2.print();
	  	//System.out.println("The above should return  in base 10.");
	  	BigInt c = new BigInt("1 2 3 4 5 6 7 8 9 1 2 3 4");
	  	BigInt d = new BigInt("9 8 7 6 5 4 3 2 1 9 8 7 6 5 4 3 2 1");

	  	BigInt result3 = koMul(a, b);
	  	System.out.println("The result of multiplying " + a.value() + " and " + b.value());
	  	result3.print();
	  	
	  	BigInt result2 = koMul(c, d);
	  	System.out.println("The result of multiplying " + c.value() + " and " + d.value());
	  	result2.print();
	  	
	 /* Unsigned m = new Unsigned(1);
	  Unsigned n = new Unsigned(10);
	  Unsigned t = new Unsigned(90);
	  File fout = new File("koMulOptTimes");
	  BigIntMul.getRunTimes(m, n, t, fout, true);
	  */	
	  	
  }
} //end StudentCode class


