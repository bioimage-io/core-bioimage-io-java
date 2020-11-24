package io.bioimage.specification.util;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpecificationUtil {

	@Nullable
	public static Map<String, Object> asMap(Object in) {
		if(in == null) return null;
		if(Map.class.isAssignableFrom(in.getClass())) {
			return (Map<String, Object>) in;
		} else {
			return null;
		}
	}

	public static Map<String, Object> getOrAppendMap(Map<String, Object> parent, String id) {
		Map<String, Object> res = asMap(parent.get(id));
		if(res == null) {
			res = new HashMap<>();
			parent.put(id, res);
		}
		return res;
	}
}
