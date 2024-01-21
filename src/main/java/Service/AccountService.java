package Service;
import Model.Account;
import DAO.AccountDAO;


public class AccountService {

    public AccountDAO accountDAO;

    /**
     * No-args constructor for accountService which creates an AccountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    /*
     * Registers new account if and only if the username is not blank, 
     * the password is at least 4 characters long, and an Account with that username does not already exist.
     * Persists account to databse
     * @param account to be registered
     * @return Account if registered
     */
    public Account registerAccount(Account account) {
        String username = account.getUsername();
        if ((account.getPassword().length()<4) || username == null || username.trim().length() == 0){ 
            return null; 
        } 
        return accountDAO.registerAccount(account);
    }
    /**
     * Method to login an account
     * Login will be successful only if username and password match the username and password of an account
     * on the server
     * @param account object without accountid
     * @return Account to be logged in
     */
    public Account login(Account account){
        return accountDAO.loginAccount(account);
    }
}
