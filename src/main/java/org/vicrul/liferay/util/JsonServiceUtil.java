package org.vicrul.liferay.util;

import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

public class JsonServiceUtil {
	public static void writeJson(PrintWriter writer, List<Object> objects) {
		Gson gson = new Gson();
		if (writer == null) {
			return;
		}
		String jsonString = gson.toJson(objects);
		writer.print(jsonString);
		writer.flush();
	}
}
