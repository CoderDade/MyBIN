package com.dade.bin.mybin.executor.resultset;

import com.dade.bin.mybin.session.BinConfig;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResultSetWrapper {

    private final ResultSet resultSet;
    private final List<String> columnNames = new ArrayList();
    private final List<String> classNames = new ArrayList();

    public ResultSetWrapper(ResultSet rs, BinConfig configuration){
        this.resultSet = rs;
    }

}
