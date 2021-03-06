package com.dade.bin.test;

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

import java.util.List;

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


    @Autowired
    BINTestInterface binTestInterface;

    @Test
    public void getSpringBean(){
        BINTestEntity binTestEntity = binTestInterface.testSingleEntity();
        System.out.println(binTestEntity.toString());
    }

    @Test
    public void getBean(){
        List<BINTestEntity> binTestEntitys = binTestInterface.testMulEntity();
        binTestEntitys.forEach(System.out::println);
    }



}
