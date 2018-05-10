package com.dade.bin.mybin.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Arrays;
import java.util.Set;

/**
 * 配置入口
 */
public class ClassPathMapperScanner  extends ClassPathBeanDefinitionScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathMapperScanner.class);

    private MapperFactoryBean<?> mapperFactoryBean = new MapperFactoryBean<>();

    public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * MapperFactoryBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            LOGGER.warn("No MyBatis mapper was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions){
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            LOGGER.debug("Creating MapperFactoryBean with name '" + holder.getBeanName()
                    + "' and '" + beanClassName + "' mapperInterface");

            // the mapper interface is the original class of the bean
            // but, the actual class of the bean is MapperFactoryBean
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.setBeanClass(this.mapperFactoryBean.getClass());

//            definition.getPropertyValues().add("addToConfig", this.addToConfig);

            boolean explicitFactoryUsed = false;
//            if (StringUtils.hasText(this.sqlSessionFactoryBeanName)) {
//                definition.getPropertyValues().add("sqlSessionFactory", new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
//                explicitFactoryUsed = true;
//            } else if (this.sqlSessionFactory != null) {
//                definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
//                explicitFactoryUsed = true;
//            }

//            if (StringUtils.hasText(this.sqlSessionTemplateBeanName)) {
//                if (explicitFactoryUsed) {
//                    LOGGER.warn("Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
//                }
//                definition.getPropertyValues().add("sqlSessionTemplate", new RuntimeBeanReference(this.sqlSessionTemplateBeanName));
//                explicitFactoryUsed = true;
//            } else if (this.sqlSessionTemplate != null) {
//                if (explicitFactoryUsed) {
//                    LOGGER.warn("Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
//                }
//                definition.getPropertyValues().add("sqlSessionTemplate", this.sqlSessionTemplate);
//                explicitFactoryUsed = true;
//            }
//
            if (!explicitFactoryUsed) {
                LOGGER.debug("Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            }
        }
    }

}
