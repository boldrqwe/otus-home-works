import java.util.Arrays;
import java.util.List;

public class Euro implements Valuta {

    private final ValutaType valutaType = ValutaType.EURO;

    @Override
    public List<String> getNominal() {
        return Arrays.stream(Usd.Nominal.values()).map(n -> n.name().replaceAll("N", "")).toList();
    }

    enum Nominal implements Nom {
        N500,
        N200,
        N100,
        N50,
        N20,
        N10,
        ;

        @Override
        public Long getNominalValue() {
            return Long.parseLong(this.name().replaceAll("N", ""));
        }
    }
}
