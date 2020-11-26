package io.bioimage.specification.io;

import io.bioimage.specification.CitationSpecification;
import io.bioimage.specification.DefaultCitationSpecification;
import io.bioimage.specification.DefaultInputNodeSpecification;
import io.bioimage.specification.DefaultModelSpecification;
import io.bioimage.specification.DefaultOutputNodeSpecification;
import io.bioimage.specification.InputNodeSpecification;
import io.bioimage.specification.NodeSpecification;
import io.bioimage.specification.OutputNodeSpecification;
import io.bioimage.specification.WeightsSpecification;
import io.bioimage.specification.transformation.ScaleLinearTransformation;
import io.bioimage.specification.transformation.ZeroMeanUnitVarianceTransformation;
import io.bioimage.specification.weights.TensorFlowSavedModelBundleSpecification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.bioimage.specification.util.SpecificationUtil.asMap;

class SpecificationReaderV1 {

	private final static String idName = "name";
	private final static String idDescription = "description";
	private final static String idCite = "cite";
	private final static String idAuthors = "authors";
	private final static String idDocumentation = "documentation";
	private final static String idTags = "tags";
	private final static String idLicense = "license";
	private final static String idFormatVersion = "format_version";

	private final static String idLanguage = "language";

	private final static String idFramework = "framework";
	private final static String idSource = "source";
	private final static String idTestInput = "test_input";
	private final static String idTestOutput = "test_output";
	private final static String idInputs = "inputs";
	private final static String idOutputs = "outputs";
	private final static String idPrediction = "prediction";
	private final static String idTraining = "training";
	private final static String idTrainingSource = "source";
	private final static String idTrainingKwargs = "kwargs";

	private final static String idNodeName = "name";
	private final static String idNodeAxes = "axes";
	private final static String idNodeDataType = "data_type";
	private final static String idNodeDataRange = "data_range";
	private final static String idNodeShape = "shape";
	private final static String idNodeHalo = "halo";

	private final static String idNodeShapeMin = "min";
	private final static String idNodeShapeStep = "step";
	private final static String idNodeShapeReferenceInput = "reference_input";
	private final static String idNodeShapeScale = "scale";
	private final static String idNodeShapeOffset = "offset";

	private final static String idCiteText = "text";
	private final static String idCiteDoi = "doi";

	private static final String idPredictionPreprocess = "preprocess";
	private final static String idTransformationKwargs = "kwargs";
	private static final String idTransformationMean = "mean";
	private static final String idTransformationStd = "stdDev";

	static DefaultModelSpecification read(DefaultModelSpecification specification, Map<String, Object> obj) {
		readMeta(specification, obj);
		readInputsOutputs(specification, obj);
		readTraining(specification, obj);
		readPrediction(specification, obj);
		WeightsSpecification weights  = new TensorFlowSavedModelBundleSpecification();
		specification.addWeights(weights);
		return specification;
	}

	private static void readMeta(DefaultModelSpecification specification, Map<String, Object> obj) {
		specification.setName((String) obj.get(idName));
		specification.setDescription((String) obj.get(idDescription));
		if(obj.get(idCite) != null && List.class.isAssignableFrom(obj.get(idCite).getClass())) {
			List<Map> citations = (List<Map>) obj.get(idCite);
			for (Map citation : citations) {
				specification.addCitation(readCitation(citation));
			}
		}
		Object authors = obj.get(idAuthors);
		if (authors != null) {
			if (List.class.isAssignableFrom(authors.getClass())) {
				specification.setAuthors(((List<String>) authors));
			} else if (String.class.isAssignableFrom(authors.getClass())) {
				specification.setAuthors(Arrays.asList((String) authors));
			}
		}
		specification.setDocumentation((String) obj.get(idDocumentation));
		specification.setTags((List<String>) obj.get(idTags));
		specification.setLicense((String) obj.get(idLicense));
		specification.setFormatVersion((String) obj.get(idFormatVersion));
		specification.setLanguage((String) obj.get(idLanguage));
		specification.setFramework((String) obj.get(idFramework));
		specification.setSource((String) obj.get(idSource));
		specification.setTestInputs(Collections.singletonList((String) obj.get(idTestInput)));
		specification.setTestOutputs(Collections.singletonList((String) obj.get(idTestOutput)));
	}

	private static void readInputsOutputs(DefaultModelSpecification specification, Map<String, Object> obj) {
		List<Map> inputs = (List<Map>) obj.get(idInputs);
		for (Map input : inputs) {
			specification.addInputNode(readInputNode(input));
		}
		List<Map> outputs = (List<Map>) obj.get(idOutputs);
		for (Map output : outputs) {
			specification.addOutputNode(readOutputNode(output));
		}
	}

	private static void readTraining(DefaultModelSpecification specification, Map<String, Object> obj) {
		Map<String, Object> training = asMap(obj.get(idTraining));
		if (training != null) {
			specification.setTrainingSource((String) training.get(idTrainingSource));
			specification.setTrainingKwargs(asMap(training.get(idTrainingKwargs)));
		}
	}

	private static void readPrediction(DefaultModelSpecification specification, Map<String, Object> obj) {
		Map<String, Object> prediction = asMap(obj.get(idPrediction));
		if (prediction != null) {
			Map<String, Object> preprocess = asMap(prediction.get(idPredictionPreprocess));
			if(preprocess != null) {
				Map<String, Object> kwargs = asMap(preprocess.get(idTransformationKwargs));
				if(kwargs != null) {
					Number std = (Number) kwargs.get(idTransformationStd);
					Number mean = (Number) kwargs.get(idTransformationMean);
					if(mean != null && std != null) {
						ZeroMeanUnitVarianceTransformation pre = new ZeroMeanUnitVarianceTransformation();
						pre.setStd(std);
						pre.setMean(mean);
						ScaleLinearTransformation post = new ScaleLinearTransformation();
						post.setGain(std);
						post.setOffset(mean);
						specification.getInputs().get(0).setPreprocessing(Collections.singletonList(pre));
						specification.getOutputs().get(0).setPostprocessing(Collections.singletonList(post));
					}
				}
			}
		}
	}

	private static InputNodeSpecification readInputNode(Map data) {
		InputNodeSpecification node = new DefaultInputNodeSpecification();
		readNode(node, data);
		Map<String, Object> shapeData = asMap(data.get(idNodeShape));
		node.setShapeMin((List<Integer>) shapeData.get(idNodeShapeMin));
		node.setShapeStep((List<Integer>) shapeData.get(idNodeShapeStep));
		return node;
	}

	private static OutputNodeSpecification readOutputNode(Map data) {
		OutputNodeSpecification node = new DefaultOutputNodeSpecification();
		readNode(node, data);
		Map<String, Object> shapeData = asMap(data.get(idNodeShape));
		node.setShapeReferenceInput((String) shapeData.get(idNodeShapeReferenceInput));
		node.setShapeScale((List<Number>) shapeData.get(idNodeShapeScale));
		node.setShapeOffset((List<Integer>) shapeData.get(idNodeShapeOffset));
		return node;
	}

	private static void readNode(NodeSpecification node, Map data) {
		node.setName((String) data.get(idNodeName));
		node.setAxes((String) data.get(idNodeAxes));
		node.setDataType((String) data.get(idNodeDataType));
		node.setDataRange((List<?>) data.get(idNodeDataRange));
		node.setHalo((List<Integer>) data.get(idNodeHalo));
	}

	private static CitationSpecification readCitation(Map data) {
		CitationSpecification citation = new DefaultCitationSpecification();
		citation.setCitationText((String) data.get(idCiteText));
		citation.setDOIText((String) data.get(idCiteDoi));
		return citation;
	}

	public static boolean canRead(Map<String, Object> obj) {
		String version = (String) obj.get(idFormatVersion);
		return Objects.equals(version, "0.1.0");
	}
}
