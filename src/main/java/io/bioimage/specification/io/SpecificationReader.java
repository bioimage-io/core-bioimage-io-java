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

import io.bioimage.specification.DefaultModelSpecification;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static io.bioimage.specification.io.SpecificationWriter.modelFileName;

public class SpecificationReader {

    public static boolean readFromZIP(File zippedModel, DefaultModelSpecification specification) {
        try {
            return read(SpecificationWriter.extractFile(zippedModel, modelFileName), specification);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean readFromDirectory(File directory, DefaultModelSpecification specification) throws IOException {
        return read(new File(directory, modelFileName), specification);
    }

    public static boolean read(String modelSpecificationFile, DefaultModelSpecification specification) throws IOException {
        return read(new File(modelSpecificationFile), specification);
    }

    public static boolean read(File modelSpecificationFile, DefaultModelSpecification specification) throws IOException {
        try (InputStream stream = new FileInputStream(modelSpecificationFile)) {
            return read(stream, specification);
        }
    }

    public static boolean read(Path modelSpecificationPath, DefaultModelSpecification specification) throws IOException {
        try (InputStream stream = Files.newInputStream(modelSpecificationPath)) {
            return read(stream, specification);
        }
    }

    public static boolean read(InputStream stream, DefaultModelSpecification specification) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> obj = yaml.load(stream);
//		System.out.println(obj);
        if (obj == null) return false;
        return read(obj, specification);
    }

    private static boolean read(Map<String, Object> obj, DefaultModelSpecification specification) throws IOException {
        if (SpecificationReaderWriterV3.canRead(obj)) {
            SpecificationReaderWriterV3.read(specification, obj);
            return true;
        }
        if (SpecificationReaderWriterV2.canRead(obj)) {
            SpecificationReaderWriterV2.read(specification, obj);
            return true;
        }
        if (SpecificationReaderWriterV1.canRead(obj)) {
            SpecificationReaderWriterV1.read(specification, obj);
            return true;
        }
        return false;
    }
}
