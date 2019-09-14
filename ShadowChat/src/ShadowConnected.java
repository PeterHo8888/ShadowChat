public class ShadowConnected extends Thread {
    public void run()
    {
        while (true) {
            ShadowServer.send("!connected");
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
