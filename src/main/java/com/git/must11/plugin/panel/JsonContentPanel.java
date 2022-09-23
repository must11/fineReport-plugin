package com.git.must11.plugin.panel;

import com.fr.design.dialog.BasicDialog;
import com.fr.design.dialog.BasicPane;
import com.fr.design.gui.autocomplete.AutoCompletion;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.syntax.ui.rsyntaxtextarea.RSyntaxTextArea;
import com.fr.design.javascript.JavaScriptActionPane;
import com.fr.design.javascript.NewJavaScriptImplPane;
import com.fr.design.javascript.jsapi.JSImplPopulateAction;
import com.fr.design.javascript.jsapi.JSImplUpdateAction;
import com.fr.js.JavaScriptImpl;

import javax.swing.*;
import java.awt.*;

public class JsonContentPanel extends BasicPane {

    protected RSyntaxTextArea contentTextArea;
    private UILabel funNameLabel;
    private AutoCompletion ac;
    private static final Dimension FUNCTION_NAME_LABEL_SIZE = new Dimension(300, 80);
    private String[] defaultArgs;
    private int titleWidth;
    private JPanel labelPane;
    private NewJavaScriptImplPane newJavaScriptImplPane;
    private JavaScriptImpl javaScript;
    private JSImplUpdateAction jsImplUpdateAction;
    private JSImplPopulateAction jsImplPopulateAction;
    private boolean modal;
    BasicDialog advancedEditorDialog;
    @Override
    protected String title4PopupWindow() {
        return "json";
    }
}
