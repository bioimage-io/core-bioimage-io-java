package io.bioimage.specification.transformation;

import io.bioimage.specification.TransformationSpecification;

public interface AxesBasedTransformation extends TransformationSpecification {

    String getAxes();
    void setAxes(String axes);
}
