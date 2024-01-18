package Service;
import Model.Account;
import DAO.AccountDAO;


public class AccountService {

    public AccountDAO accountDAO;

    /**
     * No-args constructor for bookService which creates a BookDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    public Account registerAccount(Account account) {
        String username = account.getUsername();
        if ((account.getPassword().length()<4) || username == null || username.trim().length() == 0){ 
            return null; 
        } 
        return accountDAO.registerAccount(account);
    }

    public Account login(Account account){
        return accountDAO.loginAccount(account);
    }
}
