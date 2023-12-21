package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.Bank;
import tech.reliab.course.kurbanovar.bank.service.BankComparator;

public class BankComparatorImpl implements BankComparator {

    @Override
    public int compare(Bank a, Bank b) {
        double aCount =  a.getBankOffices().size() + a.getBankATMS().size() +
                a.getEmployees().size() + (20 - a.getInterestRate());
        double bCount = b.getBankOffices().size() + b.getBankATMS().size() +
                b.getEmployees().size() + (20 - b.getInterestRate());
        return Integer.compare((int)aCount, (int)bCount);
    }
}
