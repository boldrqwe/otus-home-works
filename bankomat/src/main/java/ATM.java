
public class ATM {
    private Cash rubs;
    private Cash euro;
    private Cash usd;

    public ATM(Cash rubs, Cash euro, Cash usd) {
        this.rubs = rubs;
        this.euro = euro;
        this.usd = usd;
    }

    public Cash getCashByAmountAndType(Long amount, ValutaType type) {
        if (amount % 10 != 0) {
            System.out.println("сумма не кратна 10 не могу выдать такую сумму");
            return null;
        }

        return switch (type) {
            case RUB -> rubs.minus(amount, type);
            case EURO -> euro.minus(amount, type);
            case USD -> usd.minus(amount, type);
        };
    }

    public void putCashByType(Cash cash) {
        switch (cash.getType()) {
            case RUB -> rubs.put(cash);
            case EURO -> euro.put(cash);
            case USD -> usd.put(cash);
        }
    }

    public void printBalance() {
        rubs.printCash();
        usd.printCash();
        euro.printCash();
    }
}
