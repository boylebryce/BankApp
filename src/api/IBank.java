package api;

public interface IBank extends IBankRequestServer, IDepartmentClient {
    String getBankName();
    void newBranch(IBankBranch bankBranch);
}
