package com.git.must11.plugin.model;

import com.fr.base.Parameter;
import com.fr.base.ParameterHelper;
import com.fr.data.AbstractDataModel;
import com.fr.general.data.DataModel;
import com.fr.general.data.TableDataException;
import com.fr.json.JSONObject;
import com.fr.json.JSONParser;
import com.fr.log.FineLoggerFactory;
import com.fr.script.Calculator;
import com.fr.third.org.apache.http.HttpEntity;
import com.fr.third.org.apache.http.client.fluent.Request;
import com.fr.third.org.apache.http.entity.StringEntity;
import com.fr.workspace.WorkContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlJsonDataModel extends AbstractDataModel {

    private String method;
    private String url;
    private String requestHead;
    private String requestBody;
    private String successFlag;
    private String successObject;
    private String[] columnName;
    private String[] property;
    private List<List<String>> rowValueList;
    private Parameter[] params;

    public UrlJsonDataModel(Calculator calculator,Parameter[] params, String method,String url,String requestHead, String requestBody,String successObject,String successFlag,String[]columnName,String[]property) {
        this.method=method;
        this.url=url;
        this.requestHead=requestHead;
        this.requestBody = requestBody;
        this.successObject=successObject;
        this.successFlag=successFlag;
        this.columnName=columnName;
        this.property=property;
        this.params=params;
    }

    @Override
    public int getColumnCount() throws TableDataException {
        return this.columnName.length;
    }

    @Override
    public String getColumnName(int index) throws TableDataException {
        return index >= 0 && index < this.columnName.length ? this.columnName[index] : null;
    }

    @Override
    public int getRowCount() throws TableDataException {
        this.tryLoad();
        return this.rowValueList.size();
    }

    private void tryLoad() {
        if(null == this.rowValueList) {
            this.load();
        }
    }

    private void load() {

    }
    private InputStream getFileInputStream() {
        String url = ParameterHelper.analyze4Templatee(this.url, this.params);
        Request request;
        if (this.method.equals("GET")) {
            request=Request.Get(url);
        }else {
            request=Request.Post(url);
        }
        String head= ParameterHelper.analyze4Templatee(this.requestHead, this.params);

        setHead(request, head);
        String body= ParameterHelper.analyze4Templatee(this.requestBody, this.params);
        StringEntity stringEntity=new StringEntity(body,"UTF-8");
        request.body(stringEntity);
        try {
            return (InputStream) new ByteArrayInputStream(request.execute().returnContent().asBytes());
        } catch (Exception var3) {
            FineLoggerFactory.getLogger().debug("try to open " + request + "  failed", var3);
            return WorkContext.getWorkResource().openStream(url);
        }
    }
    @Override
    public Object getValueAt(int row, int col) throws TableDataException {

        if (row >= 0 && row < this.rowValueList.size()) {
            List<String> var3 = this.rowValueList.get(row);
            if (col >= 0 && col < this.columnName.length) {
                return var3.get(col);
            }
        }

        return null;
    }

    private void setHead(Request request,String json){
        JSONObject jo = new JSONObject(json);
        Map<String,Object> jsonMap = jo.getMap();
        if (jsonMap!=null &&jsonMap.size()>0){
            for (Map.Entry<String, Object> entry : jsonMap.entrySet()){
                request.addHeader(entry.getKey(),jo.getString(entry.getKey()));
            }
        }
    }
}
