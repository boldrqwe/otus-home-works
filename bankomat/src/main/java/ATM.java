
public class ATM {
    private Cash rubs;

    public ATM(Cash rubs) {
        this.rubs = rubs;
    }

    public Cash getCashByAmountAndType(Long amount) {
        if (amount % 10 != 0) {
            System.out.println("сумма не кратна 10 не могу выдать такую сумму");
            return null;
        }
      return   rubs.minus(amount);
    }

    public void putCashByType(Cash cash) {
            rubs.put(cash);
    }

    public void printBalance() {
        rubs.printCash();
    }
}
