/*-
 * #%L
 * Java implementation of the bioimage.io model specification.
 * %%
 * Copyright (C) 2020 Center for Systems Biology Dresden
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
package io.bioimage.specification.io;

import io.bioimage.specification.*;
import io.bioimage.specification.transformation.*;
import io.bioimage.specification.weights.TensorFlowSavedModelBundleSpecification;

import java.io.IOException;
import java.util.*;

import static io.bioimage.specification.util.SpecificationUtil.asMap;

class SpecificationReaderWriterV3 {

    private final static String idName = "name";
    private final static String idDescription = "description";
    private final static String idCite = "cite";
    private final static String idAuthors = "authors";
    private final static String idDocumentation = "documentation";
    private final static String idTags = "tags";
    private final static String idLicense = "license";
    private final static String idFormatVersion = "format_version";
    private final static String idLanguage = "language";
    private final static String idTimestamp = "timestamp";
    private final static String idFramework = "framework";
    private final static String idSource = "source";
    private final static String idHash = "sha256";
    private final static String idGitRepo = "git_repo";
    private final static String idAttachments = "attachments";
    private final static String idTestInputs = "test_inputs";
    private final static String idTestOutputs = "test_outputs";
    private final static String idSampleInputs = "sample_inputs";
    private final static String idSampleOutputs = "sample_outputs";
    private final static String idCovers = "covers";
    private final static String idInputs = "inputs";
    private final static String idOutputs = "outputs";
    private final static String idWeights = "weights";
    private final static String idWeightsSource = "source";
    private final static String idWeightsHash = "sha256";
    private final static String idWeightsTag = "tag";
    private final static String idPackagedBy = "packaged_by";
    private final static String idDependencies = "dependencies";
    private final static String idType = "type";
    private final static String idVersion = "version";
    private final static String idParent = "parent";
    private final static String idParentUri = "uri";
    private final static String idParentHash = "sha256";

    private final static String idNodeName = "name";
    private final static String idNodeAxes = "axes";
    private final static String idNodeDescription = "description";
    private final static String idNodeDataType = "data_type";
    private final static String idNodeDataRange = "data_range";
    private final static String idNodeShape = "shape";
    private final static String idNodeHalo = "halo";

    private final static String idNodeShapeMin = "min";
    private final static String idNodeShapeStep = "step";
    private final static String idNodePreprocessing = "preprocessing";
    private final static String idNodeShapeReferenceInput = "reference_input";
    private final static String idNodeShapeScale = "scale";
    private final static String idNodeShapeOffset = "offset";
    private final static String idNodePostprocessing = "postprocessing";

    private final static String idCiteText = "text";
    private final static String idCiteDoi = "doi";
    private final static String idCiteUrl = "url";

    private final static String idAuthorName = "name";
    private final static String idAuthorAffiliation = "affiliation";
    private final static String idAuthorOrcid = "orcid";

    private final static String idTransformationName = "name";
    private final static String idTransformationKwargs = "kwargs";
    private final static String idTransformationMode = "mode";
    private final static String idTransformationModeFixed = "fixed";
    private final static String idTransformationModePerDataset = "per_dataset";
    private final static String idTransformationModePerSample = "per_sample";

    private final static String idTransformationScaleLinear = "scale_linear";
    private final static String idTransformationScaleLinearGain = "gain";
    private final static String idTransformationScaleLinearOffset = "offset";

    private final static String idTransformationZeroMean = "zero_mean_unit_variance";
    private final static String idTransformationZeroMeanMean = "mean";
    private final static String idTransformationZeroMeanStd = "std";
    private final static String idWeightsTensorFlowSavedModelBundle = "tensorflow_saved_model_bundle";

    private final static String idTransformationScaleMinMax = "scale_min_max";
    private final static String idTransformationScaleMinMaxReferenceInput = "reference_input";
    private final static String idTransformationScaleMinMaxMinPercentile = "min_percentile";
    private final static String idTransformationScaleMinMaxMaxPercentile = "max_percentile";

    private final static String idTransformationPercentile = "percentile";
    private final static String idTransformationPercentileMinPercentile = "min_percentile";
    private final static String idTransformationPercentileMaxPercentile = "max_percentile";

    private final static String idTransformationBinarize = "binarize";
    private final static String idTransformationBinarizeThreshold = "threshold";

    private final static String idTransformationClip = "clip";
    private final static String idTransformationClipMin = "min";
    private final static String idTransformationClipMax = "max";

    private final static String idConfig = "config";

    private final static String idExecutionModel = "execution_model";

    static DefaultModelSpecification read(DefaultModelSpecification specification, Map<String, Object> obj) throws IOException {
        readMeta(specification, obj);
        readInputsOutputs(specification, obj);
        readWeights(specification, obj);
        readConfig(specification, obj);
        return specification;
    }

    private static void readMeta(DefaultModelSpecification specification, Map<String, Object> obj) {
        specification.setName((String) obj.get(idName));
        specification.setDescription((String) obj.get(idDescription));
        specification.setVersion((String) obj.get(idVersion));
        specification.setType((String) obj.get(idType));
        if (obj.get(idTimestamp) == null) {
            specification.setTimestamp((String) obj.get(idTimestamp));
        } else {
            specification.setTimestamp(obj.get(idTimestamp).toString());
        }

        if (obj.get(idCite) != null && List.class.isAssignableFrom(obj.get(idCite).getClass())) {
            List<Map> citations = (List<Map>) obj.get(idCite);
            for (Map citation : citations) {
                specification.addCitation(readCitation(citation));
            }
        }
        List<Object> authors = (List<Object>) obj.get(idAuthors);
        if (authors != null && List.class.isAssignableFrom(authors.getClass())) {
            if(!authors.isEmpty() && Map.class.isAssignableFrom(authors.get(0).getClass())){
                for(Object author : authors){
                    specification.addAuthor(readAuthor((Map) author));
                }
            }else if(!authors.isEmpty() && String.class.isAssignableFrom(authors.get(0).getClass())){
                for(Object author : authors){
                    AuthorSpecification authSpec = new DefaultAuthorSpecification();
                    authSpec.setName((String) author);
                    specification.addAuthor(authSpec);
                }
            }
        }
        Object attachments = obj.get(idAttachments);
        if (attachments != null) {
            if (Map.class.isAssignableFrom(attachments.getClass())) {
                specification.setAttachments((Map<String, String>) attachments);
            }
        }
        specification.setDocumentation((String) obj.get(idDocumentation));
        specification.setTags((List<String>) obj.get(idTags));
        specification.setLicense((String) obj.get(idLicense));
        specification.setFormatVersion((String) obj.get(idFormatVersion));
        specification.setLanguage((String) obj.get(idLanguage));
        specification.setFramework((String) obj.get(idFramework));
        specification.setExecutionModel((String) obj.get(idExecutionModel));
        specification.setSource((String) parseSource(obj));
        specification.setHash((String) obj.get(idHash));
        specification.setGitRepo((String) obj.get(idGitRepo));
        specification.setTestInputs((List<String>) obj.get(idTestInputs));
        specification.setTestOutputs((List<String>) obj.get(idTestOutputs));
        specification.setSampleInputs((List<String>) obj.get(idSampleInputs));
        specification.setSampleOutputs((List<String>) obj.get(idSampleOutputs));
        specification.setCovers((List<String>) obj.get(idCovers));
        specification.setPackaged_by((String) obj.get(idPackagedBy));
        specification.setDependencies((String) obj.get(idDependencies));
        specification.setParent(parseParent(obj));
    }

    private static AuthorSpecification readAuthor(Map data) {
        AuthorSpecification author = new DefaultAuthorSpecification();
        author.setName((String) data.get(idAuthorName));
        author.setAffiliation((String) data.get(idAuthorAffiliation));
        author.setOrcId((String) data.get(idAuthorOrcid));
        return author;
    }

    private static ParentSpecification parseParent(Map<String, Object> obj) {
        Object parent = obj.get(idParent);
        if (parent == null) {
            return null;
        }
        ParentSpecification parentSpecification = new DefaultParentSpecification();
        parentSpecification.setUri(((Map<String, String>) parent).get(idParentUri));
        parentSpecification.setHash(((Map<String, String>) parent).get(idParentHash));
        return parentSpecification;
    }

    private static void readConfig(DefaultModelSpecification specification, Map<String, Object> obj) {
        specification.setConfig(asMap(obj.get(idConfig)));
    }

    private static Object parseSource(Map<String, Object> obj) {
        Object source = obj.get(idSource);
        if (source != null && source.equals("n2v")) {
            return null;
        }
        return source;
    }

    private static void readInputsOutputs(DefaultModelSpecification specification, Map<String, Object> obj) throws IOException {
        List<Map> inputs = (List<Map>) obj.get(idInputs);
        for (Map input : inputs) {
            specification.addInputNode(readInputNode(input));
        }
        List<Map> outputs = (List<Map>) obj.get(idOutputs);
        for (Map output : outputs) {
            specification.addOutputNode(readOutputNode(output));
        }
    }

    private static void readWeights(DefaultModelSpecification specification, Map<String, Object> obj) {
        Map<String, Object> weights = asMap(obj.get(idWeights));
        if (weights == null) return;
        weights.forEach((name, object) -> readWeightsEntry(specification, name, asMap(object)));
    }

    private static void readWeightsEntry(DefaultModelSpecification specification, String name, Map<String, Object> data) {
        if (name.equals(idWeightsTensorFlowSavedModelBundle)) {
            TensorFlowSavedModelBundleSpecification weightsSpec = new TensorFlowSavedModelBundleSpecification();
            if (data != null) {
                weightsSpec.setTag((String) data.get(idWeightsTag));
                weightsSpec.setSha256((String) data.get(idWeightsHash));
                weightsSpec.setSource((String) data.get(idWeightsSource));
            }
            specification.addWeights(weightsSpec);
        }
    }


    private static InputNodeSpecification readInputNode(Map data) throws IOException {
        InputNodeSpecification node = new DefaultInputNodeSpecification();
        readNode(node, data);
        Map<String, Object> shapeData = asMap(data.get(idNodeShape));
        node.setShapeMin((List<Integer>) shapeData.get(idNodeShapeMin));
        node.setShapeStep((List<Integer>) shapeData.get(idNodeShapeStep));
        Object preprocessings = data.get(idNodePreprocessing);
        ArrayList<TransformationSpecification> preprocessing = null;
        if (preprocessings != null && List.class.isAssignableFrom(preprocessings.getClass())) {
            preprocessing = new ArrayList<>();
            for (Map processing : ((List<Map>) preprocessings)) {
                preprocessing.add(readTransformation(processing));
            }
        }
        node.setPreprocessing(preprocessing);
        return node;
    }

    private static TransformationSpecification readTransformation(Map data) throws IOException {
        Map<String, Object> kwargs = asMap(data.get(idTransformationKwargs));
        Object transformation = data.get(idTransformationName);
        if (transformation == null) throw new IOException("Can't find name of transformation " + data);
        switch ((String) transformation) {
            case idTransformationBinarize:
                BinarizeTransformation binarize = new BinarizeTransformation();
                binarize.setMode(toMode(kwargs.get(idTransformationMode)));
                binarize.setThreshold(toNumber(kwargs.get(idTransformationBinarizeThreshold)));
                return binarize;
            case idTransformationScaleLinear:
                ScaleLinearTransformation scaleLinear = new ScaleLinearTransformation();
                scaleLinear.setMode(toMode(kwargs.get(idTransformationMode)));
                scaleLinear.setGain(toNumber(kwargs.get(idTransformationScaleLinearGain)));
                scaleLinear.setOffset(toNumber(kwargs.get(idTransformationScaleLinearOffset)));
                return scaleLinear;
            case idTransformationZeroMean:
                ZeroMeanUnitVarianceTransformation zeroMean = new ZeroMeanUnitVarianceTransformation();
                zeroMean.setMode(toMode(kwargs.get(idTransformationMode)));
                zeroMean.setMean(toNumber(kwargs.get(idTransformationZeroMeanMean)));
                zeroMean.setStd(toNumber(kwargs.get(idTransformationZeroMeanStd)));
                return zeroMean;
            case idTransformationScaleMinMax:
                ScaleMinMaxTransformation scaleMinMax = new ScaleMinMaxTransformation();
                scaleMinMax.setMode(toMode(kwargs.get(idTransformationMode)));
                scaleMinMax.setReferenceInput((String) kwargs.get(idTransformationScaleMinMaxReferenceInput));
                scaleMinMax.setMinPercentile(toNumber(kwargs.get(idTransformationScaleMinMaxMinPercentile)));
                scaleMinMax.setMaxPercentile(toNumber(kwargs.get(idTransformationScaleMinMaxMaxPercentile)));
                return scaleMinMax;
            case idTransformationPercentile:
                PercentileTransformation percentile = new PercentileTransformation();
                percentile.setMode(toMode(kwargs.get(idTransformationMode)));
                percentile.setMinPercentile(toNumber(kwargs.get(idTransformationPercentileMinPercentile)));
                percentile.setMaxPercentile(toNumber(kwargs.get(idTransformationPercentileMaxPercentile)));
                return percentile;
            case idTransformationClip:
                ClipTransformation clip = new ClipTransformation();
                clip.setMode(toMode(kwargs.get(idTransformationMode)));
                clip.setMin(toNumber(kwargs.get(idTransformationClipMin)));
                clip.setMax(toNumber(kwargs.get(idTransformationClipMax)));
                return clip;
        }
        throw new IOException("Could not process transformation " + transformation);
    }

    private static ImageTransformation.Mode toMode(Object obj) {
        if (obj == null) return null;
        String mode = (String) obj;
        if (mode.equals(idTransformationModeFixed)) {
            return ImageTransformation.Mode.FIXED;
        }
        if (mode.equals(idTransformationModePerDataset)) {
            return ImageTransformation.Mode.PER_DATASET;
        }
        if (mode.equals(idTransformationModePerSample)) {
            return ImageTransformation.Mode.PER_SAMPLE;
        }
        return null;
    }

    private static Number toNumber(Object obj) {
        if (obj == null) return null;
        if (Number.class.isAssignableFrom(obj.getClass())) {
            return (Number) obj;
        }
        if (List.class.isAssignableFrom(obj.getClass())) {
            return toNumber(((List) obj).get(0));
        }
        throw new ClassCastException("Cannot convert " + obj + " to number.");
    }

    private static OutputNodeSpecification readOutputNode(Map data) throws IOException {
        OutputNodeSpecification node = new DefaultOutputNodeSpecification();
        readNode(node, data);
        Map<String, Object> shapeData = asMap(data.get(idNodeShape));
        node.setShapeReferenceInput((String) shapeData.get(idNodeShapeReferenceInput));
        node.setShapeScale((List<Number>) shapeData.get(idNodeShapeScale));
        node.setShapeOffset((List<Integer>) shapeData.get(idNodeShapeOffset));
        node.setHalo((List<Integer>) data.get(idNodeHalo));
        Object postprocessings = data.get(idNodePostprocessing);
        ArrayList<TransformationSpecification> postprocessing = null;
        if (postprocessings != null && List.class.isAssignableFrom(postprocessings.getClass())) {
            postprocessing = new ArrayList<>();
            for (Map processing : ((List<Map>) postprocessings)) {
                postprocessing.add(readTransformation(processing));
            }
        }
        node.setPostprocessing(postprocessing);
        return node;
    }

    private static void readNode(NodeSpecification node, Map data) {
        node.setName((String) data.get(idNodeName));
        node.setAxes((String) data.get(idNodeAxes));
        node.setDataType((String) data.get(idNodeDataType));
        node.setDataRange((List<?>) data.get(idNodeDataRange));
        node.setDescription((String) data.get(idNodeDescription));
    }

    private static CitationSpecification readCitation(Map data) {
        CitationSpecification citation = new DefaultCitationSpecification();
        citation.setCitationText((String) data.get(idCiteText));
        citation.setDOIText((String) data.get(idCiteDoi));
        citation.setUrl((String) data.get(idCiteUrl));
        return citation;
    }

    static Map<String, Object> write(ModelSpecification specification) {
        Map<String, Object> data = new LinkedHashMap<>();
        writeMeta(specification, data);
        writeInputsOutputs(specification, data);
        writeWeights(specification, data);
        writeConfig(specification, data);
        return data;
    }


    private static void writeWeights(ModelSpecification specification, Map<String, Object> data) {
        Map<String, Object> weights = new LinkedHashMap<>();
        if (specification.getWeights() != null) {
            for (WeightsSpecification weight : specification.getWeights()) {
                Map<String, Object> weightData = new HashMap<>();
                weightData.put(idWeightsSource, weight.getSource());
                weightData.put(idWeightsHash, weight.getSha256());
                if (weight instanceof TensorFlowSavedModelBundleSpecification) {
                    weightData.put(idWeightsTag, ((TensorFlowSavedModelBundleSpecification) weight).getTag());
                }
                weights.put(getWeightsName(weight), weightData);
            }
        }
        data.put(idWeights, weights);
    }

    private static void writeConfig(ModelSpecification specification, Map<String, Object> data) {
        Map<String, Object> config = specification.getConfig();
        if (config != null) data.put(idConfig, config);
    }

    private static String getWeightsName(WeightsSpecification weight) {
        if (weight instanceof TensorFlowSavedModelBundleSpecification) return idWeightsTensorFlowSavedModelBundle;
        return null;
    }

    private static List<Map<String, Object>> buildTransformationList(List<TransformationSpecification> transformations) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (TransformationSpecification transformation : transformations) {
            res.add(writeTransformation(transformation));
        }
        return res;
    }

    private static void writeInputsOutputs(ModelSpecification specification, Map<String, Object> data) {
        data.put(idInputs, buildInputList(specification));
        data.put(idOutputs, buildOutputList(specification));
    }

    private static void writeMeta(ModelSpecification specification, Map<String, Object> data) {
        data.put(idFormatVersion, specification.getFormatVersion());
        data.put(idName, specification.getName());
        data.put(idTimestamp, specification.getTimestamp());
        data.put(idDescription, specification.getDescription());
        data.put(idAuthors, buildAuthorList(specification));
        data.put(idCite, buildCitationList(specification));
        data.put(idDocumentation, specification.getDocumentation());
        data.put(idTags, specification.getTags());
        data.put(idLicense, specification.getLicense());
        data.put(idLanguage, specification.getLanguage());
        data.put(idFramework, specification.getFramework());
        data.put(idSource, specification.getSource());
        data.put(idExecutionModel, specification.getExecutionModel());
        data.put(idGitRepo, specification.getGitRepo());
        data.put(idAttachments, specification.getAttachments());
        data.put(idTestInputs, specification.getTestInputs());
        data.put(idTestOutputs, specification.getTestOutputs());
        data.put(idSampleInputs, specification.getSampleInputs());
        data.put(idSampleOutputs, specification.getSampleOutputs());
        data.put(idDependencies, specification.getDependencies());
        data.put(idCovers, specification.getCovers());
        data.put(idHash, specification.getHash());
        data.put(idParent, buildParent(specification));
        data.put(idVersion, specification.getVersion());
        data.put(idType, specification.getType());
    }

    private static List<Map<String, Object>> buildAuthorList(ModelSpecification specification) {
        List<Map<String, Object>> authors = new ArrayList<>();
        if (specification.getAuthors() == null) {
            return null;
        }
        for(AuthorSpecification authorSpec : specification.getAuthors()){
            Map<String, Object> author = new HashMap<>();
            author.put(idAuthorName, authorSpec.getName());
            author.put(idAuthorOrcid, authorSpec.getOrcId());
            author.put(idAuthorAffiliation, authorSpec.getAffiliation());
            authors.add(author);
        }
        return authors;
    }


    private static Map<String, Object> buildParent(ModelSpecification specification) {
        Map<String, Object> parent = new HashMap<>();
        if (specification.getParent() == null) {
            return null;
        }
        parent.put(idParentUri, specification.getParent().getUri());
        parent.put(idParentHash, specification.getParent().getHash());
        return parent;
    }

    private static List<Map<String, Object>> buildInputList(ModelSpecification specification) {
        List<Map<String, Object>> inputs = new ArrayList<>();
        if (specification.getInputs() != null) {
            for (InputNodeSpecification input : specification.getInputs()) {
                inputs.add(writeInputNode(input));
            }
        }
        return inputs;
    }

    private static List<Map<String, Object>> buildOutputList(ModelSpecification specification) {
        List<Map<String, Object>> outputs = new ArrayList<>();
        if (specification.getOutputs() != null) {
            for (OutputNodeSpecification output : specification.getOutputs()) {
                outputs.add(writeOutputNode(output));
            }
        }
        return outputs;
    }

    private static List<Map<String, Object>> buildCitationList(ModelSpecification specification) {
        List<Map<String, Object>> cite = new ArrayList<>();
        if (specification.getCitations() != null) {
            for (CitationSpecification citation : specification.getCitations()) {
                cite.add(writeCitation(citation));
            }
        }
        return cite;
    }

    private static Map<String, Object> writeNode(NodeSpecification node) {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put(idNodeName, node.getName());
        if (node.getAxes() != null) res.put(idNodeAxes, node.getAxes());
        if (node.getDataType() != null) res.put(idNodeDataType, node.getDataType());
        if (node.getDataRange() != null) res.put(idNodeDataRange, node.getDataRange());
        if (node.getHalo() != null) res.put(idNodeHalo, node.getHalo());
        return res;
    }

    private static Map<String, Object> writeInputNode(InputNodeSpecification node) {
        Map<String, Object> res = writeNode(node);
        Map<String, Object> shape = new HashMap<>();
        if (node.getShapeMin() != null) shape.put(idNodeShapeMin, node.getShapeMin());
        if (node.getShapeStep() != null) shape.put(idNodeShapeStep, node.getShapeStep());
        res.put(idNodeShape, shape);
        if (node.getPreprocessing() != null) {
            res.put(idNodePreprocessing, buildTransformationList(node.getPreprocessing()));
        }
        return res;
    }

    private static Map<String, Object> writeOutputNode(OutputNodeSpecification node) {
        Map<String, Object> res = writeNode(node);
        Map<String, Object> shape = new HashMap<>();
        shape.put(idNodeShapeReferenceInput, node.getReferenceInputName());
        shape.put(idNodeShapeScale, node.getShapeScale());
        shape.put(idNodeShapeOffset, node.getShapeOffset());
        res.put(idNodeShape, shape);
        if (node.getPostprocessing() != null) {
            res.put(idNodePostprocessing, buildTransformationList(node.getPostprocessing()));
        }
        return res;
    }

    private static Map<String, Object> writeTransformation(TransformationSpecification transformation) {
        Map<String, Object> res = new LinkedHashMap<>();
        Map<String, Object> kwargs = new LinkedHashMap<>();
        if (transformation instanceof ScaleLinearTransformation) {
            res.put(idTransformationName, idTransformationScaleLinear);
            ScaleLinearTransformation scaleLinear = (ScaleLinearTransformation) transformation;
            kwargs.put(idTransformationMode, writeMode(scaleLinear.getMode()));
            kwargs.put(idTransformationScaleLinearGain, Collections.singletonList(scaleLinear.getGain()));
            kwargs.put(idTransformationScaleLinearOffset, Collections.singletonList(scaleLinear.getOffset()));
        } else if (transformation instanceof ZeroMeanUnitVarianceTransformation) {
            res.put(idTransformationName, idTransformationZeroMean);
            ZeroMeanUnitVarianceTransformation zeroMean = (ZeroMeanUnitVarianceTransformation) transformation;
            kwargs.put(idTransformationMode, writeMode(zeroMean.getMode()));
            kwargs.put(idTransformationZeroMeanMean, Collections.singletonList(zeroMean.getMean()));
            kwargs.put(idTransformationZeroMeanStd, Collections.singletonList(zeroMean.getStd()));
        } else if (transformation instanceof BinarizeTransformation) {
            res.put(idTransformationName, idTransformationBinarize);
            BinarizeTransformation binarize = (BinarizeTransformation) transformation;
            kwargs.put(idTransformationMode, writeMode(binarize.getMode()));
            kwargs.put(idTransformationBinarizeThreshold, Collections.singletonList(binarize.getThreshold()));
        } else if (transformation instanceof ScaleMinMaxTransformation) {
            res.put(idTransformationName, idTransformationScaleMinMax);
            ScaleMinMaxTransformation scaleMinMax = (ScaleMinMaxTransformation) transformation;
            kwargs.put(idTransformationMode, writeMode(scaleMinMax.getMode()));
            kwargs.put(idTransformationScaleMinMaxMinPercentile, scaleMinMax.getMinPercentile());
            kwargs.put(idTransformationScaleMinMaxMaxPercentile, scaleMinMax.getMaxPercentile());
            kwargs.put(idTransformationScaleMinMaxReferenceInput, scaleMinMax.getReferenceInput());
        } else if (transformation instanceof PercentileTransformation) {
            res.put(idTransformationName, idTransformationPercentile);
            PercentileTransformation percentile = (PercentileTransformation) transformation;
            kwargs.put(idTransformationMode, writeMode(percentile.getMode()));
            kwargs.put(idTransformationPercentileMinPercentile, percentile.getMinPercentile());
            kwargs.put(idTransformationPercentileMaxPercentile, percentile.getMaxPercentile());
        } else if (transformation instanceof ClipTransformation) {
            res.put(idTransformationName, idTransformationClip);
            ClipTransformation clip = (ClipTransformation) transformation;
            kwargs.put(idTransformationMode, writeMode(clip.getMode()));
            kwargs.put(idTransformationClipMin, clip.getMin());
            kwargs.put(idTransformationClipMax, clip.getMax());
        }
        res.put(idTransformationKwargs, kwargs);
        return res;
    }

    private static String writeMode(ImageTransformation.Mode mode) {
        if (mode == null) return null;
        if (mode.equals(ImageTransformation.Mode.FIXED)) return idTransformationModeFixed;
        if (mode.equals(ImageTransformation.Mode.PER_DATASET)) return idTransformationModePerDataset;
        if (mode.equals(ImageTransformation.Mode.PER_SAMPLE)) return idTransformationModePerSample;
        return null;
    }

    private static Map<String, Object> writeCitation(CitationSpecification citation) {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put(idCiteText, citation.getCitationText());
        res.put(idCiteDoi, citation.getDoiText());
        res.put(idCiteUrl, citation.getUrl());
        return res;
    }

    public static boolean canRead(Map<String, Object> obj) {
        String version = (String) obj.get(idFormatVersion);
        return version.startsWith("0.3.");
    }

    static boolean canWrite(ModelSpecification specification) {
        String version = specification.getFormatVersion();
        return version.startsWith("0.3.");
    }
}
