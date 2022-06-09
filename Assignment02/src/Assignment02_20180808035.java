import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Agah Berkin Güler
 * @since 22/03/2022
 */

public class Assignment02_20180808035 {
    //main
    public static void main(String[] args) {
        Bank b = new Bank("My Bank", "My Bank's Address");
        b.addCompany(1,"Company 1");
        b.getCompany(1).openAccount("1234",0.05);
        b.addAccount(b.getCompany(1).getAccount("1234"));
        b.getAccount("1234").deposit(500000);
        b.getCompany(1).getAccount("1234").deposit(500000);
        b.getCompany(1).openAccount("1235",0.03);
        b.addAccount(b.getCompany(1).getAccount("1235"));
        b.getCompany(1).getAccount("1235").deposit(25000);
        b.addCompany(2, "Company 2");
        b.getCompany(2).openAccount("2345", 0.03);
        b.addAccount(b.getCompany(2).getAccount("2345"));
        b.getCompany(2).getAccount("2345").deposit(350);
        b.addCustomer(1, "Customer", "1");
        b.addCustomer(2, "Customer", "2");
        Customer c = b.getCustomer(1);
        c.openAccount("3456");
        c.openAccount("3457");
        c.getAccount("3456").deposit(150);
        c.getAccount("3457").deposit(250);
        c = b.getCustomer(2);
        c.openAccount("4567");
        c.getAccount("4567").deposit(1000);
        b.addAccount(c.getAccount("4567"));
        c = b.getCustomer(1);
        b.addAccount(c.getAccount("3456"));
        b.addAccount(c.getAccount("3457"));
        System.out.println(b.toString());

    }
}

//Classes
class Bank{
    private String name;
    private String address;

    ArrayList<Customer> customerArrayList = new ArrayList<>();
    ArrayList<Company> companyArrayList = new ArrayList<>();
    ArrayList<Account> accountArrayList = new ArrayList<>();

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addCustomer(int id,String name,String surname){//Creates a Customer using name passed and adds to the set
        Customer customer = new Customer(name,surname);
        customer.setId(id);
        customerArrayList.add(customer);
    }
    public void addCompany(int id, String name){//Creates a Company using name passed and adds to the set
        Company company = new Company(name);
        company.setId(id);
        companyArrayList.add(company);
    }
    public void addAccount(Account account){
        accountArrayList.add(account);
    }
    public Customer getCustomer(int id)throws CustomerNotFoundException{
        for (int i = 0; i < customerArrayList.size(); i++) {
            if (Objects.equals(customerArrayList.get(i).getId(),id)){
                return customerArrayList.get(i);
            }
        }
        throw new CustomerNotFoundException(id);
    }
    public Customer getCustomer(String name, String surname)
            throws CustomerNotFoundException{
        for (int i =0;i<customerArrayList.size();i++){
            if (Objects.equals(customerArrayList.get(i).getName(), name)
                    && Objects.equals(customerArrayList.get(i).getSurname(),
                    surname)){
                return customerArrayList.get(i);
            }
        }
        throw new CustomerNotFoundException(name,surname);
    }
    public Company getCompany(int id)throws CompanyNotFoundException{
         for (int i = 0; i < companyArrayList.size(); i++) {
            if (Objects.equals(companyArrayList.get(i).getId(),id)){
                return companyArrayList.get(i);
            }
        }
        throw new CompanyNotFoundException(id);
    }
    public Company getCompany(String name)throws CompanyNotFoundException{
        for (int i = 0; i < companyArrayList.size(); i++) {
            if (Objects.equals(companyArrayList.get(i).getName(),name)){
                return companyArrayList.get(i);
            }
        }
        throw new CompanyNotFoundException(name);
    }
    public Account getAccount(String accountNum)throws AccountNotFoundException{
        for (int i = 0; i < accountArrayList.size(); i++) {
            if (Objects.equals(accountArrayList.get(i).getAccNum(),accountNum)){
                return accountArrayList.get(i);
            }
        }
        throw new AccountNotFoundException(accountNum);
    }
    public void transferFunds(String accountFrom,String accountTo,double amount)
            throws AccountNotFoundException,InvalidAmountException{

        Account accountSender = getAccount(accountFrom);
        Account accountReceiever = getAccount(accountTo);

        if (amount > accountSender.getBalance()) {
            throw new InvalidAmountException();
        }else{
            accountSender.withdrawal(amount);
            accountReceiever.deposit(amount);
        }
    }

    public void closeAccount(String accountNum)
            throws BalanceRemainingException,AccountNotFoundException{
        for (int i = 0; i < accountArrayList.size(); i++) {
            if (Objects.equals(accountArrayList.get(i).getAccNum(),accountNum)){
                if (accountArrayList.get(i).getBalance()<0){
                    accountArrayList.remove(i);
                }else{
                    throw new BalanceRemainingException();
                }
            }else{
                throw new AccountNotFoundException(accountNum);
            }
        }
    }

    @Override
    public String toString() {
        String s = getName()+"\t"+getAddress();
        for (Company company:companyArrayList) {
            s+="\n\t"+company.getName();
            for (BusinessAccount account: company.businessAccountArrayList) {
                s+="\n\t\t"+account.getAccNum()+"\t"+account.getRate()+"\t"+account.getBalance();
            }
        }
        for (Customer customer:customerArrayList) {
            s+="\n\t"+customer.getName()+" "+customer.getSurname();
            for (PersonalAccount account: customer.personalAccountArrayList) {
                s+="\n\t\t"+account.getAccNum()+"\t"+account.getBalance();
            }
        }
        return s;

    }
}
class Account{
    private String accNum;
    private double balance;// balance must be non negative keep or math.abs()

    public Account(String accNum) {
        this.accNum = accNum;
        balance = 0;
    }

    public Account(String accNum, double balance) {
        this.accNum = accNum;
        if(balance < 0){
            balance = 0;
        }else{
            this.balance = balance;
        }
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount)throws InvalidAmountException{
        if(amount >= 0){
            setBalance(getBalance()+amount);
        }else{
            throw new InvalidAmountException();
        }
    }
    public void withdrawal(double amount)throws InvalidAmountException {
        if(amount >= 0  ){
            if (getBalance()-amount<0){
                throw new InvalidAmountException();
            }else{
                setBalance(getBalance()-amount);
            }
        }else{
            throw new InvalidAmountException();
        }
    }

    @Override
    public String toString() {
        return "Account "+getAccNum()+" has "+ getBalance();
    }
}
class PersonalAccount extends Account{
    private String name;
    private String surname;
    private String PIN;

    public PersonalAccount(String accNum, String name, String surname) {
        super(accNum);
        this.name = name;
        this.surname = surname;
        PIN = randomFourDigitPin();
    }

    public PersonalAccount(String accNum, double balance, String name,
                           String surname) {
        super(accNum, balance);
        this.name = name;
        this.surname = surname;
        PIN = randomFourDigitPin();
    }

    public String randomFourDigitPin(){//to set the PIN to four random digit
        int a = 0;
        String pin ="";
        while(a==0){
            int random = (int)(Math.random()*10000)-1;
            if (random>999 && random <10000){
                pin = String.valueOf(random);
                a=1;
            }else{
                a=0;
            }
        }
        return pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    @Override
    public String toString() {
        return "Account "+super.getAccNum()+" belonging to "+getName()
                +" "+getSurname().toUpperCase()+" has "+ super.getBalance();
    }
}
class BusinessAccount extends Account{
    private double rate;

    public BusinessAccount(String accNum, double rate) {
        super(accNum);
        this.rate = Math.abs(rate);
    }

    public BusinessAccount(String accNum, double balance, double rate) {
        super(accNum, balance);
        this.rate = Math.abs(rate);
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    public double calculateInterest(){
        return getRate()*super.getBalance();
    }
}
class Customer {
    private int id;
    private String name;
    private String surname;
    ArrayList<PersonalAccount> personalAccountArrayList = new ArrayList<>();

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return Math.abs(id);
    }

    public void setId(int id) {
        this.id = Math.abs(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void openAccount(String acctNum){
        PersonalAccount personalAccount =
                new PersonalAccount(acctNum,name,surname);
        personalAccountArrayList.add(personalAccount);
    }
    public PersonalAccount getAccount(String accountNum)
            throws AccountNotFoundException{
        for (int i = 0; i < personalAccountArrayList.size(); i++) {
            if (Objects.equals(personalAccountArrayList.get(i).getAccNum(),
                    accountNum)){
                return personalAccountArrayList.get(i);
            }

        }
        throw new AccountNotFoundException(accountNum);
    }
    public void closeAccount(String accountNum)
            throws BalanceRemainingException,AccountNotFoundException{
        for (int i = 0; i < personalAccountArrayList.size(); i++) {
            if (Objects.equals(personalAccountArrayList.get(i).getAccNum(),
                    accountNum)){
                if (personalAccountArrayList.get(i).getBalance()<0){
                    personalAccountArrayList.remove(i);
                }else{
                    throw new BalanceRemainingException();
                }
            }else{
                throw new AccountNotFoundException(accountNum);
            }

        }
    }

    @Override
    public String toString() {
        return getName() +" "+getSurname().toUpperCase();
    }
}
class Company {
    private int id;
    private String name;

    ArrayList<BusinessAccount> businessAccountArrayList = new ArrayList<>();

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void openAccount(String acctNum,double rate){
        BusinessAccount businessAccount = new BusinessAccount(acctNum,rate);
        businessAccountArrayList.add(businessAccount);
    }
    public BusinessAccount getAccount(String acctNum)
            throws AccountNotFoundException{
        for (int i = 0; i < businessAccountArrayList.size(); i++) {
            if (Objects.equals(businessAccountArrayList.get(i).getAccNum(),
                    acctNum)){
                return businessAccountArrayList.get(i);
            }
        }
        throw new AccountNotFoundException(acctNum);
    }
    public void closeAccount(String accountNum)
            throws AccountNotFoundException,BalanceRemainingException{
        for (int i = 0; i < businessAccountArrayList.size(); i++) {
            if (Objects.equals(businessAccountArrayList.get(i).getAccNum(),
                    accountNum)){
                if (businessAccountArrayList.get(i).getBalance()<0){
                    businessAccountArrayList.remove(i);
                }else{
                    throw new BalanceRemainingException();
                }
            }else{
                throw new AccountNotFoundException(accountNum);
            }

        }
    }

    @Override
    public String toString() {
        return getName();
    }
}

//Exceptions
class AccountNotFoundException extends RuntimeException{
    private String acctNum;

    public AccountNotFoundException(String acctNum) {
        this.acctNum = acctNum;
    }

    @Override
    public String toString() {
        return "AccountNotFoundException: "+ acctNum;
    }
}
class BalanceRemainingException extends RuntimeException{
    private double balance;

    @Override
    public String toString() {
        return "BalanceRemainingException: "+ balance;
    }

    public double getBalance() {
        return balance;
    }
}
class CompanyNotFoundException extends RuntimeException{
    private int id;
    private String name;

    public CompanyNotFoundException(int id) {
        this.id = id;
        name = null;
    }

    public CompanyNotFoundException(String name) {
        this.name = name;
        id = 101;
    }

    @Override
    public String toString() {
        if (name != null){
            return "CompanyNotFoundException: name - "+ name;
        }else{
            return "CompanyNotFoundException: id - "+ id;
        }
    }
}
class CustomerNotFoundException extends RuntimeException{
    private int id;
    private String name,surname;

    public CustomerNotFoundException(int id) {
        this.id = id;
        name = null;
        surname = null;
    }

    public CustomerNotFoundException(String name, String surname) {
        this.name = name;
        this.surname = surname;
        id = 100;
    }

    @Override
    public String toString() {
        if (name != null && surname != null){
            return "CustomerNotFoundException: name - "+ name +" "+surname;
        }else
            return "CustomerNotFoundException: id - "+id;
    }
}
class InvalidAmountException extends RuntimeException{
    private double amount;

    @Override
    public String toString() {
        return "InvalidAmountException: "+amount;
    }
}