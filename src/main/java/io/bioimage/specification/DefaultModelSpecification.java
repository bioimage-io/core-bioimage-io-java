/*-
 * #%L
 * This is the bioimage.io modelzoo library for ImageJ.
 * %%
 * Copyright (C) 2019 - 2020 Center for Systems Biology Dresden
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This is the ImageJ version of the model zoo configuration specification
 * https://github.com/bioimage-io/configuration
 */
public class DefaultModelSpecification implements ModelSpecification {

	final static String modelZooSpecificationVersion = "0.3.0";
	private String language = "java";
	private String framework;
	private String formatVersion = modelZooSpecificationVersion;
	private List<String> sampleInputs = new ArrayList<>();

	private List<String> sampleOutputs = new ArrayList<>();
	private List<String> testInputs = new ArrayList<>();
	private List<String> testOutputs = new ArrayList<>();
	private String name;
	private String description;
	private List<CitationSpecification> citations = new ArrayList<>();
	private List<String> authors;
	private String documentation;
	private List<String> tags;
	private String license;
	private String source;
	private List<InputNodeSpecification> inputNodes = new ArrayList<>();
	private List<OutputNodeSpecification> outputNodes = new ArrayList<>();
	private List<WeightsSpecification> weights = new ArrayList<>();
	private String gitRepo;
	private Map<String, String> attachments;
	private String timestamp;
	private Map<String, Object> trainingKwargs;
	private Map<String, Object> config;

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addCitation(CitationSpecification citation) {
		citations.add(citation);
	}

	public void setAuthors(List<String> modelAuthors) {
		this.authors = modelAuthors;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void addInputNode(InputNodeSpecification inputNode) {
		inputNodes.add(inputNode);
	}

	public void addOutputNode(OutputNodeSpecification outputNode) {
		outputNodes.add(outputNode);
	}

	public void setGitRepo(String repo) {
		this.gitRepo = repo;
	}

	@Override
	public List<InputNodeSpecification> getInputs() {
		return unmodifiable(inputNodes);
	}

	@Override
	public List<OutputNodeSpecification> getOutputs() {
		return unmodifiable(outputNodes);
	}

	@Override
	public String getFormatVersion() {
		return formatVersion;
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public String getFramework() {
		return framework;
	}

	@Deprecated
	@Override
	public Map<String, Object> getTrainingKwargs() {
		return trainingKwargs;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<CitationSpecification> getCitations() {
		return unmodifiable(citations);
	}

	@Override
	public List<WeightsSpecification> getWeights() {
		return unmodifiable(weights);
	}

	@Override
	public List<String> getAuthors() {
		return unmodifiable(authors);
	}

	@Override
	public String getDocumentation() {
		return documentation;
	}

	@Override
	public List<String> getTags() {
		return unmodifiable(tags);
	}

	@Override
	public String getLicense() {
		return license;
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public String getGitRepo() {
		return gitRepo;
	}

	@Override
	public Map<String, String> getAttachments() {
		return unmodifiable(attachments);
	}

	@Override
	public List<String> getTestInputs() {
		return unmodifiable(testInputs);
	}

	public void setTestInputs(List<String> testInputs) {
		this.testInputs = testInputs;
	}

	@Override
	public List<String> getTestOutputs() {
		return unmodifiable(testOutputs);
	}

	public void setTestOutputs(List<String> testOutputs) {
		this.testOutputs = testOutputs;
	}

	@Override
	public List<String> getSampleInputs() {
		return unmodifiable(sampleInputs);
	}

	@Override
	public void setSampleInputs(List<String> sampleInputs) {
		this.sampleInputs = sampleInputs;
	}

	@Override
	public List<String> getSampleOutputs() {
		return unmodifiable(sampleOutputs);
	}

	@Override
	public void setSampleOutputs(List<String> sampleOutputs) {
		this.sampleOutputs = sampleOutputs;
	}

	public void setFramework(String framework) {
		this.framework = framework;
	}

	public void setFormatVersion(String version) {
		formatVersion = version;
	}

	@Override
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public void updateToNewestVersion() {
		setFormatVersion(modelZooSpecificationVersion);
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void read(ModelSpecification spec) {
		setName(spec.getName());
		setDocumentation(spec.getDocumentation());
		setDescription(spec.getDescription());
		setAuthors(spec.getAuthors());
		setTestInputs(spec.getTestInputs());
		setTestOutputs(spec.getTestOutputs());
		setSampleInputs(spec.getSampleInputs());
		setSampleOutputs(spec.getSampleOutputs());
		setFramework(spec.getFramework());
		setLanguage(spec.getLanguage());
		setLicense(spec.getLicense());
		setSource(spec.getSource());
		setTags(spec.getTags());
		setTrainingKwargs(spec.getTrainingKwargs());
		setTrainingSource(spec.getTrainingSource());
		setGitRepo(spec.getGitRepo());
		setInputs(spec.getInputs());
		setOutputs(spec.getOutputs());
		setAttachments(spec.getAttachments());
		setWeights(spec.getWeights());
	}

	public void setWeights(List<WeightsSpecification> weights) {
		this.weights = weights;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}

	public void setOutputs(List<OutputNodeSpecification> outputs) {
		this.outputNodes = outputs;
	}

	public void setInputs(List<InputNodeSpecification> inputs) {
		this.inputNodes = inputs;
	}

	private static <T> List<T> unmodifiable(List<T> list) {
		return list != null? Collections.unmodifiableList(list) : null;
	}

	private static <T, S> Map<T, S> unmodifiable(Map<T, S> map) {
		return map != null? Collections.unmodifiableMap(map) : null;
	}

	@Deprecated
	@Override
	public String getTrainingSource() {
		return getSource();
	}

	@Override
	public Map<String, Object> getConfig() {
		return config;
	}

	@Deprecated
	public void setTrainingSource(String trainingSource) {
		setSource(trainingSource);
	}

	@Deprecated
	public void setTrainingKwargs(Map<String, Object> trainingKwargs) {
		this.trainingKwargs = trainingKwargs;
	}

	public void addWeights(WeightsSpecification weights) {
		this.weights.add(weights);
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
}
