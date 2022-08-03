package com.applitools.quickstarts;

import org.json.JSONObject;

//ZeplinGetter zeplinGetter = new ZeplinGetter("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoicGVyc29uYWxfYWNjZXNzX3Rva2VuIiwiY2xpZW50X2lkIjoiNjJjNDBiNWY0NDA2N2MxM2UzYjkxMGY0Iiwic2NvcGUiOiJhZG1pbiIsImlhdCI6MTY1NzAxNTEzNSwiZXhwIjoxOTcyNTg0Mzk1LCJpc3MiOiJ6ZXBsaW46YXBpLnplcGxpbi5pbyIsInN1YiI6IjYyYzQwYWY1YWUyNDE3MTg5ZDMzNTNhOSIsImp0aSI6IjJhNzZmZTQ4LWExZTQtNGRlMS1iMzdmLWY1N2ZlYWQwMDUxZiJ9.Sr-3eVe0V8rFyIn0tLp0LTNViQYFd1WO8oPqFVCRmqk","62c40b11ca569612ec069f19");

//zeplinGetter.getProjectFiles();
//String[] imageData=zeplinGetter.getScreenImageUrl("62c40b11ca569612ec069fcc");




public class ZeplinGetter extends MockGetter {

	public ZeplinGetter(String x_token, String pid) {
		super("https://api.zeplin.dev/v1", x_token, pid, GetterType.ZEPLIN);
	}

	public JSONObject getProjectFiles() {

		JSONObject obj = httpReq("/projects/" + pid + "/screens");

		return obj;
	}

	public String[] getScreenImageUrl(String screenId) {

		JSONObject obj = httpReq("/projects/" + pid + "/screens/" + screenId);
		String[] image = new String[3];

		image[0] = obj.getJSONObject("arr").getJSONObject("image").getString("original_url");
		image[1] = obj.getJSONObject("arr").getJSONObject("image").get("width").toString();
		image[2] = obj.getJSONObject("arr").getJSONObject("image").get("height").toString();

		return image;
	};

}
