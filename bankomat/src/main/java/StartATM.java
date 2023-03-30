import java.util.HashMap;
import java.util.Map;

public class StartATM {
    public static void main(String[] args) {

        HashMap<Nom, Long> rubs = new HashMap<>(Map.of(Rub.Nominal.N100, 10L, Rub.Nominal.N500, 10L,
                Rub.Nominal.N1000, 10L));

        Cash cashRubs = new Cash( rubs);


        ATM atm = new ATM(cashRubs);

        System.out.println("Банкомат создан");
        System.out.println("_______________");

        atm.printBalance();


        System.out.println("снимаем деньги");
        System.out.println("_______________");
        Cash cashByAmountAndType = atm.getCashByAmountAndType(1100L);

        System.out.println("снятые деньги");
        System.out.println("_______________");
        cashByAmountAndType.printCash();

        System.out.println("баланс");
        System.out.println("_______________");

        atm.printBalance();


        System.out.println("добавляем деньги");
        System.out.println("_______________");
        atm.putCashByType(new Cash( new HashMap<>(Map.of(Rub.Nominal.N100, 10L, Rub.Nominal.N500, 10L,
                Rub.Nominal.N1000, 10L))));


        System.out.println("баланс");
        System.out.println("_______________");
        atm.printBalance();
    }
}
