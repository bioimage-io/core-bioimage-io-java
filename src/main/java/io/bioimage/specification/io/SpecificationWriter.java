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

import io.bioimage.specification.ModelSpecification;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SpecificationWriter {

	public final static String dependenciesFileName = "dependencies.yaml";
	final static String modelFileName = "rdf.yaml";
	static Representer representer = new Representer() {
		@Override
		protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
			NodeTuple tuple = super.representJavaBeanProperty(javaBean, property, propertyValue,
					customTag);
			Node valueNode = tuple.getValueNode();
			if (Tag.NULL.equals(valueNode.getTag())) {
				return null;// skip 'null' values
			}
			if (valueNode instanceof CollectionNode) {
				if (Tag.SEQ.equals(valueNode.getTag())) {
					SequenceNode seq = (SequenceNode) valueNode;
					if (seq.getValue().isEmpty()) {
						return null;// skip empty lists
					}
				}
				if (Tag.MAP.equals(valueNode.getTag())) {
					MappingNode seq = (MappingNode) valueNode;
					if (seq.getValue().isEmpty()) {
						return null;// skip empty maps
					}
				}
			}
			return tuple;
		}
	};

	public static void write(ModelSpecification specification, String targetDirectory) throws IOException {
		write(specification, new File(targetDirectory));
	}

	public static void write(ModelSpecification specification, File targetDirectory) throws IOException {
		writeDependenciesFile(targetDirectory);
		Map<String, Object> data = write(specification);
		data.values().removeIf(SpecificationWriter::removeNulls);

		DumperOptions options = new DumperOptions();
		options.setIndent(2);
		Yaml yaml = new Yaml(representer, options);
		try (FileWriter writer = new FileWriter(new File(targetDirectory, modelFileName))) {
			yaml.dump(data, writer);
		}
	}

	private static boolean removeNulls(final Object o) {
		if (Objects.isNull(o)) {
			return true;
		} else if (o instanceof LinkedHashMap) {
			((LinkedHashMap) o).values().removeIf(SpecificationWriter::removeNulls);
		} else if (o instanceof List) {
			((List) o).forEach(elem->{
				if (elem instanceof LinkedHashMap) {
					((LinkedHashMap) elem).values().removeIf(SpecificationWriter::removeNulls);
				}
			});

		}
		return false;
	}

	public static void write(ModelSpecification specification, Path modelSpecificationPath) throws IOException {
		Map<String, Object> data = write(specification);
		DumperOptions options = new DumperOptions();
		options.setIndent(2);
		Yaml yaml = new Yaml(representer, options);
		try {
			Files.delete(modelSpecificationPath);
		} catch(IOException ignored) {}
		try (Writer writer = Files.newBufferedWriter(modelSpecificationPath)) {
			yaml.dump(data, writer);
		}
	}

	public static Map<String, Object> write(ModelSpecification specification) {
		if(SpecificationReaderWriterV4.canWrite(specification)) {
			return SpecificationReaderWriterV4.write(specification);
		}
		if(SpecificationReaderWriterV3.canWrite(specification)) {
			return SpecificationReaderWriterV3.write(specification);
		}
		if(SpecificationReaderWriterV2.canWrite(specification)) {
			return SpecificationReaderWriterV2.write(specification);
		}
		if(SpecificationReaderWriterV1.canWrite(specification)) {
			return SpecificationReaderWriterV1.write(specification);
		}
		return null;
	}


	static InputStream extractFile(File zipFile, String fileName) throws IOException {
		ZipFile zf = new ZipFile(zipFile);
		return zf.getInputStream(zf.getEntry(fileName));
	}

	private static void writeDependenciesFile(File targetDirectory) {
		Map<String, Object> data = new LinkedHashMap<>();
		List<String> dependencies = new ArrayList<>();
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		for (URL url : ((URLClassLoader) cl).getURLs()) {
			dependencies.add(url.getPath());
		}
		data.put("classPath", dependencies);
		Yaml yaml = new Yaml(representer);
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(targetDirectory, dependenciesFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		yaml.dump(data, writer);
	}

	public static String getModelFileName() {
		return modelFileName;
	}
}
