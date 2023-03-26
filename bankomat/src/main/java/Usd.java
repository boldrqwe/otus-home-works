import java.util.Arrays;
import java.util.List;

public class Usd implements Valuta {

    private final ValutaType valutaType = ValutaType.USD;

    @Override
    public List<String> getNominal() {
        return Arrays.stream(Usd.Nominal.values()).map(n -> n.name().replaceAll("N", "")).toList();
    }

    enum Nominal implements Nom {
        N100,
        N50,
        N20,
        N10,
        N5,
        N2,
        N1,
        ;

        @Override
        public Long getNominalValue() {
            return Long.parseLong(this.name().replaceAll("N", ""));
        }
    }
}
