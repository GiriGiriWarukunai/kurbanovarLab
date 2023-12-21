package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.Bank;
import tech.reliab.course.kurbanovar.bank.entity.BankATM;
import tech.reliab.course.kurbanovar.bank.entity.BankOffice;
import tech.reliab.course.kurbanovar.bank.entity.Employee;
import tech.reliab.course.kurbanovar.bank.service.AtmService;
import tech.reliab.course.kurbanovar.bank.utils.StatusATM;

import java.util.Objects;

public class AtmServiceImpl implements AtmService {

    @Override
    public BankATM create(Integer id, String name, StatusATM status, Double maintenanceCost) {
        BankATM bankATM = new BankATM(id, name, status, maintenanceCost);
        this.turnOnATM(bankATM);

        return bankATM;
    }

    @Override
    public void addMoney(BankATM bankATM, Double sumMoney) {
        if (Objects.equals(bankATM.getStatus(), StatusATM.NotWork)) {
            System.out.println("Банкомат не работает");
        }
        else {
            bankATM.setMoney(bankATM.getMoney() + sumMoney);
            bankATM.getBankOffice().setMoney(bankATM.getBankOffice().getMoney() + sumMoney);
            bankATM.getBank().setMoney(bankATM.getBank().getMoney() + sumMoney);
        }
    }


    @Override
    public void subtractMoney(BankATM bankATM, Double sumMoney) {

        if (Objects.equals(bankATM.getStatus(), StatusATM.NotWork) || (Objects.equals(bankATM.getStatus(),
                StatusATM.NoMoney)))
            System.out.println("Банкомат не выдаёт денег");
        else if (bankATM.getMoney() < sumMoney)
            System.out.printf("У банка не хватает %.2f денег%n",sumMoney-bankATM.getMoney());
        else if (Objects.equals(bankATM.getMoney(), sumMoney))
            bankATM.setStatus(StatusATM.NoMoney);
        else {
            //bankATM.setMoney(bankATM.getMoney() - sumMoney);
            //bankATM.getBankOffice().setMoney(bankATM.getBankOffice().getMoney() - sumMoney);
            bankATM.getBank().setMoney(bankATM.getBank().getMoney() - sumMoney);
        }
    }

    @Override
    public void turnOnATM(BankATM bankATM){
        bankATM.setCanDepositMoney(true);
        bankATM.setCanPayMoney(true);
    }

    @Override
    public void turnOffATM(BankATM bankATM){
        bankATM.setCanDepositMoney(false);
        bankATM.setCanPayMoney(false);
    }
}
