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

import java.util.*;

/**
 * This is the ImageJ version of the model zoo configuration specification
 * https://github.com/bioimage-io/configuration
 */
public class DefaultModelSpecification implements ModelSpecification {

    final static String modelZooSpecificationVersion = "0.4.2";
    private String formatVersion = modelZooSpecificationVersion;
    private List<String> sampleInputs = new ArrayList<>();

    private List<String> sampleOutputs = new ArrayList<>();
    private List<String> testInputs = new ArrayList<>();
    private List<String> testOutputs = new ArrayList<>();
    private String name;
    private String description;
    private List<CitationSpecification> citations = new ArrayList<>();
    private List<BadgeSpecification> badges = new ArrayList<>();
    private List<AuthorSpecification> authors = new ArrayList<>();
    private String documentation;
    private List<String> tags;
    private String license;
    private String source;
    private List<InputNodeSpecification> inputNodes = new ArrayList<>();
    private List<OutputNodeSpecification> outputNodes = new ArrayList<>();
    private Map<String, WeightsSpecification> weights = new HashMap<>();
    private String gitRepo;
    private Map<String, String> attachments;
    private String timestamp;
    private Map<String, Object> trainingKwargs;
    private Map<String, Object> config;
    private String hash;
    private ParentSpecification parent = new DefaultParentSpecification();
    private List<String> covers = new ArrayList<>();
    private List<AuthorSpecification> packaged_by = new ArrayList<>();
    private String version;
    private String type;
    private String downloadUrl;
    private String icon;
    private List<String> links = new ArrayList<>();
    private List<AuthorSpecification> maintainers = new ArrayList<>();
    private String runMode;

    public static String getModelZooSpecificationVersion() {
        return modelZooSpecificationVersion;
    }

    private static <T> List<T> unmodifiable(List<T> list) {
        return list != null ? Collections.unmodifiableList(list) : null;
    }

    private static <T, S> Map<T, S> unmodifiable(Map<T, S> map) {
        return map != null ? Collections.unmodifiableMap(map) : null;
    }

    public List<InputNodeSpecification> getInputNodes() {
        return inputNodes;
    }

    public void setInputNodes(List<InputNodeSpecification> inputNodes) {
        this.inputNodes = inputNodes;
    }

    public List<OutputNodeSpecification> getOutputNodes() {
        return outputNodes;
    }

    public void setOutputNodes(List<OutputNodeSpecification> outputNodes) {
        this.outputNodes = outputNodes;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ParentSpecification getParent() {
        return parent;
    }

    @Override
    public List<BadgeSpecification> getBadges() {
        return badges;
    }

    @Override
    public void setBadges(List<BadgeSpecification> badges) {
        this.badges = badges;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setParent(ParentSpecification parent) {
        this.parent = parent;
    }

    public List<String> getCovers() {
        return covers;
    }

    public void setCovers(List<String> covers) {
        this.covers = covers;
    }

    public List<AuthorSpecification> getPackagedBy() {
        return packaged_by;
    }

    public void setPackaged_by(List<AuthorSpecification> packaged_by) {
        this.packaged_by = packaged_by;
    }

    public void addCitation(CitationSpecification citation) {
        citations.add(citation);
    }

    public void addAuthor(AuthorSpecification author) {
        authors.add(author);
    }

    public void addMaintainer(AuthorSpecification maintainer) {
        maintainers.add(maintainer);
    }

    public void addInputNode(InputNodeSpecification inputNode) {
        inputNodes.add(inputNode);
    }

    public void addOutputNode(OutputNodeSpecification outputNode) {
        outputNodes.add(outputNode);
    }

    @Override
    public List<InputNodeSpecification> getInputs() {
        return unmodifiable(inputNodes);
    }

    public void setInputs(List<InputNodeSpecification> inputs) {
        this.inputNodes = inputs;
    }

    @Override
    public List<OutputNodeSpecification> getOutputs() {
        return unmodifiable(outputNodes);
    }

    public void setOutputs(List<OutputNodeSpecification> outputs) {
        this.outputNodes = outputs;
    }

    @Override
    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String version) {
        formatVersion = version;
    }

    @Deprecated
    @Override
    public Map<String, Object> getTrainingKwargs() {
        return trainingKwargs;
    }

    @Deprecated
    public void setTrainingKwargs(Map<String, Object> trainingKwargs) {
        this.trainingKwargs = trainingKwargs;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<CitationSpecification> getCitations() {
        return unmodifiable(citations);
    }

    public void setCitations(List<CitationSpecification> citations) {
        this.citations = citations;
    }

    @Override
    public Map<String, WeightsSpecification> getWeights() {
        return unmodifiable(weights);
    }

    public void setWeights(Map<String, WeightsSpecification> weights) {
        this.weights = weights;
    }

    @Override
    public List<AuthorSpecification> getAuthors() {
        return unmodifiable(authors);
    }

    @Override
    public void setAuthors(List<AuthorSpecification> modelAuthors) {
        this.authors = modelAuthors;
    }

    @Override
    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @Override
    public List<String> getTags() {
        return unmodifiable(tags);
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getGitRepo() {
        return gitRepo;
    }

    public void setGitRepo(String repo) {
        this.gitRepo = repo;
    }

    @Override
    public Map<String, String> getAttachments() {
        return unmodifiable(attachments);
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
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

    @Override
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
        setParent(spec.getParent());
        setConfig(spec.getConfig());
    }

    @Deprecated
    @Override
    public String getTrainingSource() {
        return getSource();
    }

    @Deprecated
    public void setTrainingSource(String trainingSource) {
        setSource(trainingSource);
    }

    @Override
    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public void addWeights(String name, WeightsSpecification weights) {
        this.weights.put(name,weights);
    }

    public List<AuthorSpecification> getPackaged_by() {
        return packaged_by;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<AuthorSpecification> getMaintainers() {
        return maintainers;
    }

    public void setMaintainers(List<AuthorSpecification> maintainers) {
        this.maintainers = maintainers;
    }

    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }

    public void addBadge(BadgeSpecification badge) {
        badges.add(badge);
    }

    public void addPackagedBy(AuthorSpecification packager) {
        this.packaged_by.add(packager);
    }
}
