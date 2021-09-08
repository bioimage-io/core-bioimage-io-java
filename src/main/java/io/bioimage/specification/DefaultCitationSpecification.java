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

import java.util.Objects;

public class DefaultCitationSpecification implements CitationSpecification {


	private String citationText;

	private String doiText;

	private String url;

	@Override
	public void setCitationText(String citationText) {
		this.citationText = citationText;
	}

	@Override
	public void setDOIText(String doiText) {
		this.doiText = doiText;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getCitationText() {
		return citationText;
	}

	@Override
	public String getDoiText() {
		return doiText;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		return Objects.hash(citationText, doiText, url);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DefaultCitationSpecification that = (DefaultCitationSpecification) o;
		return citationText.equals(that.citationText) && Objects.equals(doiText, that.doiText) && Objects.equals(url, that.url);
	}
}
