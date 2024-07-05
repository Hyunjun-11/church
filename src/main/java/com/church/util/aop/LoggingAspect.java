package com.church.util.aop;

import com.church.domain.board.dto.BoardResponseDto;
import com.church.util.annotation.LogMethodName;
import com.church.util.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @AfterReturning(pointcut = "@annotation(logMethodName)", returning = "result")
    public void logReturnValue(LogMethodName logMethodName, Object result) {
        log.info("Return: " + (result != null ? result.toString() : "null"));
    }

    private Long extractMemberIdFromResult(Object result) {
        // 여기서는 간단한 예로 result 객체에서 memberId를 추출하는 로직을 추가합니다.
        // 실제로는 result 객체의 타입에 맞게 적절한 필드에서 값을 추출해야 합니다.
        if (result instanceof Message) {
            Object data = ((Message<?>) result).getData();
            if (data instanceof BoardResponseDto) {
                return ((BoardResponseDto) data).getMemberId();
            }
        }
        return null; // 적절한 기본값 또는 예외 처리 필요
    }
}