import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Cash {
    private ValutaType type;
    private HashMap<Nom, Long> nominalToCount;

    public ValutaType getType() {
        return type;
    }

    public void setType(ValutaType type) {
        this.type = type;
    }

    public Map<Nom, Long> getNominalToCount() {
        return nominalToCount;
    }

    public void setNominalToCount(HashMap<Nom, Long> nominalToCount) {
        this.nominalToCount = nominalToCount;
    }

    public Cash(ValutaType type, HashMap<Nom, Long> nominalToCount) {
        this.type = type;
        this.nominalToCount = nominalToCount;
    }

    public void printCash() {
        System.out.println(type.name());
        AtomicLong total = new AtomicLong(0L);
        nominalToCount.forEach((n, a) -> {
            System.out.println("номинал: " + n + " количество: " + a);
            total.addAndGet(n.getNominalValue() * a);
        });

        System.out.println("всего: " + total.get());
    }

    public Long getTotal() {
        AtomicLong total = new AtomicLong(0L);
        nominalToCount.forEach((n, a) -> total.addAndGet(n.getNominalValue() * a));
        return total.get();
    }

    public void put(Cash cash) {
        cash.nominalToCount.forEach((nom, count) -> {
            this.nominalToCount.merge(nom, count, Long::sum);
        });
    }

    Nom getMinNom() {
        return this.nominalToCount.keySet().stream().min(Comparator.comparing(Nom::getNominalValue)).orElse(null);
    }

    Nom getNextMinNom(Nom nominal) {
        return this.nominalToCount.keySet().stream().filter(n -> n.getNominalValue() > nominal.getNominalValue()).min(Comparator.comparing(Nom::getNominalValue)).orElse(null);
    }

    public Cash minus(Long amount, ValutaType type) {
        if (amount > getTotal()) {
            throw new RuntimeException("не достаточно денег");
        }
        Cash cash = new Cash(type, new HashMap<>());

        long sufficientAmount = 0L;

        List<Nom> nominals = this.nominalToCount.keySet().stream()
                .sorted(Comparator.comparing(Nom::getNominalValue, Long::compareTo))
                .toList();

        ArrayList<Nom> usedNoms = new ArrayList<>();

        for (var nom : nominals) {
            Long nominalTotalAmount = getNominalTotalAmount(nom);

            if (sufficientAmount < amount) {
                sufficientAmount += nominalTotalAmount;
                usedNoms.add(nom);
                continue;
            }
            break;
        }

        findMinNominals(amount, cash, usedNoms);

        cash.getNominalToCount().forEach((nom, count) -> this.nominalToCount.merge(nom, count,
                (oldCount, newCount) -> oldCount - newCount));

        return cash;

    }

    private void findMinNominals(Long amount, Cash cash, ArrayList<Nom> usedNoms) {
        if (usedNoms.isEmpty()) {
            return;
        }

        long sufficientAmount2 = 0L;

        int size = usedNoms.size();

        for (int i = 0; i < size - 1; i++) {
            sufficientAmount2 += getNominalTotalAmount(usedNoms.get(0));
        }

        long remain = amount - sufficientAmount2;

        Nom nom = usedNoms.get(size - 1);

        Long needAmount = getNeedAmount(remain, nom);

        Long nominalValue = nom.getNominalValue();

        long remain2 = amount - needAmount * nominalValue;

        cash.put(nom, needAmount);

        if (remain2 <= 0) {
            return;
        }
        usedNoms.remove(nom);
        findMinNominals(remain2, cash, usedNoms);
    }

    private void put(Nom nom, Long needAmount) {
        this.nominalToCount.put(nom, needAmount);
    }

    private Long getNeedAmount(long remain, Nom nom) {
        Long nominalTotalAmount = getNominalTotalAmount(nom);
        Long amountOfLastUsedNom = this.nominalToCount.get(nom);

        Long nominalValue = nom.getNominalValue();
        return amountOfLastUsedNom - (nominalTotalAmount - remain) / nominalValue;
    }

    private Long getNominalTotalAmount(Nom nom) {
        return this.nominalToCount.get(nom) * nom.getNominalValue();
    }

    private Long getResidualOfAmount(Long amount, Nom minNom) {
        Long count = this.nominalToCount.get(minNom);
        Long nominalValue = minNom.getNominalValue();

        long amountCount = amount / nominalValue;
        return count - amountCount;
    }
}
