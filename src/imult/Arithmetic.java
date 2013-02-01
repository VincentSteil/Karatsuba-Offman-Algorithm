package imult;

/*
 * Class Arithmetic: Handles addition, subtraction, and
 *   multiplication over digits in BigInt base. Includes methods:
 *   addDigits()
 *   subDigits()
 *   mulDigits()
 *   add()
 *   sub()
 */
class Arithmetic {
  public static DigitCarry addDigits(Unsigned a, Unsigned b,
      Unsigned c) {
    long sum = a.value() + b.value() + c.value(); 
    // force into base
    DigitCarry dc = new DigitCarry();
    dc.digit(sum % BigInt.base_);
    dc.carry(sum / BigInt.base_);
    return dc;
  }
  public static DigitCarry subDigits(Unsigned a, Unsigned b,
      Unsigned c) {
    DigitCarry dc = new DigitCarry();
    long sum;
    if(a.value() >= (b.value() + c.value())) {
      sum = a.value() - (b.value() + c.value()); 
      // force into base
      dc.digit(sum % BigInt.base_);
      dc.carry(sum / BigInt.base_);
    }
    else {
      sum = (BigInt.base_ + a.value()) - (b.value() + c.value());
      dc.digit(sum);
      dc.carry(1);
    }
    //System.out.println("* " + a.value() + " - " + b.value() + " = " + dc.digit().value());
    return dc;
  }
  public static DigitCarry mulDigits(Unsigned a, Unsigned b) {
    long prod = a.value() * b.value();
    DigitCarry dc = new DigitCarry();
    dc.digit(prod % BigInt.base_);
    dc.carry(prod / BigInt.base_);
    return dc;
  }
  public static BigInt schoolMul(BigInt A, BigInt B) {
    assert(A.length() == B.length());
    int alen = A.length(), blen = B.length();
    int column = 0;
    BigInt product = new BigInt(); // answer
    for(int b = 0; b < blen; b++) { // for each digit in B
      BigInt row = new BigInt();
      Unsigned carry = new Unsigned(0);
      // Explicitly set zeros in columns
      for(int i = 0; i < b; i++) row.setDigit(i, BigInt.Zero_); 
      for(int a = 0; a < alen; a++) { // for each digit in A
        column = a + b;
        // multiple 
        DigitCarry dc1 = Arithmetic.mulDigits(B.getDigit(b), A.getDigit(a));
        // add carry to current value
        DigitCarry dc2 = Arithmetic.addDigits(dc1.digit(), carry, BigInt.Zero_);
        // set digit in this column
        row.setDigit(column, dc2.digit());
        // save carry
        carry.setValue(dc1.carry().value() + dc2.carry().value());
      }
      // add final carry 
      row.setDigit(column + 1, carry);
      // add to final product
      //product = StudentCode.add(product, row);
      BigInt sum = new BigInt();
      DigitCarry dcAdd = new DigitCarry();
      int len = product.length() > row.length() ? product.length() : row.length();
      for(int i = 0; i < len; i++) {
        dcAdd = Arithmetic.addDigits(product.getDigit(i), row.getDigit(i), dcAdd.carry());
        sum.setDigit(i, dcAdd.digit());
      }
      sum.setDigit(len, dcAdd.carry());
      product = sum;
    }
    return product;
  }
  public static BigInt fastCheckMul(BigInt A, BigInt B) {
    int alen = A.length(), blen = B.length();
    BigInt product = new BigInt(); // answer
    int col = 0;
    for(int b = 0; b < blen; b++) { // for each digit in B
      Unsigned carry = new Unsigned(0);
      col = b; // current column
      for(int a = 0; a < alen; a++) { // for each digit in A
        // multiple 
        DigitCarry dc1 = Arithmetic.mulDigits(B.getDigit(b), A.getDigit(a));        
        // add to this column with carry 
        DigitCarry dc2 = Arithmetic.addDigits(product.getDigit(col), dc1.digit(), carry);
        // set new value in current column
        product.setDigit(col++, dc2.digit());
        // save carry for next iteration
        carry.setValue(dc1.carry().value() + dc2.carry().value());
      }
      //put carry into answer here
      product.setDigit(col, carry);
    }
    return product;
  }
  public static double average(double[] a,int StartIndex) { 
	double sum = 0.0; 
	for (int i = StartIndex; i < a.length; i++) 
			sum = sum + a[i]; 
	return sum / (a.length-StartIndex); 
  }	
  public static void main(String argv[]) {
  }
} // end class Arithmetic

