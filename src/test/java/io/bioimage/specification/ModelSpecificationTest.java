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

import io.bioimage.specification.io.SpecificationReader;
import io.bioimage.specification.io.SpecificationWriter;
import io.bioimage.specification.transformation.BinarizeTransformation;
import io.bioimage.specification.transformation.ClipTransformation;
import io.bioimage.specification.transformation.ImageTransformation;
import io.bioimage.specification.transformation.ScaleLinearTransformation;
import io.bioimage.specification.transformation.ScaleMinMaxTransformation;
import io.bioimage.specification.transformation.ZeroMeanUnitVarianceTransformation;
import io.bioimage.specification.weights.TensorFlowSavedModelBundleSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ModelSpecificationTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	// example values
	private final static String source = "trainingSource";
	private final static List<AuthorSpecification> modelAuthors = Arrays.asList(
			new DefaultAuthorSpecification("author1"),
			new DefaultAuthorSpecification("author2"));
	private final static String description = "description";
	private final static String documentation = "DOCUMENTATION_LINK";
	private final static String license = "bsd";
	private final static String modelName = "model name";
	private final static Double mean = 100.;
	private final static Double std = 10.;
	private final static String gitRepo = "https://github.com/name/repo";
	private final static String citationText = "Publication name, authors, journal";
	private final static String doi = "DOI";
	private final static List<String> tags = Arrays.asList("tag1", "tag2");
	private final static String inputName = "input";
	private final static String axes = "XYZC";
	private final static List<Integer> shapeMin = Arrays.asList(4, 4, 4, 1);
	private final static List<Integer> shapeStep = Arrays.asList(16, 16, 16, 0);
	private final static List<Integer> halo = Arrays.asList(16, 16, 16, 1);
	private final static List<String> dataRange = Arrays.asList("-inf", "inf");
	private final static String dataType = "float";
	private final static String output = "output";
	private final static List<Integer> shapeOffset = Arrays.asList(0, 0, 0, 3);
	private final static List<Double> shapeScale = Arrays.asList(2., 2., 2., 1.);
	private final static String testInput = "input.png";
	private final static String testOutput = "output.png";
	private final static Map<String, String> attachments = Collections.singletonMap("manifest", "./manifest/README.txt");
	private final static String weightsSha256 = "1234567";
	private final static String weightsSource = "./weights.zip";
	private final static String timestamp = new Timestamp(System.currentTimeMillis()).toString();
	private final static ImageTransformation.Mode mode = ImageTransformation.Mode.FIXED;
	private final static Number threshold = 0.5;
	private final static Number min = 3;
	private final static Number max = 4;
	private Number minPercentile = 0.3;
	private Number maxPercentile = 99.8;

	@Test
	public void testEmptySpec() throws IOException {

		// create spec

		ModelSpecification specification = new DefaultModelSpecification();

		// write spec

		File dir = folder.getRoot();
		SpecificationWriter.write(specification, dir);

		// check if files exist and are not empty

		File modelFile = new File(dir.getAbsolutePath(), SpecificationWriter.getModelFileName());
		assertTrue(modelFile.exists());
		File dependencyFile = new File(dir.getAbsolutePath(), SpecificationWriter.dependenciesFileName);
		assertTrue(dependencyFile.exists());
		String content = FileUtils.readFileToString(modelFile, StandardCharsets.UTF_8);
		assertFalse(content.isEmpty());
		content = FileUtils.readFileToString(dependencyFile, StandardCharsets.UTF_8);
		assertFalse(content.isEmpty());

		// read spec
		DefaultModelSpecification newSpec = new DefaultModelSpecification();
		assertTrue(SpecificationReader.readFromDirectory(dir, newSpec));

		// check default spec values
		assertEquals(DefaultModelSpecification.modelZooSpecificationVersion, newSpec.getFormatVersion());
	}

	@Test
	public void testExampleSpec() throws IOException {

		// create spec

		File dir = folder.getRoot();
		DefaultModelSpecification specification = new DefaultModelSpecification();
		setExampleValues(specification);

		// check values
		checkExampleValues(specification);

		// write spec
		SpecificationWriter.write(specification, dir);
		File modelFile = new File(dir.getAbsolutePath(), SpecificationWriter.getModelFileName());
		assertTrue(modelFile.exists());
		String content = FileUtils.readFileToString(modelFile, StandardCharsets.UTF_8);
		System.out.println(content);

		// read spec
		DefaultModelSpecification newSpec = new DefaultModelSpecification();
		assertTrue(SpecificationReader.readFromDirectory(dir, newSpec));

		// check values
		checkExampleValues(newSpec);

	}

	private void setExampleValues(DefaultModelSpecification specification) {
		// meta
		specification.setName(modelName);
		specification.setAuthors(modelAuthors);
		specification.setDescription(description);
		specification.setDocumentation(documentation);
		specification.setLicense(license);
		specification.setSource(source);
		specification.setGitRepo(gitRepo);
		specification.setTags(tags);
		specification.setTimestamp(timestamp);
		CitationSpecification citation = new DefaultCitationSpecification();
		citation.setCitationText(citationText);
		citation.setDOIText(doi);
		specification.addCitation(citation);
		specification.setSampleInputs(Collections.singletonList(testInput));
		specification.setSampleOutputs(Collections.singletonList(testOutput));
		specification.setAttachments(attachments);
		// transformations
		ScaleLinearTransformation scaleLinear = getScaleLinearTransformation();
		BinarizeTransformation binarize = getBinarizeTransformation();
		ClipTransformation clip = getClipTransformation();
		ZeroMeanUnitVarianceTransformation zeroMean = getZeroMeanUnitVarianceTransformation();
		ScaleMinMaxTransformation scaleMinMax = getScaleMinMaxTransformation();
		// input node
		InputNodeSpecification inputNode = new DefaultInputNodeSpecification();
		inputNode.setShapeMin(shapeMin);
		inputNode.setShapeStep(shapeStep);
		inputNode.setAxes(axes);
		inputNode.setDataRange(dataRange);
		inputNode.setDataType(dataType);
		inputNode.setName(inputName);
		inputNode.setHalo(halo);
		inputNode.setPreprocessing(Arrays.asList(clip, zeroMean));
		specification.addInputNode(inputNode);
		// output node
		OutputNodeSpecification outputNode = new DefaultOutputNodeSpecification();
		outputNode.setName(output);
		outputNode.setAxes(axes);
		outputNode.setShapeOffset(shapeOffset);
		outputNode.setShapeReferenceInput(inputName);
		outputNode.setShapeScale(shapeScale);
		outputNode.setPostprocessing(Arrays.asList(binarize, clip, scaleMinMax, scaleLinear));
		specification.addOutputNode(outputNode);
		// weights
		TensorFlowSavedModelBundleSpecification weights = new TensorFlowSavedModelBundleSpecification();
		weights.setSha256(weightsSha256);
		weights.setSource(weightsSource);
		specification.addWeights(TensorFlowSavedModelBundleSpecification.id, weights);
	}

	private ScaleMinMaxTransformation getScaleMinMaxTransformation() {
		ScaleMinMaxTransformation res = new ScaleMinMaxTransformation();
		res.setMinPercentile(minPercentile);
		res.setMaxPercentile(maxPercentile);
		res.setReferenceInput(inputName);
		res.setMode(mode);
		return res;
	}

	private ScaleLinearTransformation getScaleLinearTransformation() {
		ScaleLinearTransformation res = new ScaleLinearTransformation();
		res.setGain(std);
		res.setOffset(mean);
		res.setMode(mode);
		return res;
	}

	private ZeroMeanUnitVarianceTransformation getZeroMeanUnitVarianceTransformation() {
		ZeroMeanUnitVarianceTransformation res = new ZeroMeanUnitVarianceTransformation();
		res.setMean(mean);
		res.setStd(std);
		res.setMode(mode);
		return res;
	}

	private BinarizeTransformation getBinarizeTransformation() {
		BinarizeTransformation res = new BinarizeTransformation();
		res.setThreshold(threshold);
		res.setMode(mode);
		return res;
	}

	private ClipTransformation getClipTransformation() {
		ClipTransformation res = new ClipTransformation();
		res.setMin(min);
		res.setMax(max);
		res.setMode(mode);
		return res;
	}

	private void checkExampleValues(ModelSpecification specification) {
		// meta
		assertEquals(modelName, specification.getName());
		assertEquals(modelAuthors.stream().map(spec -> spec.getName()).collect(Collectors.toList()), specification.getAuthors().stream().map(spec -> spec.getName()).collect(Collectors.toList()));
		assertEquals(description, specification.getDescription());
		assertEquals(documentation, specification.getDocumentation());
		assertEquals(license, specification.getLicense());
		assertEquals(source, specification.getSource());
		assertEquals(gitRepo, specification.getGitRepo());
		assertEquals(attachments, specification.getAttachments());
		assertArrayEquals(tags.toArray(), specification.getTags().toArray());
		assertEquals(1, specification.getCitations().size());
		CitationSpecification citation = new DefaultCitationSpecification();
		citation.setCitationText(citationText);
		citation.setDOIText(doi);
		assertEquals(citation, specification.getCitations().get(0));
		assertNotNull(specification.getSampleInputs());
		assertEquals(1, specification.getSampleInputs().size());
		assertEquals(testInput, specification.getSampleInputs().get(0));
		assertNotNull(specification.getSampleOutputs());
		assertEquals(1, specification.getSampleOutputs().size());
		assertEquals(testOutput, specification.getSampleOutputs().get(0));
		assertEquals(timestamp, specification.getTimestamp());

		// input
		assertEquals(1, specification.getInputs().size());
		InputNodeSpecification _input = specification.getInputs().get(0);
		assertEquals(inputName, _input.getName());
		assertEquals(axes, _input.getAxes());
		assertEquals(dataType, _input.getDataType());
		assertArrayEquals(shapeMin.toArray(), _input.getShapeMin().toArray());
		assertArrayEquals(shapeStep.toArray(), _input.getShapeStep().toArray());
		assertArrayEquals(dataRange.toArray(), _input.getDataRange().toArray());
		assertNotNull(_input.getPreprocessing());
		assertEquals(2, _input.getPreprocessing().size());
		assertEquals(ClipTransformation.name, _input.getPreprocessing().get(0).getName());
		ClipTransformation clipIn = (ClipTransformation) _input.getPreprocessing().get(0);
		assertEquals(min, clipIn.getMin());
		assertEquals(max, clipIn.getMax());
		assertEquals(mode, clipIn.getMode());
		assertEquals(ZeroMeanUnitVarianceTransformation.name, _input.getPreprocessing().get(1).getName());
		ZeroMeanUnitVarianceTransformation zeroMeanIn = (ZeroMeanUnitVarianceTransformation) _input.getPreprocessing().get(1);
		assertEquals(mean, zeroMeanIn.getMean());
		assertEquals(std, zeroMeanIn.getStd());
		assertEquals(mode, zeroMeanIn.getMode());

		// output
		assertEquals(1, specification.getOutputs().size());
		OutputNodeSpecification _output = specification.getOutputs().get(0);
		assertEquals(output, _output.getName());
		assertEquals(axes, _output.getAxes());
		assertEquals(inputName, _output.getReferenceInputName());
		assertArrayEquals(shapeOffset.toArray(), _output.getShapeOffset().toArray());
		assertArrayEquals(shapeScale.toArray(), _output.getShapeScale().toArray());
		assertNotNull(_output.getPostprocessing());
		assertEquals(4, _output.getPostprocessing().size());
		assertEquals(BinarizeTransformation.name, _output.getPostprocessing().get(0).getName());
		BinarizeTransformation binarize = (BinarizeTransformation) _output.getPostprocessing().get(0);
		assertEquals(threshold, binarize.getThreshold());
		assertEquals(mode, binarize.getMode());
		assertEquals(ClipTransformation.name, _output.getPostprocessing().get(1).getName());
		ClipTransformation clip = (ClipTransformation) _output.getPostprocessing().get(1);
		assertEquals(min, clip.getMin());
		assertEquals(max, clip.getMax());
		assertEquals(mode, clip.getMode());
		assertEquals(ScaleMinMaxTransformation.name, _output.getPostprocessing().get(2).getName());
		ScaleMinMaxTransformation scaleMinMax = (ScaleMinMaxTransformation) _output.getPostprocessing().get(2);
		assertEquals(minPercentile, scaleMinMax.getMinPercentile());
		assertEquals(maxPercentile, scaleMinMax.getMaxPercentile());
		assertEquals(inputName, scaleMinMax.getReferenceInput());
		assertEquals(mode, scaleMinMax.getMode());
		assertEquals(ScaleLinearTransformation.name, _output.getPostprocessing().get(3).getName());
		ScaleLinearTransformation scaleLinear = (ScaleLinearTransformation) _output.getPostprocessing().get(3);
		assertEquals(std, scaleLinear.getGain());
		assertEquals(mean, scaleLinear.getOffset());
		assertEquals(mode, scaleLinear.getMode());

		assertNotNull(specification.getWeights());
		assertEquals(1, specification.getWeights().size());
		WeightsSpecification weights = specification.getWeights().get(TensorFlowSavedModelBundleSpecification.id);
		assertTrue(weights instanceof TensorFlowSavedModelBundleSpecification);
		assertEquals(weightsSha256, weights.getSha256());
		assertEquals(weightsSource, weights.getSource());
		assertEquals("serve", ((TensorFlowSavedModelBundleSpecification)weights).getTag());
	}

}
