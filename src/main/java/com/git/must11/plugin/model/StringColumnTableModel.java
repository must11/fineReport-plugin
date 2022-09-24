package com.git.must11.plugin.model;

import com.fr.design.gui.itableeditorpane.UITableEditAction;
import com.fr.design.gui.itableeditorpane.UITableModelAdapter;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.i18n.Toolkit;
import com.fr.log.FineLoggerFactory;
import com.fr.stable.StringUtils;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StringColumnTableModel extends UITableModelAdapter<String[]> {
    private static final String[] DEFAULT_COLUMN_NAME = {Toolkit.i18nText("Plugin-Column_Name"), Toolkit.i18nText("Plugin-Object_Property")};
    public StringColumnTableModel(){
        this(DEFAULT_COLUMN_NAME);
    }

    public StringColumnTableModel(String[] strings) {

        super(strings);
        this.setColumnClass(new Class[]{String.class, String.class});
        this.setDefaultEditor(String.class, new StringColumnTableModel.StringEditor());
        this.setDefaultRenderer(String.class, new StringColumnTableModel.CellRenderer());

    }

    @Override
    public Object getValueAt(int i, int i1) {
        if (i1>1){
            return null;
        }
        String[] v  = this.getList().get(i);
        if (v.length!=2){
            return null;
        }
        return v[i1];
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        if (i1 != 1) {
            return true;
        } else {
            return this.getList().get(i) != null && StringUtils.isNotEmpty((this.getList().get(i))[0]);
        }
    }

    @Override
    public UITableEditAction[] createAction() {
        return new UITableEditAction[]{new StringColumnTableModel.AddAction(), new StringColumnTableModel.DeleteAction()};
    }

    private class StringEditor extends AbstractCellEditor implements TableCellEditor {
        private UITextField textField = new UITextField();

        public StringEditor() {
            this.addCellEditorListener(new CellEditorListener() {
                public void editingCanceled(ChangeEvent var1) {
                }

                public void editingStopped(ChangeEvent var1) {
                    if (StringColumnTableModel.this.table.getSelectedRow() != -1) {
                        String[] var2 = (String[]) StringColumnTableModel.this.getList().get(StringColumnTableModel.this.table.getSelectedRow());
                        String var3 = StringUtils.trimToNull(StringColumnTableModel.StringEditor.this.textField.getText());
                        FineLoggerFactory.getLogger().error("点击列:{}", StringColumnTableModel.this.table.getSelectedColumn());
                        var2[StringColumnTableModel.this.table.getSelectedColumn()] = var3;
                        StringColumnTableModel.this.fireTableDataChanged();
                    }
                }
            });
        }
        public Component getTableCellEditorComponent(JTable var1, Object var2, boolean var3, int var4, int var5) {
            this.textField.setText((String)var2);
            return this.textField;
        }

        public Object getCellEditorValue() {
            return this.textField.getText();
        }
    }

    class CellRenderer extends DefaultTableCellRenderer {
        CellRenderer() {
        }

        public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
            if (var6 == 0) {
                this.setBackground(new Color(229, 229, 229));
                this.setHorizontalAlignment(0);
            }

            return super.getTableCellRendererComponent(var1, var2, var3, var4, var5, var6);
        }
    }

    public class AddAction extends UITableModelAdapter<StringColumnTableModel>.AddTableRowAction {
        public void actionPerformed(ActionEvent var1) {
            super.actionPerformed(var1);
            StringColumnTableModel.this.addList();
        }
    }

    private void addList() {
        String[] var1 = new String[]{null,null};
        this.addRow(var1);
        this.fireTableDataChanged();
        this.table.getSelectionModel().setSelectionInterval(this.table.getRowCount() - 1, this.table.getRowCount() - 1);
    }
}
