package bgu.spl.net.Messages;

public class Login extends Message{
    private String username;
    private String password;
    private byte captcha;

    public Login(String username, String password, byte captcha){
        this.username = username;
        this.password = password;
        this.captcha = captcha;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public byte getCaptcha() {
        return captcha;
    }

}
