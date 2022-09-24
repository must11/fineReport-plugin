package com.git.must11.plugin.panel;

import com.fr.design.DesignerEnvManager;
import com.fr.design.border.UIRoundedBorder;
import com.fr.design.constants.UIConstants;
import com.fr.design.dialog.BasicDialog;
import com.fr.design.dialog.BasicPane;
import com.fr.design.gui.autocomplete.AutoCompletion;
import com.fr.design.gui.autocomplete.CompletionProvider;
import com.fr.design.gui.autocomplete.DefaultCompletionProvider;
import com.fr.design.gui.icontainer.UIScrollPane;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.syntax.ui.rsyntaxtextarea.RSyntaxTextArea;
import com.fr.design.gui.syntax.ui.rtextarea.RTextScrollPane;
import com.fr.design.i18n.Toolkit;
import com.fr.design.javascript.beautify.JavaScriptFormatHelper;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.general.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JsonContentPanel extends BasicPane {

    protected RSyntaxTextArea contentTextArea;
    private final UILabel titleLabel;
    private AutoCompletion ac;
    private static final Dimension FUNCTION_NAME_LABEL_SIZE = new Dimension(300, 40);
    private final JPanel labelPane;

    public JsonContentPanel() {
        this.titleLabel = new UILabel();
        this.labelPane = new JPanel(new BorderLayout(6, 4));
        this.setLayout(FRGUIPaneFactory.createBorderLayout());

        RTextScrollPane var3 = this.createContentTextAreaPanel();
        this.initContentTextAreaListener();
        this.add(this.createJsonParaPane(),"North");
        this.add(var3, "Center");
    }
    protected JPanel createJsonParaPane() {
        UILabel var1 = new UILabel(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Basic_Format_JavaScript"), IOUtils.readIcon("com/fr/design/images/edit/format.png"), 2);
        var1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        var1.setToolTipText(Toolkit.i18nText("Fine-Design_Basic_Format_JavaScript"));
        var1.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent var1) {
                (new SwingWorker<String, Void>() {
                    protected String doInBackground() {
                        return JavaScriptFormatHelper.beautify(JsonContentPanel.this.contentTextArea.getText());
                    }

                    protected void done() {
                        try {
                            String var1 = this.get();
                            JsonContentPanel.this.contentTextArea.setText(var1);
                        } catch (Exception ignored) {
                        }

                    }
                }).execute();
            }
        });
        this.labelPane.add(var1, "Center");
        JPanel var2 = new JPanel(new BorderLayout(4, 4));
        var2.setPreferredSize(new Dimension(200, 40));
        UIScrollPane var3 = new UIScrollPane(this.titleLabel);
        var3.setPreferredSize(FUNCTION_NAME_LABEL_SIZE);
        var3.setBorder(new UIRoundedBorder(UIConstants.TITLED_BORDER_COLOR, 1, 0));
        var2.add(var3, "West");
        var2.add(this.labelPane, "East");
        return var2;
    }
    public void populate(String var1) {
        this.contentTextArea.setText(var1);
    }
    public void setTitle(String var1) {
        this.titleLabel.setText(var1);
    }

    public String update() {
        return this.contentTextArea.getText();
    }

    private void initContentTextAreaListener() {
        this.contentTextArea.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent var1) {
                JsonContentPanel.this.installAutoCompletion();
            }

            public void focusLost(FocusEvent var1) {
                JsonContentPanel.this.uninstallAutoCompletion();
            }
        });
    }

    private void uninstallAutoCompletion() {
    }

    private void installAutoCompletion() {
        if (this.ac == null) {
            CompletionProvider var1 = this.createCompletionProvider();
            this.ac = new AutoCompletion(var1);
            String var2 = DesignerEnvManager.getEnvManager().getAutoCompleteShortcuts();
            this.ac.setTriggerKey(this.convert2KeyStroke(var2));
            this.ac.install(this.contentTextArea);
        }
    }

    private KeyStroke convert2KeyStroke(String var2) {
        return KeyStroke.getKeyStroke(var2.replace("+", "pressed"));
    }

    private CompletionProvider createCompletionProvider() {
       return  new DefaultCompletionProvider();
    }

    private RTextScrollPane createContentTextAreaPanel() {
        this.contentTextArea = new RSyntaxTextArea();
        this.contentTextArea.setCloseCurlyBraces(true);
        this.contentTextArea.setLineWrap(true);
        this.contentTextArea.setSyntaxEditingStyle("text/javascript");
        this.contentTextArea.setCodeFoldingEnabled(true);
        this.contentTextArea.setAntiAliasingEnabled(true);
        this.contentTextArea.setLineWrap(true);
        RTextScrollPane rTextScrollPane = new RTextScrollPane(this.contentTextArea);
        rTextScrollPane.setLineNumbersEnabled(true);
        rTextScrollPane.setBorder(new UIRoundedBorder(UIConstants.LINE_COLOR, 1, 0));
        rTextScrollPane.setPreferredSize(new Dimension(680, 600));
        return rTextScrollPane;
    }

    @Override
    protected String title4PopupWindow() {
        return "json";
    }
}
