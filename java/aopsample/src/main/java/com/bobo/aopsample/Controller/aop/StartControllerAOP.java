package com.bobo.aopsample.Controller.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StartControllerAOP {
	
	@Pointcut("execution(public * com.bobo.aopsample.Controller.StartController.*(..))")
	public void play(){
		
	}
	
	@Before("play()")
	public void doBeforePlay(){
		System.out.println("Agent is preparing for the game!");
	}
	
	@After("play()")
	public void doAfterPlay(){
		System.out.println("Agent applauded for the start!");
	}
	
	@AfterReturning("play()")
	public void doAfterReturningPlay(){
		System.out.println("return notification:Agent applauded for the start!");
	}
	
	
	@Pointcut("execution(public * com.bobo.aopsample.Controller.StartController.durant(int)) && args(point)")
	public void playdata(int point){
		
	}
	
	@Around("playdata(point)")
	public void doRoundData(ProceedingJoinPoint pjp, int point) throws Throwable{
		try{
			
			System.out.println("Start is doing warm-up");
			pjp.proceed();
			System.out.println("Start got " + point + " points");
		}
		catch(Throwable e){
			System.out.println("Excetion Notificaiton: Fans want to refund ticket!");
		}
	}

}
