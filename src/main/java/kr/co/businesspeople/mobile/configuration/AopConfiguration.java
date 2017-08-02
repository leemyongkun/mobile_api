/**
 * 
 */
package kr.co.businesspeople.mobile.configuration;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.businesspeople.mobile.common.annotation.custom.RequestMappingCustom;

/**
 * @author ykleem
 * @date   2017. 7. 13.
 * @Desc   
 */
//@EnableAspectAutoProxy(proxyTargetClass=true) - Proxy를 사용하지 않는 LegacyCode를 사용할수있게 해준다. (==TX , Bean 등)
@Configuration
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class AopConfiguration {
	
	 final Logger logger =  LoggerFactory.getLogger("aop");
			
	@Before("execution(* kr.co.businesspeople.mobile.controller.*.*(..))") 
	public void actionBefore(JoinPoint joinPoint) { 
		
		//RequestMappingCustom의 값을 가져온다.		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		RequestMappingCustom pathDesc = method.getAnnotation(RequestMappingCustom.class);
		
		logger.info(
					((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("USER_ID")+"\t" +
					((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("USER_NAME")+"\t" +
					pathDesc.path_en()+"\t"+
					pathDesc.path_kr()+"\t"+
					joinPoint.getSignature().toShortString()+"\t"+
					getParameter(joinPoint.getArgs())+"\t"+
					((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getHeader("referer")+"\t"
				);
		
	}

	/*@AfterThrowing(pointcut="execution(* kr.co.businesspeople.mobile.controller.*.*(..))", throwing = "ex")
	public void errorInterceptor( JoinPoint joinPoint, Throwable ex) {
	   
		if (logger.isDebugEnabled()) {
	        logger.debug("Error Message Interceptor started");
	    }

	    // DO SOMETHING HERE WITH EX
	   System.out.println( ">>"+ex.getCause().getMessage());


	    if (logger.isDebugEnabled()) {
	        logger.debug("Error Message Interceptor finished.");
	    }
	}*/
	
	/**
	 * @Auth ykleem
	 * @Date 2017. 7. 20.
	 * @Description 
	 *  > Request로 들어오는 Parameter를 JSON 형태로 변경한다.
	 *  > 단, Controller에서 모든 Parameter는 기본적으로 HashMap을 이용하게 하며, 이외의 Object는 무시된다.
	 *  > 추가하여 사용할 수 있으며, 분기처리하여 instanceof로구분한다. 
	 */
	private String getParameter(Object[] args) {
		for(Object obj : args){
			if(obj instanceof HashMap){
				//[TODO] Singleton으로 변경해야함 : ykleem@20170720
				return new ObjectMapper().convertValue(((HashMap<String,?>)obj), HashMap.class).toString(); 
			}
		}
		return "";
	}
	
	 
	/*Object[] args = joinPoint.getArgs();
	Annotation[][] parameterAnnotations = method.getParameterAnnotations();
	System.out.println("args.length " + args.length );
	System.out.println("parameterAnnotations.length " + parameterAnnotations.length );
	assert args.length == parameterAnnotations.length:new Exception(); //-ea 옵션
	
	 for (int argIndex = 0; argIndex < args.length; argIndex++) {
            for (Annotation annotation : parameterAnnotations[argIndex]) {
            	System.out.println("annotation.annotationType().getName() " + annotation.annotationType().getName());
                
            	if (!(annotation instanceof RequestParam))
                    continue;
                
                RequestParam requestParam = (RequestParam) annotation;
                
                if (! "param".equals(requestParam.value()))
                    continue;
                System.out.println("  " + requestParam.value() + " = " + args[argIndex]);
            }
        }*/
}
