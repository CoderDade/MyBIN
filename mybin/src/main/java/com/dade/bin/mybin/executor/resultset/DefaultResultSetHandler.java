package com.dade.bin.mybin.executor.resultset;

import com.dade.bin.mybin.exceptions.ReflectionException;
import com.dade.bin.mybin.reflection.DefaultReflectorFactory;
import com.dade.bin.mybin.reflection.Reflector;
import com.dade.bin.mybin.reflection.factory.DefaultObjectFactory;
import com.dade.bin.mybin.reflection.invoker.Invoker;
import com.dade.bin.mybin.session.BINConfig;
import com.dade.bin.mybin.session.FieldEntity;
import com.dade.bin.mybin.session.ReturnEntity;
import com.dade.bin.mybin.util.ExceptionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class DefaultResultSetHandler {

    private final DefaultObjectFactory objectFactory = new DefaultObjectFactory();
    private final DefaultReflectorFactory reflectorFactory = new DefaultReflectorFactory();

    public Object handleResultSets(ResultSet rs, BINConfig config){
        ReturnEntity returnEntity = config.getReturnEntity();
        Object entity = objectFactory.create(returnEntity.getReturnType());
        Reflector reflector = reflectorFactory.findForClass(returnEntity.getReturnType());

        Map<String, Object> fieldMap = createFieldMap(rs, returnEntity);

        setBeanProperty("", entity, "", reflector);

        return null;
    }

    private Map<String, Object> createFieldMap(ResultSet rs, ReturnEntity returnEntity){

        Map<Integer, FieldEntity> fieldMap = returnEntity.getFieldMap();
        Map<String, Object> map = Maps.newHashMap();

        for (Map.Entry<Integer, FieldEntity> fieldEntry : fieldMap.entrySet()) {
            FieldEntity fieldEntity = fieldEntry.getValue();
            if (fieldEntity.getFieldTpye()==String.class){
                String value = rs.getStringValue(fieldEntry.getKey());
                map.put(fieldEntity.getFieldName(), value);
            }else if (fieldEntity.getFieldTpye() == Integer.class) {
                Integer value = rs.getIntegerValue(fieldEntry.getKey());
                map.put(fieldEntity.getFieldName(), value);
            }
        }
        return map;
    }

    private void setBeanProperty(String prop, Object object, Object value, Reflector reflector) {
        try {
            Invoker method = reflector.getSetInvoker(prop);
            Object[] params = {value};
            try {
                method.invoke(object, params);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        } catch (Throwable t) {
            throw new ReflectionException("Could not set property '" + prop + "' of '" + object.getClass() + "' with value '" + value + "' Cause: " + t.toString(), t);
        }
    }

}
