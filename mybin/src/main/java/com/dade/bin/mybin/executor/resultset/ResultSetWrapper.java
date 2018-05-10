package com.dade.bin.mybin.executor.resultset;

import com.dade.bin.mybin.session.BINConfig;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResultSetWrapper {

    private final ResultSet resultSet;
    private final List<String> columnNames = new ArrayList();
    private final List<String> classNames = new ArrayList();

    public ResultSetWrapper(ResultSet rs, BINConfig configuration){
        this.resultSet = rs;
    }

}
