import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ServerThread extends Thread {
    private BufferedReader br;
    private PrintWriter pw;
    private User user;

    public ServerThread(Socket socket, User user)
    {
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            this.br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            this.pw = new PrintWriter(os, true);
            echo("Hello");
            this.user = user;
        } catch (Exception e) {
            System.out.println("ERROR: could not create server thread.");
        }
    }


    public void run()
    {
        try {
            while (true) {
                String str = this.br.readLine();
                if (str.equals("!exit")) {
                    output("A client requests to disconnect.\n");
                    break;
                }
                if (str.startsWith("!")) {
                    doAction(str);
                    continue;
                }
                ShadowServer.echoAll(str);
            }
        } catch (Exception e) {
            // This is basically them leaving
            System.out.println("Client " + user.getID() + " disconnected unexpectedly");
        }

        output("A client has disconnected.\n");
        ShadowServer.map.remove(user);
        sendConnectedList();
    }

    public void doAction(String str)
    {
        if (str.startsWith("!name")) {
            String[] split = str.split("!name ");
            String name = split[1];
            output("id " + user.getID() + " now has name " + name + ".\n");
            user.setName(name);
            sendConnectedList();
        } else if (str.startsWith("!connected")) {
            sendConnectedList();  // !connected should not happen anymore
        }
    }
    
    public void sendConnectedList()
    {
        ShadowServer.echoAll("!name start");
        for (Map.Entry<User, ServerThread> entry : ShadowServer.map.entrySet()) {
            ShadowServer.echoAll("!name " + entry.getKey().getID() + " " + entry.getKey().getName());
        }
    }

    public void echo(String str)
    {
        try {
            this.pw.println(str);
        } catch (Exception e) {
            System.out.println("error 5, could not echo.");
        }
    }

    public void output(String str)
    {
        System.out.println(str);
    }
}
