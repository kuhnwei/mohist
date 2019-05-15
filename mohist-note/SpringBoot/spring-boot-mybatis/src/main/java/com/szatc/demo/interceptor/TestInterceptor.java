package com.szatc.demo.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/21 21:20
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class}) })
public class TestInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Pattern pattern = Pattern.compile("\\n(\\s)+");

    public TestInterceptor() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("invocation = [" + invocation + "]");
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = null;
        Matcher m = pattern.matcher(boundSql.getSql());
        if (m.find()) {
            sql= m.replaceAll(" ");
        }
        logger.info("mybatis intercept sql:{}", sql);
        Field sqlField = boundSql.getClass().getDeclaredField("sql");
        sqlField.setAccessible(true);
        sqlField.set(boundSql, sql);
        Object result = invocation.proceed();
        return result;
    }

    @Override
    public Object plugin(Object target) {
        logger.info("target = [" + target + "]");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
