import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShadowServer {

    private final int PORT = 20000;
    
    protected static ArrayList<ServerThread>  serverThreads;
    
    protected static HashMap<User, ServerThread> map;

    ServerSocket ss;

    public ShadowServer()
    {
        
        serverThreads = new ArrayList<ServerThread>();
        map = new HashMap<User, ServerThread>();

        System.out.print("ShadowChat Server v.01b\n\n");
        System.out.print(
                "DISCLAIMER: This is free software and comes with ABSOLUTELY NO WARRANTY, "
                        + "to the extent permitted by applicable law.\n\n");
        System.out.print("\n--========================--\n\n");
        
        try {
            System.out.print("Initializing socket...\n");
            this.ss = new ServerSocket(20000);
            System.out.print("Socket opened on port 20000\n");

            while (true) {
                System.out.print("Waiting for connections...\n");
                Socket s = this.ss.accept();
                System.out.print("Connection made!\n");
                User user = new User();
                ServerThread st = new ServerThread(s, user);
                System.out.print("Sent hello message...Successful\n");
                st.start();
                map.put(user, st);
            }

        } catch (Exception e) {
            System.out.println("Error 325695207078194, not sure");

            try {
                this.ss.close();
            } catch (IOException e2) {
                System.out.println("error 4, could not close socket.");
            }
            return;
        }
    }

    public static void main(String[] args)
    {
        new ShadowServer();
    }

    public static void echoAll(String str)
    {
        for (Map.Entry<User, ServerThread> entry : map.entrySet()) {
            try {
                ServerThread st = entry.getValue();
                st.echo(str);
            } catch (Exception ex) {
                System.out.println("error 1, problem echoing.");
            }
        }
    }
}
