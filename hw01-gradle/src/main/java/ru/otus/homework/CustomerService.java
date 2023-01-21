package ru.otus.homework;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import java.util.Map;

public class CustomerService {

    private HashMap<Customer, String> map = new HashMap();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry1 = map.entrySet().stream()
                .min(Comparator.comparing(entry -> entry.getKey().getScores()))
                .orElseThrow(() -> new RuntimeException("сервис пуст"));

        CustomerService customerService = new CustomerService();
        var customer = entry1.getKey();
        var data = entry1.getValue();
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
