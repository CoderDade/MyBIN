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
import com.dade.bin.mybin.util.HexUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultResultSetHandler {

    private final DefaultObjectFactory objectFactory = new DefaultObjectFactory();
    private final DefaultReflectorFactory reflectorFactory = new DefaultReflectorFactory();

    public Object handleResultSets(ResultSet rs, BINConfig config) {
        ReturnEntity returnEntity = config.getReturnEntity();
        Object entity = objectFactory.create(returnEntity.getReturnType());

        if (returnEntity.isCollection()) {
            setBeanPropertyForCollection(rs, returnEntity, entity);
        } else {
            Reflector reflector = reflectorFactory.findForClass(returnEntity.getReturnType());
            setBeanProperty(rs, returnEntity, entity, reflector);
        }


        return entity;
    }

    private void setBeanPropertyForCollection(ResultSet rs,
                                              ReturnEntity returnEntity,
                                              Object entity) {
        Reflector reflector = reflectorFactory.findForClass(returnEntity.getRealType());
        Map<Integer, FieldEntity> fieldMap = returnEntity.getFieldMap();

        List<Map<Integer, byte[]>> cleanResults = rs.getResultMaps();
        for (Map<Integer, byte[]> cleanResultMap : cleanResults) {
            Object subEntity = objectFactory.create(returnEntity.getRealType());
            for (Map.Entry<Integer, byte[]> entry : cleanResultMap.entrySet()) {
                Integer order = entry.getKey();
                byte[] result = entry.getValue();

                FieldEntity fieldEntity = fieldMap.get(order);

                Object value = null;

                if (fieldEntity.getFieldTpye() == String.class) {
                    value = getStringValue(result);
                } else if (fieldEntity.getFieldTpye() == Integer.class) {
                    value = getIntegerValue(result);
                }
                setBeanProperty(fieldEntity.getFieldName(), subEntity, value, reflector);
            }
            ((Collection) entity).add(subEntity);
        }
    }


    private void setBeanProperty(ResultSet rs,
                                 ReturnEntity returnEntity,
                                 Object entity,
                                 Reflector reflector) {

        Map<Integer, FieldEntity> fieldMap = returnEntity.getFieldMap();

        List<Map<Integer, byte[]>> cleanResults = rs.getResultMaps();
        for (Map<Integer, byte[]> cleanResultMap : cleanResults) {
            for (Map.Entry<Integer, byte[]> entry : cleanResultMap.entrySet()) {
                Integer order = entry.getKey();
                byte[] result = entry.getValue();

                FieldEntity fieldEntity = fieldMap.get(order);

                Object value = null;

                if (fieldEntity.getFieldTpye() == String.class) {
                    value = getStringValue(result);
                } else if (fieldEntity.getFieldTpye() == Integer.class) {
                    value = getIntegerValue(result);
                }

                setBeanProperty(fieldEntity.getFieldName(), entity, value, reflector);

            }
        }
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

    public String getStringValue(byte[] bytes) {
        return HexUtil.getStringFromBytes(bytes);
    }

    public Integer getIntegerValue(byte[] bytes) {
        return HexUtil.bytesToInteger(bytes, 10);
    }

}
