
public class Main {

    public static void main(String[] args) throws Exception {
        DataBase base = new DataBase();
        base.establishConnectionAndRead();
        base.printDataBase();
        base.createNewUserInDataBase("Bill", 25, 888);
        base.printDataBase();
        System.out.println(base.getUserSalary("Bill"));
        base.close();
    }

}
