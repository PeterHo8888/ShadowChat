import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ShadowGui extends JFrame implements ActionListener {
    private static final long serialVersionUID = -3555101439158612228L;
    public static String      version          = "0.2.0";

    // GUI Components
    private JTextField     tf; // typing
    private JTextArea      ta; // connected
    private JTextPane      tp; // messages
    private StyledDocument doc;

    // Menus
    private JMenuBar  menuBar;
    private JMenu     mnFile;
    private JMenu     mnHelp;
    private JMenuItem mntmExit;
    private JMenuItem mntmAbout;
    private JMenuItem mntmHelp;

    // Control
    private static Style       s;
    public static ShadowServer ss;

    // Emoticons
    public Hashtable<String, ImageIcon> emoticons;

    public static void main(String[] args)
    {
        ShadowGui g = new ShadowGui();
        ss = new ShadowServer(g);
    }

    private void createEmoticons()
    {
        if (emoticons != null) {
            System.out.println("emoticons hashtable already created.");
            return;
        }

        emoticons = new Hashtable<String, ImageIcon>();

        File folder = new File("images"); // normal
        if (!folder.exists())
            folder = new File("src/images"); // eclipse
        if (!folder.exists()) {
            System.out.println("Could not find images/");
            return;
        }

        File[] listOfFiles = folder.listFiles();

        for (File f : listOfFiles) {
            if (f.isFile()) {
                emoticons.put(f.getName().split(".png")[0],
                        createImageIcon("images/" + f.getName(), ""));
            }
        }
    }

    public ShadowGui()
    {
        setGuiComponents();
        createEmoticons();
    }

    private void setGuiComponents()
    {
        setTitle("ShadowChat " + version);
        setSize(573, 302);
        setDefaultCloseOperation(3);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 163, 319 };
        gridBagLayout.rowHeights = new int[] { 0, 253, 20 };
        gridBagLayout.columnWeights = new double[] { 0.0D, 1.0D };
        gridBagLayout.rowWeights = new double[] { 0.0D, 1.0D, 0.0D,
                Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        JLabel lblConnectedClients = new JLabel("Connected Clients:");
        GridBagConstraints gbc_lblConnectedClients = new GridBagConstraints();
        gbc_lblConnectedClients.insets = new Insets(0, 0, 5, 5);
        gbc_lblConnectedClients.gridx = 0;
        gbc_lblConnectedClients.gridy = 0;
        getContentPane().add(lblConnectedClients, gbc_lblConnectedClients);

        ta = new JTextArea();
        ta.setEditable(false);
        GridBagConstraints gbc_ta = new GridBagConstraints();
        gbc_ta.insets = new Insets(0, 0, 5, 5);
        gbc_ta.fill = 1;
        gbc_ta.gridx = 0;
        gbc_ta.gridy = 1;
        getContentPane().add(ta, gbc_ta);

        tp = new JTextPane();
        tp.setEditable(false);
        JScrollPane psp = new JScrollPane(tp);
        psp.setVerticalScrollBarPolicy(22);
        psp.setAutoscrolls(true);
        DefaultCaret caret = (DefaultCaret) tp.getCaret();
        caret.setUpdatePolicy(2);
        doc = tp.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, Color.RED);
        StyleConstants.setBackground(keyWord, Color.YELLOW);
        StyleConstants.setBold(keyWord, true);

        Style def = StyleContext.getDefaultStyleContext().getStyle("default");

        Style regular = doc.addStyle("reg", def);
        StyleConstants.setFontFamily(def, "Courier New");

        s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);

        s = doc.addStyle("icon", regular);
        StyleConstants.setAlignment(s, 1);

        GridBagConstraints gbc_tp = new GridBagConstraints();
        gbc_tp.insets = new Insets(0, 0, 5, 0);
        gbc_tp.fill = 1;
        gbc_tp.gridx = 1;
        gbc_tp.gridy = 1;

        getContentPane().add(psp, gbc_tp);

        JLabel lblCopyright = new JLabel("ShadowChat " + version);
        GridBagConstraints gbc_lblCopyright = new GridBagConstraints();
        gbc_lblCopyright.insets = new Insets(0, 0, 0, 5);
        gbc_lblCopyright.gridx = 0;
        gbc_lblCopyright.gridy = 2;
        getContentPane().add(lblCopyright, gbc_lblCopyright);

        this.tf = new JTextField();
        this.tf.addActionListener(this);
        GridBagConstraints gbc_tf = new GridBagConstraints();
        gbc_tf.fill = 2;
        gbc_tf.gridx = 1;
        gbc_tf.gridy = 2;
        getContentPane().add(this.tf, gbc_tf);
        this.tf.setColumns(10);

        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);

        this.mnFile = new JMenu("File");
        this.menuBar.add(this.mnFile);

        this.mntmExit = new JMenuItem("Exit");
        this.mntmExit.addActionListener(this);
        this.mnFile.add(this.mntmExit);

        this.mnHelp = new JMenu("Help");
        this.menuBar.add(this.mnHelp);

        this.mntmAbout = new JMenuItem("About");
        this.mntmAbout.addActionListener(this);
        this.mnHelp.add(this.mntmAbout);

        this.mntmHelp = new JMenuItem("Help");
        this.mntmHelp.addActionListener(this);
        this.mnHelp.add(this.mntmHelp);

        setVisible(true);
        this.tf.requestFocus();
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                showExitMessage();
            }
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.tf) {
            String text = this.tf.getText();
            if (this.tf.getText().replaceAll("\\s+", "").equals(""))
                return;
            this.tf.setText("");
            if (!text.startsWith("!exit") && !text.startsWith("!name")
                    && !text.startsWith("!connected")) {
                ShadowServer.send(
                        String.valueOf(ShadowServer.login.name) + ": " + text);
            } else {
                ShadowServer.send(text);
            }
        }
        if (e.getSource() == this.mntmExit) {
            showExitMessage();
        }
        if (e.getSource() == this.mntmAbout) {
            (new AboutDialog(this, "About", true)).setVisible(true);
        }
        if (e.getSource() == this.mntmHelp) {
            (new HelpDialog(this, "Help", true)).setVisible(true);
        }
    }
    
    private void showExitMessage()
    {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Are you sure you want to quit?");
        String[] options = { "OK", "CANCEL" };
        panel.add(label);
        int result = JOptionPane.showOptionDialog(null, panel, "Goodbye...",
                -1, 3, null, options, options[0]);
        if (result == 0) {
            ShadowServer.send("!exit");
            System.exit(0);
        }
    }

    protected static ImageIcon createImageIcon(String path, String description)
    {
        URL imgURL = ShadowGui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        }
        System.err.println("Couldn't find file: " + path);
        return null;
    }

    protected void insert(String text, String type)
    {
        try {
            if (type.equals("reg")) {
                // Java 7 broke this bad
                // https://stackoverflow.com/questions/8666727/
                for (int i = 0; i < text.length(); i++) {
                    doc.insertString(doc.getLength(),
                            text.substring(i, i + 1),
                            // Change attribute every other char
                            i % 2 == 0 ? null : doc.getStyle(type));
                }
            } else {
                doc.insertString(doc.getLength(), text, doc.getStyle(type));    
            }
            // Sometimes doc.insertString doesn't scroll to bottom by itself...
            ta.select(doc.getLength(), doc.getLength()); // scroll to bottom
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    protected void setConnectedText(String str)
    {
        ta.setText(str);
    }

    protected void appendConnectedText(String str)
    {
        ta.append(str);
    }

    protected String getConnectedText()
    {
        return ta.getText();
    }

    protected Style getStyle()
    {
        return s;
    }
}
