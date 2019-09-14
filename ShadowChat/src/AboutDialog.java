import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AboutDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = -5743904792494021945L;
    JButton                   jbnOk;

    AboutDialog(JFrame parent, String title, boolean modal)
    {
        super(parent, title, modal);
        setBackground(Color.black);

        JPanel p1 = new JPanel(new FlowLayout(1));

        getContentPane().add(new JScrollPane(p1), "Center");

        StringBuffer text = new StringBuffer();
        text.append("Information\n\n");
        text.append("Name:              ShadowChat\n");
        text.append("Developers: \t Alejandro Garcia and Peter Ho\n");
        text.append("Version:\t" + ShadowGui.version + "\n\n\n");

        text.append("Changelog\n\n");

        text.append("0.2.0 - 9/13/2019\n");
        text.append("- Cleanup and refactoring\n");
        text.append("- Replace bad connected list code\n");
        text.append("- Replace bad networking code\n");
        text.append("- Dynamic emoticons\n");
        text.append("- Fixed JTextPane scrolling and word wrap problems\n\n");
        
        text.append("v0.1c - 6/12/2016\n");
        text.append("- Fixed glitchy connected list\n\n");

        text.append("v0.1b - 4/2/2016\n");
        text.append("- Added a GUI\n");
        text.append("- Added offline support\n\n");

        text.append("v0.1a - Date Unknown\n");
        text.append("- Creation of the application.\n");

        TextArea jtAreaAbout = new TextArea("", 15, 45, 1);
        jtAreaAbout.setText(text.toString());
        jtAreaAbout.setFont(new Font("Courier New", 1, 13));
        jtAreaAbout.setEditable(false);
        jtAreaAbout.setBackground(Color.WHITE);

        p1.add(jtAreaAbout);
        getContentPane().add(p1, "Center");

        JPanel p2 = new JPanel(new FlowLayout(1));
        this.jbnOk = new JButton(" OK ");
        this.jbnOk.addActionListener(this);

        p2.add(this.jbnOk);
        getContentPane().add(p2, "South");

        setLocation(408, 270);

        setResizable(false);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                Window aboutDialog = e.getWindow();
                aboutDialog.dispose();
            }
        });

        pack();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.jbnOk)
            dispose();
    }
}
