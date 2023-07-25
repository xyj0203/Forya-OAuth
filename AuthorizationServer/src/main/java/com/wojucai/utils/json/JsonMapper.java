package com.wojucai.utils.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Json 与 Bean 相互映射
 * 实现必须保证线程安全
 */
public interface JsonMapper {

    /**
     * 反序列化为简单类型 （无法处理泛型）
     *
     * @param jsonString Json字符串
     * @param type 对象类型
     * @param <T> 对象类型
     * @return  返回反序列化的对象
     * @throws JsonException 解析失败的异常
     */
    <T> T parseObject(String jsonString, Class<T> type) throws JsonException;

    /**
     * 反序列化为对象
     *
     * @param jsonString Json字符串
     * @param typeDefinition 类型定义
     * @param <T>   对象类型
     * @return  反序列化后的对象
     * @throws JsonException 解析失败的异常
     */
    <T> T parseObject(String jsonString, Type typeDefinition) throws JsonException;

    /**
     * 反序列化为数组，此处限定为集合List
     *
     * @param jsonString JSON字符串
     * @param collectionType 集合类型
     * @param <T> 泛型类型
     * @return  反序列化后的列表
     * @throws JsonException    解析失败的异常
     */
    <T> List<T> parseArray(String jsonString, Type collectionType) throws JsonException;

    /**
     * 序列化到字符串
     *
     * @param obj 要序列化的对象
     * @param <T> 对象类型
     * @return  返回的字符串
     * @throws JsonException 序列化失败的异常
     */
    <T> String serializeToString(T obj) throws JsonException;

    /**
     * 序列化到字符串
     *
     * @param obj 要序列化的对象
     * @param typeDefinition 类型的定义
     * @param <T> 对象类型
     * @return JSON字符串
     * @throws JsonException 序列化失败的异常
     */
    <T> String serializeToString(T obj, Type typeDefinition) throws JsonException;

    /**
     * 命名风格
     */
    void preferNamingStrategy(NamingStrategy namingStrategy);

    /**
     * 属性名命名风格
     */
    enum NamingStrategy {
        /**
         * 不改变名字 （JAVA默认命名小驼峰）
         */
        IDENTITY,
        /**
         * 小写，连字符(-)分割
         */
        LOWER_CASE_WITH_DASHES,
        /**
         * 小写，连字符(.)分割
         */
        LOWER_CASE_WITH_DOTS,
        /**
         * 下划线式
         */
        LOWER_CASE_WITH_UNDERSCORES,
        /**
         * 大驼峰式
         */
        UPPER_CAMEL_CASE,
        /**
         * 大驼峰式，用空格分隔
         */
        UPPER_CAMEL_CASE_WITH_SPACES
    }
}
