import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ShadowServer {
    protected static ShadowLogin  login;
    private static ShadowListener sl;
    private static Socket         s;

    public ShadowServer(ShadowGui gui)
    {
        login = new ShadowLogin();
        Scanner scanner = new Scanner(System.in);
        try {
            s = new Socket(login.server, login.port);
            send("!name " + login.name);
            sl = new ShadowListener(s, gui);
            sl.start();
            while (true) {
                send(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
            scanner.close();
            System.exit(0);
        }
    }

    public static void send(String input)
    {
        try {
            OutputStream os = s.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println(input);
            pw.flush();
        } catch (IOException e) {
            System.out.println("Error sending data");
        }
    }

}
