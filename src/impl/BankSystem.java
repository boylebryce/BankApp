package impl;

import api.IATM;
import api.IBankBranch;

import java.util.List;

public class BankSystem {
    private List<IATM> atms;
    private List<IBankBranch> bankBranches;

    public BankSystem(List<IATM> atms, List<IBankBranch> bankBranches) {
        this.atms = atms;
        this.bankBranches = bankBranches;
    }

    public List<IATM> getAtms() {
        return atms;
    }

    public List<IBankBranch> getBankBranches() {
        return bankBranches;
    }
}
