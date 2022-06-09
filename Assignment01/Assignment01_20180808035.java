import org.w3c.dom.ls.LSOutput;

/**
 * @author: Agah Berkin Güler
 * @since: 09/03/2022
 */

public class Assignment01_20180808035 {
    public static void main(String[] args) {

        Account a = new Account("1234",100);
        PersonalAccount pa = new PersonalAccount("9876","Joseph","Ledet");
        BusinessAccount ba = new BusinessAccount("5678", 1000, 0.09);
        Customer cu = new Customer("John","Smith");
        Company co = new Company("Akdeniz Universitesi");

        a.withdrawal(50);
        System.out.println(a);
        pa.deposit(150);
        System.out.println(pa);
        System.out.println("PIN is "+ pa.getPIN());
        ba.deposit(ba.calculateInterest());
        System.out.println(ba);
        ba.withdrawal(100);
        System.out.println(ba);

        System.out.println(cu);
        cu.openAccount("3456");
        cu.getAccount().deposit(123);
        System.out.println(cu.getAccount());

        System.out.println(co);
        co.openAccount("6543",0.05);
        co.getAccount().deposit(321);
        System.out.println(co.getAccount());

    }
}

class Account{
    private String accNum;
    private double balance;

    public Account(String accNum) {
        this.accNum = accNum;
        balance = 0;
    }

    public Account(String accNum, double balance) {
        this.accNum = accNum;
        if(balance <=0){
            balance = 0;
        }
        else{
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

    public void deposit(double amount){
        if(amount >= 0){
            setBalance(getBalance()+amount);
        }
    }
    public void withdrawal(double amount) {
        if(amount >= 0){
            setBalance(getBalance()-amount);
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

    public PersonalAccount(String accNum, double balance, String name, String surname) {
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
        this.rate = rate;
    }

    public BusinessAccount(String accNum, double balance, double rate) {
        super(accNum, balance);
        this.rate = rate;
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
    private String name;
    private String surname;
    PersonalAccount personalAccount;

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    public void openAccount(String accNum){
        personalAccount = new PersonalAccount(accNum,name,surname);
    }
    public PersonalAccount getAccount(){
        return personalAccount;
    }

    @Override
    public String toString() {
        return getName() +" "+getSurname().toUpperCase();
    }
}
class Company {
    private String name;

    BusinessAccount businessAccount;

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void openAccount(String accNum,double rate){
        businessAccount = new BusinessAccount(accNum,rate);
    }
    public BusinessAccount getAccount(){
        return businessAccount;
    }

    @Override
    public String toString() {
        return getName();
    }
}

