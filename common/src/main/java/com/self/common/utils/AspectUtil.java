package com.self.common.utils;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.self.common.anno.OperationResult;
import com.self.common.anno.PageAnn;
import com.self.common.enums.LogLevelEnum;
import com.self.common.enums.ResultEnum;
import com.self.common.exception.SelfException;
import com.self.common.res.SelfResult;
import com.self.common.vo.PageParamVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Slf4j
public class AspectUtil {

    public static Object dealResult(ProceedingJoinPoint jp) throws Throwable {
        Date enterDate = new Date();
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        boolean printParam = false;
        boolean printRes = false;
        if (method.isAnnotationPresent(OperationResult.class)) {
            OperationResult result = method.getAnnotation(OperationResult.class);
            printParam = result.printParamLog();
            printRes = result.printResLog();
        } else if (method.isAnnotationPresent(PageAnn.class)) {
            PageAnn pageAnn = method.getAnnotation(PageAnn.class);
            printParam = pageAnn.printParamLog();
            printRes = pageAnn.printResLog();
        }
        Object[] args = jp.getArgs();
        printMethodParam(printParam, method, args);

        Object result = null;
        try {
            result = jp.proceed();
        } catch (Exception e) {
            if (e instanceof SelfException && LogLevelEnum.INFO.getCode().equals(
                            ((SelfException) e).getLogLevelEnum().getCode())) {
                log.info("发生异常", e);
            } else {
                log.error("发生错误", e);
            }
            throw e;
        }

        SelfResult selfResult = dealSelfResult(method, result, args);

        Date end = new Date();
        Long diff = enterDate.getTime() - end.getTime();
        log.info("方法{}，请求时间{}，完成时间{}，处理用时{}，操作人admin，参数{}",
                method.getName(), enterDate, end, diff, args);
        if (printRes) {
            log.info("结果为{}", result);
        }
        return selfResult;
    }

    private static SelfResult dealSelfResult(Method method, Object result, Object[] args) {
        if (null != method && method.isAnnotationPresent(PageAnn.class)) {
            PageParamVO pageParamVO = (PageParamVO) args[0];
            return getQueryData((List) result, pageParamVO.getPageIndex(), pageParamVO.getPageSize());
        }
        int total = 0;
        if (result instanceof List) {
            PageInfo page = PageInfo.of((List) result);
            total = (int) page.getTotal();
        }
        return new SelfResult(total, ResultEnum.SUCCESS.getCode(), result);
    }

    private static void printMethodParam(boolean printParam, Method method, Object[] args) {
        if (printParam) {
            log.info("方法名称{},参数{}", method.getName(), args);
        }
    }

    private static SelfResult getQueryData(List list, int currentPage, int pageSize) {
        if (CollectionUtils.isEmpty(list)) {
            return new SelfResult(0, ResultEnum.SUCCESS.getCode(), Lists.newArrayList());
        }
        log.info("currentPage {}, pageSize {}", currentPage, pageSize);
        int start = (currentPage - 1) * pageSize;
        start = start > list.size() || start < 0 ? 0 : start;
        int end = Math.min((start + pageSize), list.size());
        log.info("start {}, end {}", start, end);
        return new SelfResult(list.size(), ResultEnum.SUCCESS.getCode(), list.subList(start, end));
    }
}
