package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
/**
 * DAO class for Account which manages Account services
 */
public class AccountDAO {
    /**
     * Method which registers a new account 
     * @param account
     * @return Account registered
     */
    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int gen_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(gen_account_id, account.getUsername(), account.getPassword());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method to process logins
     * @param account to be logged in
     * @return Account which is logged in
     */
     public Account loginAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account newAccount = new Account(rs.getInt("account_id"), 
                        rs.getString("username"),
                        rs.getString("password"));
                return newAccount;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
}
