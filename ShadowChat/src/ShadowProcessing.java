import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

public class ShadowProcessing {
    private ShadowGui gui;

    public ShadowProcessing(ShadowGui gui)
    {
        this.gui = gui;
    }

    public void process(String str)
    {
        try {
            if (str.startsWith("!"))
                doAction(str);
            else if (str.startsWith("*"))
                System.out.print(str);
            else
                sendToScreen(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doAction(String str)
    {
        // TODO: fix this up badly
        if (str.startsWith("!name") && !str.startsWith("!name start")
                && !str.contains("!disconnected")) {

            String[] split = str.split("\\s+");
            int id = Integer.parseInt(split[1]);
            String[] temp = str.split("!name " + id + " ");
            String name = temp[1];
            String connectedText = gui.getConnectedText();
            if (!connectedText
                    .contains(String.valueOf(Integer.toString(id)) + ". ")
                    && !name.contains("!removed"))
                gui.appendConnectedText(String.valueOf(id) + ". " + name + "\n");
            if (name.contains("!removed")) {
                String[] lines = connectedText.split("\\n");
                for (int x = 0; x < lines.length; ++x) {
                    if (lines[x].contains(
                            String.valueOf(Integer.toString(id)) + ". ")) {
                        gui.setConnectedText("");
                    }
                }
            }
        }
    }

    public void sendToScreen(String message)
    {
        String[] list = message.split(" ");
        boolean isIcon = false;

        if (message.length() == 0 || message.charAt(0) == '\n')
            return;

        for (int j = 0; j < list.length; j++) {
            for (int i = 0; i < ShadowGui.EMOTE_LENGTH; i++) {
                isIcon = false;
                if (list[j].equals(gui.emotes[i])) {
                    isIcon = true;
                    ImageIcon icon = (ImageIcon) gui.emoticons
                            .get(gui.emotes[i]);
                    if (icon != null) {
                        StyleConstants.setIcon(gui.getStyle(), icon);
                    }
                    gui.insert(gui.emotes[i], "icon");
                    break;
                }
            }

            if (!isIcon) {
                gui.insert(list[j], "reg");
            }
        }
        gui.insert("\n", "reg");
    }
}
