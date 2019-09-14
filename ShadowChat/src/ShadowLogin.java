import javax.swing.JOptionPane;

public class ShadowLogin {
    String server;
    int    port;
    String temp_key;
    String key;
    String name;

    public ShadowLogin()
    {
        try {
            this.server = JOptionPane.showInputDialog(null, "Server IP",
                    "Server IP", 3);
            //      this.port = Integer.parseInt(JOptionPane.showInputDialog(null, "Server Port", "Server Port", 3));
            this.name = JOptionPane.showInputDialog(null, "Name", "Name", 3);
            //      this.temp_key = JOptionPane.showInputDialog(null, "Encryption Key", "Encryption Key", 3);
            //      this.key = pad(this.temp_key);
            //this.server = "ssh.buhao.cc";
            this.port = 20000;
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}
