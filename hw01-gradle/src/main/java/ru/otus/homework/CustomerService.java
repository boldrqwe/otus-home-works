package ru.otus.homework;

import java.util.Comparator;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private TreeMap<Customer, String> map = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = map.firstEntry();
        CustomerService customerService = new CustomerService();
        var customer = entry.getKey();
        var data = entry.getValue();
        customerService.add(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
        return customerService.getNext(new Customer(0L, "", 0L));
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return map.entrySet().stream()
                .filter(c -> c.getKey().getScores() > customer.getScores())
                .min(Comparator.comparing(customerStringEntry -> customerStringEntry.getKey().getScores()))
                .orElse(null);
    }

    public void add(Customer customer, String data) {
        map.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }

}
