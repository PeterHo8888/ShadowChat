import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HelpDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 708726427169339357L;
    JButton                   jbnOk;

    HelpDialog(JFrame parent, String title, boolean modal)
    {
        super(parent, title, modal);
        setBackground(Color.black);

        JPanel p1 = new JPanel(new FlowLayout(1));

        StringBuffer text = new StringBuffer();
        text.append("How to Use\n\n");
        text.append("Just use it...\n");

        JTextArea jtAreaAbout = new JTextArea(5, 21);
        jtAreaAbout.setText(text.toString());
        jtAreaAbout.setFont(new Font("Times New Roman", 1, 13));
        jtAreaAbout.setEditable(false);

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
