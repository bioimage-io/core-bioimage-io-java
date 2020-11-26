package io.bioimage.specification.transformation;

public class ScaleMinMaxTransformation extends DefaultImageTransformation {

	public static final String name = "scale_min_max";
	private String referenceInput;
	private Number minPercentile;
	private Number maxPercentile;

	public Number getMinPercentile() {
		return minPercentile;
	}

	public void setMinPercentile(Number minPercentile) {
		this.minPercentile = minPercentile;
	}

	public Number getMaxPercentile() {
		return maxPercentile;
	}

	public void setMaxPercentile(Number maxPercentile) {
		this.maxPercentile = maxPercentile;
	}

	public String getReferenceInput() {
		return referenceInput;
	}

	public void setReferenceInput(String referenceInput) {
		this.referenceInput = referenceInput;
	}

	@Override
	public String getName() {
		return name;
	}
}
