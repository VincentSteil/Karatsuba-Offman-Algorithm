package imult;
/*
 * Class DigitCarry: Represents a digit and carry pair 
 * (See coursework handout for full method documentation)
 */

class DigitCarry {
  private Unsigned digit_ = new Unsigned(0);
  private Unsigned carry_ = new Unsigned(0);
  public Unsigned digit() {
    return digit_;
  }
  public Unsigned carry() {
    return carry_;
  }
  public void digit(long d) {
    digit_.setValue(d);
  }
  public void carry(long c) {
    carry_.setValue(c);
  }
  public void digit(int d) {
    digit_.setValue(d);
  }
  public void carry(int c) {
    carry_.setValue(c);
  }
} // end DigitCarry

