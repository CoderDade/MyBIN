package com.dade.bin.mybin.mapper;

import com.dade.bin.mybin.executor.DefaultExecutor;
import com.dade.bin.mybin.session.DefaultBinSession;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import static org.springframework.util.Assert.notNull;

/**
 * 通过xml配置制定Mapper接口
 * <bean class="com.dade.bin.mybin.mapper.MapperFactoryBean">
 *      <property name="mapperInterface" value="com.dade.bin.test.BINTestInterface"/>
 * </bean>
 */
public class MapperFactoryBean<T> implements InitializingBean, FactoryBean<T> {

    private static final Logger logger = LoggerFactory.getLogger(MapperFactoryBean.class);

    private Class<T> mapperInterface;
    private DefaultBinSession session;

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public MapperFactoryBean() {
        //intentionally empty
    }

    public T getObject() throws Exception {
        return getSession().getMapper(this.mapperInterface);
    }

    public Class<?> getObjectType() {
        return this.mapperInterface;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        notNull(this.mapperInterface, "Property 'mapperInterface' is required");

        // 在这里解析注解
        if (!getSession().hasMapper(this.mapperInterface)) {
            try {
                getSession().addMapper(this.mapperInterface);
            } catch (Exception e) {
                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
                throw new IllegalArgumentException(e);
            } finally {
//                ErrorContext.instance().reset();
            }
        }

    }


    private DefaultBinSession getSession() {
        // 这里和mybatis不同，不需要去解析xml，所以直接用空的Config
        // bug#1 getSession 不断产生新的config
        if (this.session == null) {
            DefaultBinSession defaultBinSession = new DefaultBinSession(Lists.newArrayList(), new DefaultExecutor());
            this.session = defaultBinSession;
        }
        return this.session;
    }


    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

}
