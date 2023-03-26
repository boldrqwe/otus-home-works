import java.util.Arrays;
import java.util.List;

public class Rub implements Valuta {
    private final ValutaType valutaType = ValutaType.RUB;

    enum Nominal implements Nom {
        N5000,
        N2000,
        N1000,
        N500,
        N100,
        N50,
        N10
        ;
        @Override
        public Long getNominalValue() {
            return Long.parseLong(this.name().replaceAll("N", ""));
        }
    }



    @Override
    public List<String> getNominal() {
        return Arrays.stream(Usd.Nominal.values()).map(n -> n.name().replaceAll("N", "")).toList();
    }

}
