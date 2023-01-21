package ru.otus.homework;

import java.util.Stack;

public class CustomerReverseOrder {

    private Stack stack = new Stack();

    public CustomerReverseOrder() {
    }
    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        stack.add(customer);
    }

    public Customer take() {
        return (Customer) stack.pop();
    }
}
