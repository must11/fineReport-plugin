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

public class ColumnSetModel extends UITableModelAdapter<ColumnSet> {
    private static final String[] DEFAULT_COLUMN_NAME = {Toolkit.i18nText("Plugin-Column_Name"), Toolkit.i18nText("Plugin-Object_Property")};
    public ColumnSetModel(){
        this(DEFAULT_COLUMN_NAME);
    }
    protected ColumnSetModel(String[] strings) {
        super(strings);
        this.setColumnClass(new Class[]{String.class, String.class});
        this.setDefaultEditor(String.class, new ColumnSetModel.StringEditor());
        this.setDefaultRenderer(String.class, new ColumnSetModel.CellRenderer());
    }

    @Override
    public Object getValueAt(int i, int i1) {
        if (i1>1){
            return null;
        }
        ColumnSet v  = this.getList().get(i);

        return i1 ==0? v.getColumnName():v.getProperty();
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        if (i1 != 1) {
            return true;
        } else {
            return this.getList().get(i) != null && StringUtils.isNotEmpty((this.getList().get(i)).getColumnName());
        }
    }



    @Override
    public UITableEditAction[] createAction() {
        return new UITableEditAction[]{new ColumnSetModel.AddAction(), new ColumnSetModel.DeleteAction()};
    }

    private class StringEditor extends AbstractCellEditor implements TableCellEditor {
        private final UITextField textField = new UITextField();

        public StringEditor() {
            this.addCellEditorListener(new CellEditorListener() {
                public void editingCanceled(ChangeEvent var1) {
                }

                public void editingStopped(ChangeEvent var1) {
                    if (ColumnSetModel.this.table.getSelectedRow() != -1) {
                        ColumnSet columnSet = ColumnSetModel.this.getList().get(ColumnSetModel.this.table.getSelectedRow());
                        String value = StringUtils.trimToNull(ColumnSetModel.StringEditor.this.textField.getText());
                        if(ColumnSetModel.this.table.getSelectedColumn() ==0) {
                            columnSet.setColumnName(value);
                        }else {
                            columnSet.setProperty(value);
                        }
                        FineLoggerFactory.getLogger().error("对象:{}",columnSet);
                        ColumnSetModel.this.fireTableDataChanged();
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
                this.setHorizontalAlignment(SwingConstants.CENTER);
            }

            return super.getTableCellRendererComponent(var1, var2, var3, var4, var5, var6);
        }
    }

    public class AddAction extends UITableModelAdapter<ColumnSet>.AddTableRowAction {
        public void actionPerformed(ActionEvent var1) {
            super.actionPerformed(var1);
            ColumnSetModel.this.addList();
        }
    }

    private void addList() {
        ColumnSet columnSet=new ColumnSet();
        this.addRow(columnSet);
        this.fireTableDataChanged();
        this.table.getSelectionModel().setSelectionInterval(this.table.getRowCount() - 1, this.table.getRowCount() - 1);
    }

}
