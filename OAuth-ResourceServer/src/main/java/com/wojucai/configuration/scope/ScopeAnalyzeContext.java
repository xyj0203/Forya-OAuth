package com.wojucai.configuration.scope;

import com.wojucai.dao.PropertyRepository;
import com.wojucai.dao.ScopeRepository;
import com.wojucai.entity.annotation.PropertyAnnotations;
import com.wojucai.entity.annotation.ScopeAnnotations;
import com.wojucai.entity.po.Property;
import com.wojucai.entity.po.Scope;
import com.wojucai.util.invoker.assign.ValueAssign;
import com.wojucai.util.invoker.factory.DefaultAssignFactory;
import com.wojucai.util.invoker.factory.ValueAssignFactory;
import com.wojucai.util.invoker.support.MethodHandleConfigSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static com.wojucai.entity.codeEnum.CacheConstant.SCOPE_CACHE;


/**
 * @description: 域转化
 * @author: xuyujie
 * @date: 2023/08/05
 **/
@Component
@Slf4j
public class ScopeAnalyzeContext implements SmartInitializingSingleton, ApplicationContextAware {

    private final String BASE_PACKAGE = "com.wojucai.entity.po";

    private final String RESOURCE_PATTERN = "/**/*.class";
    @javax.annotation.Resource
    private ScopeRepository scopeRepository;
    @javax.annotation.Resource
    private PropertyRepository propertyRepository;
    @Autowired
    private RedisTemplate<String,Integer> redisTemplate;
    private DefaultListableBeanFactory applicationContext;

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Set<String>> propertiesTable = new HashMap<>();
        // 持久化
        parseScopeClass(propertiesTable);
        ValueAssignFactory valueAssignFactory = new DefaultAssignFactory();
        ValueAssign valueAssign = valueAssignFactory.getValueAssign(new MethodHandleConfigSupport(propertiesTable));
    }

    /**
     * 查找待加载的类
     */
//    private List<Class> findWaitLoadClass() {
//
//    }

    /**
     * 从指定包下加载Scope类
     */
    private void parseScopeClass(Map<String, Set<String>> propertiesTable) {
        scopeRepository.deleteAll();
        propertyRepository.deleteAll();
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
                persistenceScope(classname, propertiesTable);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("There is a problem parsing the class");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void persistenceScope(String className,
                                 Map<String, Set<String>> propertiesTable) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        redisTemplate.delete(SCOPE_CACHE+className);
        //判断是否有指定主解
        ScopeAnnotations scopes = clazz.getAnnotation(ScopeAnnotations.class);
        if (scopes != null) {
            className = clazz.getName();
            Scope scope = new Scope(null, className, scopes.classDescription());
            scopeRepository.save(scope);
            Field[]fields = clazz.getDeclaredFields();
            Set<String> propertiesList = new HashSet<>();
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyAnnotations.class)) {
                    PropertyAnnotations annotations = field.getAnnotation(PropertyAnnotations.class);
                    if (annotations.behavior() == 1) {
                        Property Property = new Property(null,
                                field.getName(), scope.getId(), annotations.behavior() - 1, annotations.description());
                        Property save1 = propertyRepository.save(Property);
                        redisTemplate.opsForList().leftPush(SCOPE_CACHE+className,save1.getId());
                    }
                    Property Property = new Property(null,
                            field.getName(), scope.getId(), annotations.behavior(), annotations.description());
                    Property save1 = propertyRepository.save(Property);
                    redisTemplate.opsForList().leftPush(SCOPE_CACHE+className,save1.getId());
                    propertiesList.add(save1.getProperty());
                }
            }
            propertiesTable.put(className, propertiesList);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (DefaultListableBeanFactory) applicationContext;
    }
}
