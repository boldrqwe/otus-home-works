import java.util.Arrays;
import java.util.List;

public class Rub {

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


    public List<String> getNominal() {
        return Arrays.stream(Nominal.values()).map(n -> n.name().replaceAll("N", "")).toList();
    }

}
