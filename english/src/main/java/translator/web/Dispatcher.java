package belhard.web;

import belhard.annotation.RequestMapping;
import belhard.controller.UserController;
import belhard.dao.UserDAO;
import belhard.exception.IllegalRequestException;
import belhard.service.UserService;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Front Controller Dispatcher. Route request to controller
 * by url in request. Invoke target method of the controller.
 * Dispatcher keeps all controller instances.
 */
public class Dispatcher {
	private List<Controller> controllers;
	private Invoker invoker = new Invoker();
	private static Dispatcher dispatcher;

	private Dispatcher() {
		controllers = ImmutableList.<Controller>builder()
				.add(new UserController(new UserService(new UserDAO())))
				.build();
	}

	public static Dispatcher getInstance() {
		if (dispatcher == null) {
			dispatcher = new Dispatcher();
		}
		return dispatcher;
	}

	public ModelAndView dispatch(String url, String method, Map<String, String[]> parametersMap) {
		HttpMethod httpMethod = HttpMethod.valueOf(method);
		Target target = getTargetForInvoke(url, httpMethod);
		if (target != null) {
			fillTargetByParameters(target, parametersMap);

			Object result = invoker.invoke(target);
			return (ModelAndView) result;
		}
		return new ModelAndView(HttpStatus.PAGE_NOT_FOUND);
	}

	private void fillTargetByParameters(Target target, Map<String, String[]> parametersMap) {
		Parameter[] parameters = target.method.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			String[] strings = parametersMap.get(parameters[i].getName());
			if (strings != null) {
				target.params[i] = strings[0];
			}
		}
	}

	private Target getTargetForInvoke(String requestedUrl, HttpMethod requestedHttpMethod) {
		Target target = null;

		for (Controller controller : controllers) {
			Method[] methods = controller.getClass().getMethods();

			for (Method method : methods) {
				if (method.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping current = method.getAnnotation(RequestMapping.class);

					if (requestedHttpMethod == current.method() && StringUtils.equals(requestedUrl, current.url())) {
						target = new Target(controller, method);
						target.params = new String[method.getParameterCount()];
						break;
					}
				}
			}
			if (target != null) {
				break;
			}
		}
		return target;
	}

	private static class Invoker {
		private Object invoke(Target target) {
			try {
				target.method.setAccessible(true);

				return target.method.invoke(target.controller, target.params);
			} catch (Exception e) {
				throw new IllegalRequestException(e.getMessage());
			}
		}
	}

	private static class Target {
		private Controller controller;
		private Method method;
		private Object[] params;

		Target(Controller controller, Method method) {
			this.controller = controller;
			this.method = method;
		}
	}
}
