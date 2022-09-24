package com.git.must11.plugin;

import com.fr.base.TableData;
import com.fr.design.data.tabledata.tabledatapane.AbstractTableDataPane;
import com.fr.design.fun.impl.AbstractTableDataDefineProvider;
import com.fr.design.i18n.Toolkit;
import com.git.must11.plugin.panel.UrlJsonPanel;

public class UrlJson extends AbstractTableDataDefineProvider {
    @Override
    public Class<? extends TableData> classForTableData() {
        return UrlJsonTableData.class;
    }

    @Override
    public Class<? extends TableData> classForInitTableData() {
        return UrlJsonTableData.class;
    }

    @Override
    public Class<? extends AbstractTableDataPane> appearanceForTableData() {
        return UrlJsonPanel.class;
    }

    @Override
    public String nameForTableData() {
        return Toolkit.i18nText("Plugin-Dataset_Name");
    }

    @Override
    public String prefixForTableData() {
        return "url";
    }

    @Override
    public String iconPathForTableData() {
        return "com/git/must11/plugin/web/http.png";
    }
}
