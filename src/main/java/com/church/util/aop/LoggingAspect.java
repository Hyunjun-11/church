package com.church.util.aop;

import com.church.domain.board.dto.BoardResponseDto;
import com.church.domain.members.entity.Members;
import com.church.util.annotation.LogMethodName;
import com.church.util.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @AfterReturning(pointcut = "@annotation(logMethodName)", returning = "result")
    public void logReturnValue(LogMethodName logMethodName, Object result) {
        log.info("Return: " + (result != null ? result.toString() : "null"));
    }

    @Around("@annotation(logMethodName)")
    public Object logReturnValue2(ProceedingJoinPoint joinPoint, LogMethodName logMethodName) throws Throwable {
        // 메서드 실행 전
        Object[] args = joinPoint.getArgs();
        Members member = null;

        for (Object arg : args) {
            if (arg instanceof Members) {
                member = (Members) arg;
                break;
            }
        }

        // 메서드 실행
        Object result = joinPoint.proceed();

        // 메서드 실행 후
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            if (responseEntity.getBody() instanceof Message) {
                Message<?> message = (Message<?>) responseEntity.getBody();
                if (message.getData() instanceof BoardResponseDto) {
                    BoardResponseDto boardResponseDto = (BoardResponseDto) message.getData();
                    Long boardId = boardResponseDto.getBoardId();
                    String logContent = message.getMessage() + " - Board ID: " + boardId;
                    Long memberId = member != null ? member.getId() : null;

                    // 로그 저장
//                    Log logEntity = new Log(logContent, memberId);
//                    logRepository.save(logEntity);

                    log.info("Return: " + logContent);
                }
            }
        }

        return result;
    }
}