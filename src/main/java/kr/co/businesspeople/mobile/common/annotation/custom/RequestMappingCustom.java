/**
 * 
 */
package kr.co.businesspeople.mobile.common.annotation.custom;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ykleem
 * @date   2017. 7. 14.
 * @Desc   사용자가 다녀가는 경로를 추적하기 위해 흔적을 남긴다. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface RequestMappingCustom {
	
	/**
	 * 
	 * @Auth ykleem
	 * @Date 2017. 7. 19.
	 * @Description 
	 *  > 각 URL의 한글 이름을 넣는다.
	 */
	String path_kr() default "NOTITLE";
	String path_en() default "/";
}
