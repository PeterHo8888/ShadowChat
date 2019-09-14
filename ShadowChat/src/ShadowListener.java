import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ShadowListener extends Thread {
    private BufferedReader   br;
    private ShadowProcessing sp;

    public ShadowListener(Socket socket, ShadowGui gui)
    {
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            sp = new ShadowProcessing(gui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try {
            while (true) {
                String str = this.br.readLine();
                sp.process(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
