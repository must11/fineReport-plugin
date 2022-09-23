package com.git.must11.plugin;

import com.fr.data.AbstractParameterTableData;
import com.fr.general.data.DataModel;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.script.Calculator;
@EnableMetrics
public class UrlJsonTableData extends AbstractParameterTableData {
    @Override
    public DataModel createDataModel(Calculator calculator) {
        return new UrlJsonDataModel(calculator);
    }

    @Override
    @Focus(id = "com.git.must11.plugin.url.json",text = "Plugin-UrlJson_TableSet",source = Original.PLUGIN)
    public DataModel createLimitDataModel(Calculator calculator, int i) {
        return super.createLimitDataModel(calculator, i);
    }
}
