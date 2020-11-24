package io.bioimage.specification;

import java.io.IOException;
import java.util.Map;

import static io.bioimage.specification.util.SpecificationUtil.asMap;
import static io.bioimage.specification.util.SpecificationUtil.getOrAppendMap;

public class CustomSpecification extends DefaultModelSpecification {

		private final static String idConfig = "config";
		private final static String idConfigMyTool = "mytool";
		private final static String idConfigMyToolSecret = "secret";

		private String secret;

		@Override
		protected boolean read(Map<String, Object> root) throws IOException {
			boolean success = super.read(root);
			if(success) {
				success = readConfig(root);
			}
			return success;
		}

		@Override
		public Map<String, Object> write() {
			Map<String, Object> root = super.write();
			writeConfig(root);
			return root;
		}

		private boolean readConfig(Map<String, Object> root) {
			Map<String, Object> config = asMap(root.get(idConfig));
			if(config == null) return false;
			Map<String, Object> myTool = asMap(config.get(idConfigMyTool));
			if(myTool == null) return false;
			secret = myTool.get(idConfigMyToolSecret).toString();
			return true;
		}

		private void writeConfig(Map<String, Object> root) {
			Map<String, Object> config = getOrAppendMap(root, idConfig);
			Map<String, Object> myTool = getOrAppendMap(config, idConfigMyTool);
			myTool.put(idConfigMyToolSecret, secret);
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getSecret() {
			return secret;
		}
	}
