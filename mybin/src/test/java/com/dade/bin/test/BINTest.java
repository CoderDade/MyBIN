package com.dade.bin.test;

import com.dade.bin.mybin.mapper.MapperFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class BINTest implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BINTest.class);

    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        context=arg0;
    }

    @Test
    public void testLog(){
        logger.info("Hello Log.");
    }


//    @Resource
//    MapperFactoryBean mapperFactoryBean;

    @Autowired
    BINTestInterface binTestInterface;

    @Test
    public void getSpringBean(){
//        Object mapper = context.getBean("mapperFactoryBean");
//        mapper.toString();
        BINTestEntity binTestEntity = binTestInterface.testSingleEntity();
//        binTestEntity.toString();
    }




}
