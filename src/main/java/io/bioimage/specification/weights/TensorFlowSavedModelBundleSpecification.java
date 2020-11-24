package io.bioimage.specification.weights;

import io.bioimage.specification.DefaultWeightsSpecification;

public class TensorFlowSavedModelBundleSpecification extends DefaultWeightsSpecification {
	public static final String id = "tensorflow-saved-model-bundle";
	private String tag = "serve";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String getId() {
		return id;
	}
}
