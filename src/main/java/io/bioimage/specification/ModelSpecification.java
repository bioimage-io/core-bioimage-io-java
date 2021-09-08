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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * This is the ImageJ version of the model zoo configuration specification
 * https://github.com/bioimage-io/configuration
 */
public interface ModelSpecification {

	List<String> getTestInputs();

	List<String> getTestOutputs();

	List<String> getSampleInputs();

	List<String> getSampleOutputs();

	List<InputNodeSpecification> getInputs();

	List<OutputNodeSpecification> getOutputs();

	String getFormatVersion();

	String getLanguage();

	String getFramework();

	String getName();

	String getDescription();

	List<CitationSpecification> getCitations();

	Map<String, WeightsSpecification> getWeights();

	List<AuthorSpecification> getAuthors();

	String getDocumentation();

	List<String> getTags();

	String getLicense();

	String getSource();

	String getGitRepo();

	Map<String, String> getAttachments();

	String getTimestamp();

	String getExecutionModel();

	@Deprecated
	Map<String, Object> getTrainingKwargs();

	@Deprecated
	String getTrainingSource();

	Map<String, Object> getConfig();

	void setSampleInputs(List<String> inNames);

	void setSampleOutputs(List<String> outNames);

	void setName(String name);

	void setAuthors(List<AuthorSpecification> authors);

	void setTags(List<String> tags);

	void setDescription(String description);

    String getDependencies();

	List<String> getCovers();

	String getHash();

	ParentSpecification getParent();

	List<BadgeSpecification> getBadges();

	void setBadges(List<BadgeSpecification> badges);

	void setVersion(String s);

	void setType(String s);

	String getVersion();

	String getType();
}
