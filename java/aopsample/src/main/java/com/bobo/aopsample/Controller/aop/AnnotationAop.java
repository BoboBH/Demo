package com.bobo.aopsample.Controller.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@Component
public class AnnotationAop {

	@Pointcut(value="@annotation(log)", argNames="log")
	public void pointcut(AopLog log){
		
	}
	
	@Around(value="pointcut(log)", argNames="joinPoint,log")
	public Object doAround(ProceedingJoinPoint joinPoint, AopLog log) throws Throwable {
		try{
			System.out.println("Before api and log= " + log.value());
			System.out.println("Target Class is :" + joinPoint.getTarget().getClass().getName());
			System.out.println("Target Method is :" + joinPoint.getSignature().getName());
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if(requestAttributes != null){
				HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
				System.out.println("Request IP Address:" + request.getRemoteAddr());
				System.out.println("Request IP Address:" + request.getRequestURI());
			}
			return joinPoint.proceed();
		}
		catch (Exception e) {
			System.out.println("Occur an exception on around function");
			e.printStackTrace();
			throw e;
		}
		finally {
			System.out.println("Around end");
		}
	}
}
