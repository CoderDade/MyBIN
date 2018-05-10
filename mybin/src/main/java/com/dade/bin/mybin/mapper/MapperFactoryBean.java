package com.dade.bin.mybin.mapper;

import com.dade.bin.mybin.executor.DefaultExcutor;
import com.dade.bin.mybin.session.BINConfig;
import com.dade.bin.mybin.session.BINSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import static org.springframework.util.Assert.notNull;

public class MapperFactoryBean<T>  implements InitializingBean, FactoryBean<T> {

    private static final Logger logger = LoggerFactory.getLogger(MapperFactoryBean.class);

    private Class<T> mapperInterface;

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
        return null;
    }

    public boolean isSingleton() {
        return false;
    }

    public void afterPropertiesSet() throws Exception {
        notNull(this.mapperInterface, "Property 'mapperInterface' is required");

        // 在这里解析注解
        BINConfig configuration = getSession().getConfiguration();
        if (!configuration.hasMapper(this.mapperInterface)) {
            try {
                configuration.addMapper(this.mapperInterface);
            } catch (Exception e) {
                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
                throw new IllegalArgumentException(e);
            } finally {
//                ErrorContext.instance().reset();
            }
        }

    }


    private BINSession getSession(){
        // 这里和mybatis不同，不需要去解析xml，所以直接用空的Config
        BINSession binSession =  new BINSession(new BINConfig(),new DefaultExcutor());
        return binSession;
    }

}
