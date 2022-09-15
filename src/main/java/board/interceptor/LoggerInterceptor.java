package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("==================== START ====================");
     
   //System.out.println("preHandle");
        log.debug(" Request URI \t: " + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	  // System.out.println("postHandle");
    	   log.debug("==================== END ====================");
    }
    
    
    @Override
    public void afterCompletion(
      HttpServletRequest request, 
      HttpServletResponse response,
      Object handler, Exception ex) {
        // your code
    }
}