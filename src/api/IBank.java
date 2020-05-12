package api;

import java.io.IOException;
import java.util.List;

public interface IBank extends IBankRequestServer, IDepartmentClient {
    String getBankName();
    void addBranch(IBankBranch bankBranch);

    List<IBankBranch> getBranches();
}
