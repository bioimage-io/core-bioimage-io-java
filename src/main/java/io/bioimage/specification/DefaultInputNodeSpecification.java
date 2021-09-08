/*-
 * #%L
 * Java implementation of the bioimage.io model specification.
 * %%
 * Copyright (C) 2020 - 2021 Center for Systems Biology Dresden
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.bioimage.specification;

import java.util.List;

public class DefaultInputNodeSpecification extends DefaultNodeSpecification implements InputNodeSpecification {

	private List<Integer> shapeMin;
	private List<Integer> shapeStep;
	private List<TransformationSpecification> preprocessing;


	@Override
	public void setShapeMin(List<Integer> shapeMin) {
		this.shapeMin = shapeMin;
	}

	@Override
	public void setShapeStep(List<Integer> shapeStep) {
		this.shapeStep = shapeStep;
	}

	@Override
	public void setPreprocessing(List<TransformationSpecification> preprocessing) {
		this.preprocessing = preprocessing;
	}

	@Override
	public List<Integer> getShapeMin() {
		return shapeMin;
	}

	@Override
	public List<Integer> getShapeStep() {
		return shapeStep;
	}

	@Override
	public List<TransformationSpecification> getPreprocessing() {
		return preprocessing;
	}

}
