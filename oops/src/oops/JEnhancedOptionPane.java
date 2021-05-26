package oops;

import javax.swing.*;
import java.awt.*;

public class JEnhancedOptionPane extends JOptionPane {
    public static Object showInputDialog(final Object message, final Object[] options,String title)
            throws HeadlessException {
        final JOptionPane pane = new JOptionPane(message, QUESTION_MESSAGE,
                OK_CANCEL_OPTION, null,
                options, null);
        //pane.setWantsInput(true);
        pane.setComponentOrientation((getRootFrame()).getComponentOrientation());
        pane.setMessageType(INFORMATION_MESSAGE);
        pane.selectInitialValue();
        final JDialog dialog = pane.createDialog(null, title);
        dialog.setSize(400, 200);
        dialog.setVisible(true);
        dialog.dispose();
        final Object value = pane.getValue();
        return value;
    }
}
