package org.quan.demoutils.utils;

import lombok.extern.slf4j.Slf4j;
import org.quan.demoutils.entity.User;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Function接口实现优雅断言
 */
@Slf4j
public class AssertionUtils {
    /**
     * 通用断言方法，接受一个Function作为验证逻辑
     *
     * @param input        待验证的输入
     * @param assertion    验证函数
     * @param errorMessage 错误消息
     * @param <T>          输入类型
     * @throws IllegalArgumentException 验证失败时抛出
     */
    public static <T> void assertThat(T input, Function<T, Boolean> assertion, String errorMessage) {
        if (!assertion.apply(input)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * 支持多个断言条件的方法
     *
     * @param input      待验证的输入
     * @param assertions 多个验证函数
     * @param <T>        输入类型
     */
    @SafeVarargs
    public static <T> void assertAll(T input, Function<T, Boolean>... assertions) {
        for (Function<T, Boolean> assertion : assertions) {
            assertThat(input, assertion, "多重断言验证失败");
        }
    }

    /**
     * 带上下文的断言方法
     *
     * @param input     输入
     * @param context   上下文对象
     * @param assertion 带上下文的验证函数
     * @param <T>       输入类型
     * @param <C>       上下文类型
     */
    public static <T, C> void assertWithContext(T input, C context, BiFunction<T, C, Boolean> assertion) {
        if (!assertion.apply(input, context)) {
            throw new IllegalArgumentException("上下文断言验证失败");
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        User user = new User();
        user.setId(1L);
        user.setName("jcen");
        user.setPassword("123456");

        try {
            new AssertionUtils().createUser(user);
        } catch (IllegalArgumentException e) {
            log.error("创建用户失败：{}", e.getMessage());
        }
    }

    public void createUser(User user) {
        log.info("assert start");

        // 测试 assertThat
        AssertionUtils.assertThat(user, u -> u != null, "用户对象不能为空");
//        AssertionUtils.assertThat(user, u -> u.getPassword().length() > 8, "密码长度需大于8位");

        // 测试 assertAll
        AssertionUtils.assertAll(user,
                u -> u.getName() != null && !u.getName().isEmpty(),
                u -> u.getPassword() != null && u.getPassword().matches(".*[0-9]+.*"),
                u -> u.getId() > 0
        );

        // 测试 assertWithContext
        String validNamePrefix = "jc";
        AssertionUtils.assertWithContext(user, validNamePrefix, (u, prefix) ->
                u.getName() != null && u.getName().startsWith(prefix)
        );

        log.info("assert end");
    }
}