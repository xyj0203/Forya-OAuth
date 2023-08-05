package com.wojucai.util.factory;

import com.wojucai.entity.annotation.ScopeAnnotations;
import com.wojucai.entity.annotation.PropertyAnnotations;
import com.wojucai.dao.ScopePropertyRepository;
import com.wojucai.dao.ScopeRepository;
import com.wojucai.entity.po.Scope;
import com.wojucai.entity.po.ScopeProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


/**
 * @description: 域转化
 * @author: xuyujie
 * @date: 2023/08/05
 **/
@Component
@Slf4j
public class ScopeAnalyzeFactory implements SmartInitializingSingleton {

    private final String BASE_PACKAGE = "com.wojucai.entity.po";
    private final String RESOURCE_PATTERN = "/**/*.class";
    @javax.annotation.Resource
    private ScopeRepository scopeRepository;
    @javax.annotation.Resource
    private ScopePropertyRepository scopePropertyRepository;

    @Override
    public void afterSingletonsInstantiated() {
        parseScopeClass();
    }

    /**
     * 从指定包下加载Scope类
     */
    private void parseScopeClass() {
        scopeRepository.deleteAll();
        scopePropertyRepository.deleteAll();
        int cnt = 0;
        // 获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            // MetadataReader 的工厂类
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                // 用于读取类信息
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                // 扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                persistenceScope(classname);
            }
            log.info("The number of loaded classes is {}", cnt);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("There is a problem parsing the class");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    void persistenceScope(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        //判断是否有指定主解
        ScopeAnnotations scopes = clazz.getAnnotation(ScopeAnnotations.class);
        if (scopes != null) {
            Scope scope = new Scope(null, className, scopes.classDescription());
            scopeRepository.save(scope);
            Field []fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyAnnotations.class)) {
                    PropertyAnnotations annotations = field.getAnnotation(PropertyAnnotations.class);
                    if (annotations.behavior() == 1) {
                        ScopeProperty scopeProperty = new ScopeProperty(null,
                                field.getName(), scope.getId(), annotations.behavior() - 1, annotations.description());
                        scopePropertyRepository.save(scopeProperty);
                    }
                    ScopeProperty scopeProperty = new ScopeProperty(null,
                            field.getName(), scope.getId(), annotations.behavior(), annotations.description());
                    scopePropertyRepository.save(scopeProperty);
                }
            }

        }
    }
}
