package mk.ukim.finki.mp.smsManagement.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAclFilter {
	public String permission();
	public String typeOfPermission();
}
