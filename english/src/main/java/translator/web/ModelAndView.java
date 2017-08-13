package belhard.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 17.06.2017.
 */
public class ModelAndView {
	private View view;
	private Map<String, Object> parameters = new HashMap<>();
	private int status;

	public ModelAndView() {
	}

	public ModelAndView(int status) {
		view = View.ERROR;
		this.status = status;
	}

	public ModelAndView(View view) {
		this.view = view;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
