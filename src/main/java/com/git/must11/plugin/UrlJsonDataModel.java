package com.git.must11.plugin;

import com.fr.data.AbstractDataModel;
import com.fr.general.data.DataModel;
import com.fr.general.data.TableDataException;
import com.fr.script.Calculator;

public class UrlJsonDataModel extends AbstractDataModel {
    public UrlJsonDataModel(Calculator calculator) {
    }

    @Override
    public int getColumnCount() throws TableDataException {
        return 0;
    }

    @Override
    public String getColumnName(int i) throws TableDataException {
        return null;
    }

    @Override
    public int getRowCount() throws TableDataException {
        return 0;
    }

    @Override
    public Object getValueAt(int i, int i1) throws TableDataException {
        return null;
    }
}
