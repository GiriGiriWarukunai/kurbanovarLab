package tech.reliab.course.kurbanovar.bank.service;

import tech.reliab.course.kurbanovar.bank.entity.Bank;

import java.util.Comparator;

public interface BankComparator extends Comparator<Bank> {
    @Override
    int compare(Bank o1, Bank o2);

}