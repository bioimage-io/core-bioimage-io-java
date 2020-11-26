package io.bioimage.specification.io;

import io.bioimage.specification.DefaultModelSpecification;
import io.bioimage.specification.ModelSpecification;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
		if(SpecificationReaderWriterV3.canRead(obj)) {
			SpecificationReaderWriterV3.read(specification, obj);
			return true;
		}
		if(SpecificationReaderV2.canRead(obj)) {
			SpecificationReaderV2.read(specification, obj);
			return true;
		}
		if(SpecificationReaderV1.canRead(obj)) {
			SpecificationReaderV1.read(specification, obj);
			return true;
		}
		return false;
	}
}
