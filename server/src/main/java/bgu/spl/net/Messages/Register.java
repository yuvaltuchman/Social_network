package bgu.spl.net.Messages;

public class Register extends Message {
    private String userName;
    private String passWord;
    private String birthDay;
    public Register(String userName, String passWord, String birthDay){
        this.userName = userName;
        this.passWord = passWord;
        this.birthDay = birthDay;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}
