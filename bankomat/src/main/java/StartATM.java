import java.util.HashMap;
import java.util.Map;

public class StartATM {
    public static void main(String[] args) {

        HashMap<Nom, Long> rubs = new HashMap<>(Map.of(Rub.Nominal.N100, 10L, Rub.Nominal.N500, 10L,
                Rub.Nominal.N1000, 10L));

        Cash cashRubs = new Cash(ValutaType.RUB, rubs);

        HashMap<Nom, Long> euro = new HashMap<>(Map.of(Euro.Nominal.N100, 10L, Euro.Nominal.N500, 10L,
                Euro.Nominal.N200, 10L));

        Cash cashEuro = new Cash(ValutaType.EURO, euro);

        HashMap<Nom, Long> usd = new HashMap<>(Map.of(Usd.Nominal.N100, 10L, Usd.Nominal.N50, 10L, Usd.Nominal.N20,
                10L));

        Cash cashUsd = new Cash(ValutaType.USD, usd);

        ATM atm = new ATM(cashRubs, cashEuro, cashUsd);

        System.out.println("Банкомат создан");
        System.out.println("_______________");

        atm.printBalance();


        System.out.println("снимаем деньги");
        System.out.println("_______________");
        Cash cashByAmountAndType = atm.getCashByAmountAndType(1100L, ValutaType.RUB);

        System.out.println("снятые деньги");
        System.out.println("_______________");
        cashByAmountAndType.printCash();

        System.out.println("баланс");
        System.out.println("_______________");

        atm.printBalance();


        System.out.println("добавляем деньги");
        System.out.println("_______________");
        atm.putCashByType(new Cash(ValutaType.RUB, new HashMap<>(Map.of(Rub.Nominal.N100, 10L, Rub.Nominal.N500, 10L,
                Rub.Nominal.N1000, 10L))));


        System.out.println("баланс");
        System.out.println("_______________");
        atm.printBalance();
    }
}
