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
package io.bioimage.specification.io;

import io.bioimage.specification.*;
import io.bioimage.specification.transformation.ImageTransformation;
import io.bioimage.specification.transformation.ZeroMeanUnitVarianceTransformation;
import io.bioimage.specification.weights.TensorFlowSavedModelBundleSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class SpecificationReaderWriterV3Test {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testExampleSpec() throws IOException {

        // create spec

        File dir = folder.getRoot();
        DefaultModelSpecification specification = new DefaultModelSpecification();
        String modelSpecificationFile = getClass().getResource("/example.0.3.1.model.yaml").getPath();
        SpecificationReader.read(modelSpecificationFile, specification);

        // check values
        checkContent(specification);

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
        checkContent(newSpec);

    }

    private void checkContent(ModelSpecification specification) {
        // meta
        assertEquals("0.3.1", specification.getFormatVersion());
        assertEquals("modelname", specification.getName());
        assertEquals("authors", specification.getAuthors().get(0).getName());
        assertEquals("model description", specification.getDescription());
        assertEquals("model documentation", specification.getDocumentation());
        assertEquals("bsd", specification.getLicense());
        assertArrayEquals(new String[]{"denoising", "unet2d"}, specification.getTags().toArray());
        assertEquals(1, specification.getCitations().size());
        CitationSpecification citation = new DefaultCitationSpecification();
        citation.setCitationText("my citation");
        citation.setDOIText("https://arxiv.org/abs/2005.02987");
        assertEquals(citation, specification.getCitations().get(0));
        assertNotNull(specification.getTestInputs());
        assertEquals(1, specification.getTestInputs().size());
        assertEquals("test_input.tif", specification.getTestInputs().get(0));
        assertNotNull(specification.getTestOutputs());
        assertEquals(1, specification.getTestOutputs().size());
        assertEquals("test_output.tif", specification.getTestOutputs().get(0));

        // input
        assertEquals(1, specification.getInputs().size());
        InputNodeSpecification _input = specification.getInputs().get(0);
        assertEquals("input", _input.getName());
        assertEquals("byxc", _input.getAxes());
        assertEquals("float32", _input.getDataType());
        assertArrayEquals(new Integer[]{1, 4, 4, 1}, _input.getShapeMin().toArray());
        assertArrayEquals(new Integer[]{0, 4, 4, 0}, _input.getShapeStep().toArray());
        assertNotNull(_input.getPreprocessing());
        assertEquals(1, _input.getPreprocessing().size());
        assertEquals(ZeroMeanUnitVarianceTransformation.name, _input.getPreprocessing().get(0).getName());
        ZeroMeanUnitVarianceTransformation preIn = (ZeroMeanUnitVarianceTransformation) _input.getPreprocessing().get(0);
        assertEquals(41498.87, preIn.getMean());
        assertEquals(15007.021, preIn.getStd());
        assertEquals(ImageTransformation.Mode.FIXED, preIn.getMode());

        // output
        assertEquals(1, specification.getOutputs().size());
        OutputNodeSpecification _output = specification.getOutputs().get(0);
        assertEquals("denoised", _output.getName());
        assertEquals("byxc", _output.getAxes());
        assertEquals("input", _output.getReferenceInputName());
        assertArrayEquals(new Integer[]{0, 0, 0, 0}, _output.getShapeOffset().toArray());
        assertArrayEquals(new Integer[]{1, 1, 1, 1}, _output.getShapeScale().toArray());

        assertNotNull(specification.getWeights());
        assertEquals(1, specification.getWeights().size());
        WeightsSpecification weights = specification.getWeights().get(TensorFlowSavedModelBundleSpecification.id);
        assertTrue(weights instanceof TensorFlowSavedModelBundleSpecification);
        assertNotNull(weights.getSha256());
        assertNotNull(weights.getSource());

        assertNotNull(specification.getConfig());
        Map<String, Object> fijiConfig = (Map<String, Object>) specification.getConfig().get("fiji");
        assertEquals(64, fijiConfig.get("batchSize"));
    }

}
