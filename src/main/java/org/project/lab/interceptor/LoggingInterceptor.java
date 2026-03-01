package org.project.lab.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Interceptor
@Logged
public class LoggingInterceptor implements Serializable {

  private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

  @AroundInvoke
  public Object logMethod(InvocationContext ctx) throws Exception {
    String className = ctx.getTarget().getClass().getSuperclass().getSimpleName();
    String methodName = ctx.getMethod().getName();
    String time = LocalDateTime.now().toString();
    String userName = "system";

    LOGGER.info(String.format("[%s] User: %s | %s.%s() | Invoked",
        time, userName, className, methodName));

    try {
      Object result = ctx.proceed();
      LOGGER.info(String.format("[%s] User: %s | %s.%s() | Completed successfully",
          time, userName, className, methodName));
      return result;
    } catch (Exception e) {
      LOGGER.severe(String.format("[%s] User: %s | %s.%s() | FAILED: %s",
          time, userName, className, methodName, e.getMessage()));
      throw e;
    }
  }
}