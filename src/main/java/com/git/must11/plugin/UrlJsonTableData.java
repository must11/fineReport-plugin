package com.git.must11.plugin;

import com.fr.base.Parameter;
import com.fr.config.holder.Conf;
import com.fr.config.holder.factory.Holders;
import com.fr.config.holder.impl.ColConf;
import com.fr.data.AbstractParameterTableData;
import com.fr.general.data.DataModel;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.script.Calculator;
import com.fr.stable.StableUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import com.git.must11.plugin.model.UrlJsonDataModel;

import java.util.ArrayList;
import java.util.Collection;

@EnableMetrics
public class UrlJsonTableData extends AbstractParameterTableData {
    private Conf<String> url=Holders.simple("");
    private Conf<String> method=Holders.simple("");
    private Conf<String> requestHead=Holders.simple("");
    private Conf<String> requestBody=Holders.simple("");
    private Conf<String> reposeSuccessFlag=Holders.simple("");
    private Conf<String> responseSuccess=Holders.simple("");
    private ColConf<Collection<String>> columnNameList = Holders.collection(new ArrayList(), String.class);
    private ColConf<Collection<String>> propertyList = Holders.collection(new ArrayList(), String.class);
    @Override
    public DataModel createDataModel(Calculator calculator) {
        Parameter[] p = Parameter.providers2Parameter(Calculator.processParameters(calculator, this.getParams()));
        return new UrlJsonDataModel(calculator,p,method.get(),
                url.get(),requestHead.get(),requestBody.get(),
                reposeSuccessFlag.get(),responseSuccess.get(),
                (String[]) columnNameList.get().toArray(),(String[])propertyList.get().toArray());
    }

    @Override
    @Focus(id = "com.git.must11.plugin.url.json",text = "Plugin-UrlJson_TableSet",source = Original.PLUGIN)
    public DataModel createLimitDataModel(Calculator calculator, int i) {
        return super.createLimitDataModel(calculator, i);
    }
    public Parameter[] getParams() {
        Collection var1 = (Collection)this.parameters.get();
        return (Parameter[])var1.toArray(new Parameter[var1.size()]);
    }

    @Override
    public void readXML(XMLableReader var1) {
        super.readXML(var1);
        if (var1.isChildNode()) {
            String var3 = var1.getTagName();
            String value;
            if ("UrlJsonTableDataAttr".equals(var3)) {
                this.setUrl(var1.getAttrAsString("url", null));
                this.setMethod(var1.getAttrAsString("method", null));
                this.setRequestHead(var1.getAttrAsString("requestHead", null));
                this.setRequestBody(var1.getAttrAsString("requestBody", null));
                this.setReposeSuccessFlag(var1.getAttrAsString("reposeSuccessFlag", null));
                this.setResponseSuccess(var1.getAttrAsString("responseSuccess", null));

            } else if ("columnNameList".equals(var3)) {
                    if (( value = var1.getElementValue()) != null) {
                        this.columnNameList.set(new ArrayList());
                        String[] var4 = StableUtils.splitString(value, ",,.,,");
                        for (String s:var4) {
                            this.columnNameList.add(s);
                        }
                    }
            }else if ("propertyList".equals(var3)) {
                if (( value = var1.getElementValue()) != null) {
                    this.propertyList.set(new ArrayList());
                    String[] propertyList = StableUtils.splitString(value, ",,.,,");
                    for (String s:propertyList) {
                        this.propertyList.add(s);
                    }
                }
            }
        }

    }

    @Override
    public void writeXML(XMLPrintWriter var1) {
        var1.startTAG("UrlJsonTableDataAttr")
                .attr("url", this.url.get())
                .attr("method", this.method.get())
                .attr("requestHead", this.requestHead.get())
                .attr("requestBody", this.requestBody.get())
                .attr("reposeSuccessFlag", this.reposeSuccessFlag.get())
                .attr("responseSuccess", this.responseSuccess.get())
                .end();
        var1.startTAG("columnNameList").textNode(StableUtils.join((Collection)this.columnNameList.get(), ",,.,,")).end();
        var1.startTAG("propertyList").textNode(StableUtils.join((Collection)this.propertyList.get(), ",,.,,")).end();
        super.writeXML(var1);
    }

    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public String getMethod() {
        return method.get();
    }

    public void setMethod(String method) {
        this.method.set(method);
    }

    public String getRequestHead() {
        return requestHead.get();
    }

    public void setRequestHead(String requestHead) {
        this.requestHead.set(requestHead);
    }

    public String getRequestBody() {
        return requestBody.get();
    }

    public void setRequestBody(String requestBody) {
        this.requestBody.set(requestBody);
    }

    public String getReposeSuccessFlag() {
        return reposeSuccessFlag.get();
    }

    public void setReposeSuccessFlag(String reposeSuccessFlag) {
        this.reposeSuccessFlag.set(reposeSuccessFlag);
    }

    public String getResponseSuccess() {
        return responseSuccess.get();
    }

    public void setResponseSuccess(String responseSuccess) {
        this.responseSuccess.set(responseSuccess);;
    }

    public ColConf<Collection<String>> getColumnNameList() {
        return columnNameList;
    }

    public void setColumnNameList(ColConf<Collection<String>> columnNameList) {
        this.columnNameList = columnNameList;
    }

    public ColConf<Collection<String>> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(ColConf<Collection<String>> propertyList) {
        this.propertyList = propertyList;
    }
}
