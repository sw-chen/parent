package com.sw.minitor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Map.Entry;


@Aspect
public class SwaggerAccessLogPoint {
	private final static Logger logger = LoggerFactory.getLogger("swagger-access-log");
	private final static ThreadLocal<String> topic = new ThreadLocal<>();
	private final static ThreadLocal<Long> start = new ThreadLocal<>();
	private static Gson gson = new Gson();
	private MonitorConfig monitorConfig;

	public SwaggerAccessLogPoint(MonitorConfig monitorConfig) {
		this.monitorConfig = monitorConfig;
	}
	
//	@Pointcut("execution(public * com.dashi1314..*.controller..*.*(..))")
	@Pointcut("within(com.sw..controller..*)")
	private void excudeService() {
		System.out.println("sssssssssssssssssssssssssssssss");
	}

	@Before("excudeService()")
	public void recordLogger(JoinPoint  point) {
		if (logger.isInfoEnabled()) {
			if (monitorConfig.isAfterReturn()) {
				start.set(System.currentTimeMillis());
			}
			if (monitorConfig.isBeforeLog()) {
				String info = buildTopic(point);
				topic.set(info);
				logger.info(info);
			}
		}

	}

	@ConditionalOnProperty(prefix="ssds.monitor", name="after-return-log", havingValue="true", matchIfMissing = true)
	@AfterReturning(value = "excudeService()", returning = "result")
	public void afterReturn(JoinPoint jp, Object result){
		if (monitorConfig.isAfterReturn() && logger.isInfoEnabled()) {
			Long st = start.get();
			if (st != null) {
				st = System.currentTimeMillis() - st;
			} else {
				st = -1L;
			}
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			boolean isGet = request.getMethod().equalsIgnoreCase("GET");
			String info = buildTopic(jp);
			ResultStringWriter rsw = new ResultStringWriter((isGet ? 1024 : 150) + info.length(), info.length() + monitorConfig.getMaxResultLength());
			rsw.append(info);
			rsw.append("\n+++++ 耗时: ").append(String.valueOf(st)).append("ms\t请求结果 -> ");
			if (result == null) {
				rsw.append("null");
			} else {
				try {
					gson.toJson(result, result.getClass(), rsw);
				} catch (ResultTooLongException igro) {}
			}
			logger.info(rsw.toString());
		}

	}

	/**
	 * 异常通知：目标方法发生异常的时候执行以下代码
	 */
	@ConditionalOnProperty(prefix="ssds.monitor", name="after-throw-log", havingValue="true", matchIfMissing = true)
	@AfterThrowing(value="excudeService()",throwing="e")
	public void afterThorwingMethod(JoinPoint jp, Exception e){
		if (monitorConfig.isAfterThrow()) {
			String info = buildTopic(jp);
			StringBuffer sb = new StringBuffer(info.length() + 256).append(info);
			sb.append("\n+++++ 请求异常 -> ").append(e.getMessage());
			logger.error(sb.toString(), e);
		}
	}

	private String buildTopic(JoinPoint  point) {
		String info = topic.get();
		if (info != null) {
			topic.remove();
			return info;
		}
		Class<?> target = point.getTarget().getClass();
		Api api = target.getAnnotation(Api.class);
		MethodSignature s = (MethodSignature) point.getSignature();
		Method m = s.getMethod();
		ApiOperation ao = m.getAnnotation(ApiOperation.class);

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		StringBuilder url = new StringBuilder(256);
		url.append('【').append(ao == null ? m.getName() : ao.value()).append("@").append(api == null ? target.getName() : api.value()).append("】\t").append(request.getMethod());
		url.append(' ').append(request.getRequestURI());
		url.append("\t{");
		Map<String, String[]> args = request.getParameterMap();
		if (args != null && !args.isEmpty()) {

			JsonObject jo = new JsonObject();
			for (Entry<String, String[]> e : args.entrySet()) {
				if (e.getValue().length == 1) {
					jo.addProperty(e.getKey(), e.getValue()[0]);
				} else {
					JsonArray ja = new JsonArray();
					for (String v : e.getValue()) {
						ja.add(v);
					}
					jo.add(e.getKey(), ja);
				}
			}
			url.append("\"query\":").append(jo.toString()).append(',');

		}

		String body = getRequestBodyParameter(m, point.getArgs());
		if (body != null) {
			url.append("\"body\":").append(body).append(',');
		}
		int len = url.length() - 1;
		if (url.charAt(len) == ',') {
			url.setCharAt(len, '}');
		} else {
			url.setLength(len);
		}
		return url.toString();
	}

	
	private String getRequestBodyParameter(Method m, Object[] vals) {
		try {
		 	if (vals != null && vals.length > 0) {
			 	Parameter[] args = m.getParameters();
		 		for (int i = 0; i < args.length; i++) {
		 			RequestBody rb = args[i].getAnnotation(RequestBody.class);
		 			if (rb != null) {
		 				Object val = vals[i];
		 				return val == null ? null : gson.toJson(val);
		 			}
				}
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
