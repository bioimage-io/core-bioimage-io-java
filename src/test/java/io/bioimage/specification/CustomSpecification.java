package io.bioimage.specification;

import java.util.HashMap;
import java.util.Map;

import static io.bioimage.specification.util.SpecificationUtil.asMap;
import static io.bioimage.specification.util.SpecificationUtil.getOrAppendMap;

public class CustomSpecification extends DefaultModelSpecification {

		private final static String idConfigMyTool = "mytool";
		private final static String idConfigMyToolSecret = "secret";

		public void setSecret(String secret) {
			Map<String, Object> config = this.getConfig();
			if(config == null) config = new HashMap<>();
			Map<String, Object> myTool = getOrAppendMap(config, idConfigMyTool);
			myTool.put(idConfigMyToolSecret, secret);
			setConfig(config);
		}

		public String getSecret() {
			Map<String, Object> config = this.getConfig();
			if(config == null) return null;
			Map<String, Object> myTool = asMap(config.get(idConfigMyTool));
			if(myTool == null) return null;
			return myTool.get(idConfigMyToolSecret).toString();
		}
	}
