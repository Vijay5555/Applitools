package com.applitools.quickstarts;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

public class FigmaGetter extends MockGetter {

	public FigmaGetter(String x_token, String pid) {
		super("https://api.figma.com/v1", x_token, pid, GetterType.FIGMA);
	}

	public JSONObject getProjectFiles() {

		JSONObject obj = httpReq("/files/" + pid);

		return obj;
	}

	public String[] getScreenImageUrl(String screenId) {

		JSONObject obj = httpReq("/images/" + pid + "?ids=" + screenId);
		String[] image = new String[1];

		image[0] = obj.getJSONObject("arr").getJSONObject("images").getString(screenId);

		image = ArrayUtils.addAll(image, getScreenImageSize(screenId));

		return image;

	};

	public String[] getScreenImageSize(String screenId) {
		String[] size = new String[2];

		JSONObject obj = httpReq("/files/" + pid + "/nodes?ids=" + screenId);

		obj = obj.getJSONObject("arr").getJSONObject("nodes").getJSONObject(screenId).getJSONObject("document")
				.getJSONObject("absoluteBoundingBox");

		size[0] = obj.getInt("width") + "";
		size[1] = obj.getInt("height") + "";

		return size;
	}

}
