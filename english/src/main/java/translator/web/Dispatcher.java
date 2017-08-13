package translator.web;

import com.google.common.collect.ImmutableList;
import translator.DataLayer.DataRetrievers.TopicRetriever;
import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DataRetrievers.WordRetriever;
import translator.DataLayer.DataRetrievers.UsersWordsRetriever;
import translator.annotation.RequestMapping;
import translator.controller.TopicController;
import translator.controller.UserWordController;
import translator.controller.UserController;
import translator.controller.WordController;
import translator.exceptions.IllegalRequestException;
import translator.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

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
				.add(new UserController(new UserRetriever()))
				.add(new UserWordController(new UsersWordsRetriever()))
				.add(new WordController(new WordRetriever()))
				.add(new TopicController(new TopicRetriever()))
				.build();
	}

	public static Dispatcher getInstance() {
		if (dispatcher == null) {
			dispatcher = new Dispatcher();
		}
		return dispatcher;
	}

	public ModelAndView dispatch(String url, HttpMethod httpMethod, Map<String, String[]> parametersMap) {

		Target target = getTargetForInvoke(url, httpMethod);
		if (target != null) {
			if (parametersMap != null && !parametersMap.isEmpty())
				fillTargetByParameters(target, parametersMap);

			Object result = invoker.invoke(target);
			return (ModelAndView) result;
		}
		return new ModelAndView(HttpStatus.PAGE_NOT_FOUND);
	}

	public <TParam> ModelAndView dispatchGeneric(String url, HttpMethod httpMethod, TParam parameter) {

		Target target = getTargetForGenericInvoke(url, httpMethod, parameter.getClass());
		if (target != null) {
			if (parameter != null)
				fillTargetBySingleParameter(target, parameter);
			Object result = invoker.invoke(target);
			return (ModelAndView) result;
		}
		return new ModelAndView(HttpStatus.PAGE_NOT_FOUND);
	}
	private <TParam> void fillTargetBySingleParameter(Target target, TParam parameter) {
		Parameter[] parameters = target.method.getParameters();
		if(parameters.length!=1) {
			return;
		}
		target.params[0] = parameter;
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

	private <TParam> Target getTargetForGenericInvoke(String requestedUrl, HttpMethod requestedHttpMethod, Class<TParam> type) {
		Target target = null;

		for (Controller controller : controllers) {
			Method[] methods = controller.getClass().getMethods();

			for (Method method : methods) {
				if (method.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping current = method.getAnnotation(RequestMapping.class);

					if (requestedHttpMethod == current.method() && StringUtils.equals(requestedUrl, current.url())) {
						target = new Target(controller, method);
						target.params = (TParam[])Array.newInstance(type, 1);
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
