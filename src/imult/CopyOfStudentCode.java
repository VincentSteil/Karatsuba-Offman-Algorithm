package imult;
/*
 * Class StudentCode: class for students to implement 
 * the specific methods required by the assignment:
 * add()
 * sub()
 * koMul()
 * (See coursework handout for full method documentation)
 */

public class CopyOfStudentCode {
  public static BigInt add(BigInt A, BigInt B) {
	  	System.out.println("Adding ");
	  	A.print();
		B.print();
	
		int lengthA = A.length();								//Declare Length Variables for comparison below
		int lengthB = B.length();								//This is only, so the code is more easily readable. It can be compressed further if needed.
		int lengthResult = Math.max(lengthA, lengthB) +1;
		
		A.lshift(lengthResult - lengthA);						//Shift left to not have an out of bounds exception later. This is cheaper than checking, if the for loop is at the last digit yet later
		B.lshift(lengthResult - lengthB);
		
		BigInt result = new BigInt(lengthResult);				//Initialise the output BigInt
					
		DigitCarry dc = new DigitCarry();						//Initialise the carry as global variable for first use

		for (int i = lengthResult -1; i>=0; i--) {
			Unsigned digitA = A.getDigit(i);
			Unsigned digitB = B.getDigit(i);
			dc = Arithmetic.addDigits(digitA, digitB, dc.carry());
			result.setDigit(i, dc.digit()); 	
		}
		while (result.getDigit(0).intValue()==0){					//Check if the most significant digit is zero, if yes, remove it by splitting
			result = result.split(1, result.length());
		} 
		result.print();
		return result;
	  	  
  }
  public static BigInt sub(BigInt A, BigInt B) {
	  	System.out.println("Subtracting ");
	  	A.print();
	  	B.print();
	  	
	  	int lengthA = A.length();								//Declare Length Variables for comparison below
	    int lengthB = B.length();								//This is only, so the code is more easily readable. It can be compressed further if needed.
	    int lengthResult = Math.max(lengthA, lengthB);
	    
	    A.lshift(lengthResult - lengthA);						//Shift left to not have an out of bounds exception later. This is cheaper than checking, if the for loop is at the last digit yet later
		B.lshift(lengthResult - lengthB);
		
	    BigInt result = new BigInt(lengthResult);				//Initialise the output BigInt
	    				
	    Unsigned carry = new Unsigned(0);						//Initialise the carry for first use as global variable
	    
	    for (int i = (lengthResult - 1); i>=0; i--) {
	    	Unsigned digitA = A.getDigit(i);
	    	Unsigned digitB = B.getDigit(i);
	    	DigitCarry dc = Arithmetic.subDigits(digitA, digitB, carry);
	    	result.setDigit(i, dc.digit());
	    	carry = dc.carry();
	    }
	  
	    while (result.getDigit(0).intValue()==0){					//Check if the most significant digit is zero, if yes, remove it by splitting
	    	result = result.split(1, lengthResult);
	    }
	    result.print();
	    return result;
	
  }
  
  public static BigInt rshift(int offset, BigInt A) {
	  BigInt result = new BigInt(A.length() + offset);
	  Unsigned zero = new Unsigned(0);
	  
	  for (int i = 0; i < result.length(); i++) {
			if (i<A.length()) {
				result.setDigit(i, A.getDigit(i));
			}
			else {
				result.setDigit(i, zero);
			}
 	  }
	  return result;
  }
  
  public static BigInt koMul(BigInt A, BigInt B) {
	  	System.out.print("A: ");
	  	A.print();
	  	System.out.print("B: ");
	  	B.print();
	  	
	  	System.out.println("A.length: " + A.length());
	  	System.out.println("B.length: " + B.length());
	  	
	  	
	  	int n = Math.max(A.length(), B.length());
	  	System.out.println("n: " + n);		
	  	
		if (n == 1) {
			DigitCarry dc =  Arithmetic.mulDigits(A.getDigit(0), B.getDigit(0));
			BigInt result = new BigInt(1);
			result.setDigit(0, dc.digit());
			if(dc.carry().intValue() != 0) {
				result.lshift(1);
				result.setDigit(0, dc.carry());	
			}
			System.out.print("result: ");
			result.print();
			return result;
		}
	  
		//A.lshift(n-A.length());
		//B.lshift(n-B.length());
		
		
		int nfloor = n/2;
		if (n%2>0) {
			nfloor = n/2 +1;
		}
		int nceiling = nfloor -1;
		
		
		System.out.println("ceiling: " + nceiling);
		System.out.println("floor: " + nfloor);
		System.out.println("n-1: " + (n-1));
		
		BigInt a0 = A.split(nfloor, n-1);	
		BigInt a1 = A.split(0, nceiling);
		
		System.out.print("a0: ");
		a0.print();
		System.out.print("a1: ");
		a1.print();
		
		BigInt b0 = B.split(nfloor, n-1);
		BigInt b1 = B.split(0, nceiling);
		
		System.out.print("b0: ");
		b0.print();
		System.out.print("b1: ");
		b1.print();
	  
		BigInt l = koMul(a0, b0);
		System.out.print("l: ");
		l.print();
		BigInt h = koMul(a1, b1);
		System.out.print("h: ");
		h.print();
		BigInt atimesb = koMul(add(a0, a1), add(b0, b1));
		BigInt lplush = add(l, h);
		BigInt m = sub(atimesb, lplush);
		System.out.print("m: ");
		m.print();
		BigInt mB = new BigInt((m.length() + nfloor));
		BigInt hB = new BigInt(2*((h.length() + nfloor)));
		Unsigned zero = new Unsigned(0);
		for (int i = 0; i < mB.length(); i++) {
			if (i<m.length()) {
				mB.setDigit(i, m.getDigit(i));
			}
			else {
				mB.setDigit(i, zero);
			}
		}
		for (int i = 0; i < 2*hB.length(); i++){
			if (i<h.length()) {
				hB.setDigit(i, h.getDigit(i));
			}
			else {
				hB.setDigit(i, zero);
			}
		} 
		BigInt output = add(add(m, h), l);
		return output;  
	  
	  	  
  }
  /* public static BigInt koMulOpt(BigInt A, BigInt B) {
    // student needs to implement
	  //remember to reset BigInt.java to base 10k
  }
  */
  public static void main(String argv[]) {
	 
	  	
	  
	  	BigInt a = new BigInt("4 3 2 1");						//Test for BigInt.add
	  	BigInt b = new BigInt("6 5 4");
	  	BigInt e = rshift(3, a);
	  	System.out.println("The length of e is: " + e.length());
	  	BigInt result = add(a, b);
	  	result.print();
	  	System.out.println("The above should return  in base 10.");
	  	
	  	
	  	
	  	
	  	BigInt result2 = sub(a, b);
	  	result2.print();
	  	System.out.println("The above should return  in base 10.");
	  	
	  	BigInt c = new BigInt("1 2 4 1 3");						//Test for BigInt.koMul
	  	BigInt d = new BigInt("6 4 5 3 1");
	  	BigInt result3 = koMul(c, d);
	  	result3.print();
	  	System.out.println("The above should return  in base 10.");
	  	
	  	
  }
} //end StudentCode class


