package com.liangquan.handw_spring.spring;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Scope;

import java.beans.Introspector;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName：JcenApplicationContext
 * @Author: liangquan
 * @Date: 2024/12/6 14:29
 * @Description: 自定义的 Spring 容器
 */
public class JcenApplicationContext {

    // 容器里面有哪些属性和方法？

    // 配置文件类
    private Class configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    // 单例池
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    //
    private ArrayList<BeanPostProcessor> beanPostProcessorsList = new ArrayList<>();

    public Class getConfigClass() {
        return configClass;
    }

    public JcenApplicationContext(Class configClass) {
        this.configClass = configClass;

        // Spring 容器启动，第一件事情是什么？扫描
        // 1: 扫描  --> BeanDefinition  --> BeanDefinitionMap
        // 1.1： 传入的 configClass 是否有 ComponentScan 这个注解
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotation.value(); // 扫描路径：com.liangquan.handw_spring

            // 1.2： 处理 path 为路径
            path = path.replace(".", "/"); // com/liangquan/handw_spring/service

            ClassLoader classLoader = JcenApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path); // 在-classPath里面获取path相对路径的资源
//            File file = new File(resource.getFile());
            String decodedPath = null;
            try {
                // URL.getFile() 返回的是编码过的文件路径，需要进行解码才能被正确识别。
                decodedPath = URLDecoder.decode(resource.getFile(), "UTF-8");
                File file = new File(decodedPath);

                System.out.println("file = " + file);
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        String fileName = f.getAbsolutePath();
                        if (fileName.endsWith(".class")) {
                            // 将绝对路径转换为全限定类名
                            String className = fileName.substring(fileName.indexOf("com/"), fileName.lastIndexOf(".class"));
                            // 替换路径中的斜杠为点
                            className = className.replace("/", ".");

                            // 加载类并检查是否有注解
                            Class<?> clazz = classLoader.loadClass(className);
                            if (clazz.isAnnotationPresent(Component.class)) {
                                // System.out.println(className + " 存在 @Component 注解");
                                // 将该类注册为 Bean (先不做)


                                // 类是否实现了 BeanPostProcessor
                                if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                    BeanPostProcessor instance = (BeanPostProcessor) clazz.newInstance();
                                    beanPostProcessorsList.add(instance);
                                }

                                // Component（）写的名字
                                Component component = clazz.getAnnotation(Component.class);
                                String beanName = component.value();

                                if (beanName.equals("")) {
                                    // 首字母小写
                                    String simpleName = clazz.getSimpleName();
                                    beanName = Introspector.decapitalize(simpleName);
                                }

                                // 设置作用域：是否有 Scope 注解
                                BeanDefinition beanDefinition = new BeanDefinition();
                                // 设置 Bean 的类型
                                beanDefinition.setType(clazz);

                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    String scope = scopeAnnotation.value();
                                    if (scope.equals("prototype")) {
                                        beanDefinition.setScope("prototype");
                                    }
                                    beanDefinition.setScope(scope);
                                } else {
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        }
                    }
                }
            } catch (UnsupportedEncodingException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        // 2: 实例化单例 Bean
        beanDefinitionMap.keySet().forEach(beanName -> {
             BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
             if (beanDefinition.getScope().equals("singleton")) {
                 Object bean = createBean(beanName, beanDefinition);
                 singletonObjects.put(beanName, bean);
             }
        });
    }

    // Bean 的生命周期
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            // 无参的构造方法（前提是要创建的bean需要有一个无参构造方法）
            // 1: bean的实例化
            Object instance = clazz.getConstructor().newInstance();

            // 2：依赖注入
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);
                        f.set(instance, getBean(f.getName()));
                }
            }

            // 3: Aware 回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // 初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(beanName, instance);
            }

            // 4: 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // 初始化后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                instance = beanPostProcessor.postProcessAfterInitialization(beanName, instance);
            }

            // 需要 AOP 返回代理对象

            return instance;

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException("beanName = " + beanName);
        } else {
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                Object singletonBean = singletonObjects.get(beanName);
                if (singletonBean == null) {
                    Object o = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, o);
                }
                return singletonBean;
            } else {
                // 多例 Bean 每次都去创建
                return createBean(beanName, beanDefinition);
            }
        }

    }
}
