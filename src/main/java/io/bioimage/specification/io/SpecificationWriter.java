package io.bioimage.specification.io;

import io.bioimage.specification.ModelSpecification;
import org.yaml.snakeyaml.Yaml;

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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SpecificationWriter {

	public final static String dependenciesFileName = "dependencies.yaml";
	final static String modelFileName = "model.yaml";

	public static void write(ModelSpecification specification, String targetDirectory) throws IOException {
		write(specification, new File(targetDirectory));
	}

	public static void write(ModelSpecification specification, File targetDirectory) throws IOException {
		writeDependenciesFile(targetDirectory);
		// when (re)writing the specification, use the most recent specification version
		specification.updateToNewestVersion();
		Map<String, Object> data = write(specification);
		Yaml yaml = new Yaml();
		try (FileWriter writer = new FileWriter(new File(targetDirectory, modelFileName))) {
			yaml.dump(data, writer);
		}
	}

	public static void write(ModelSpecification specification, Path modelSpecificationPath) throws IOException {
		specification.updateToNewestVersion();
		Map<String, Object> data = write(specification);
		Yaml yaml = new Yaml();
		try {
			Files.delete(modelSpecificationPath);
		} catch(IOException ignored) {}
		try (Writer writer = Files.newBufferedWriter(modelSpecificationPath)) {
			yaml.dump(data, writer);
		}
	}

	public static Map<String, Object> write(ModelSpecification specification) {
		return SpecificationReaderWriterV3.write(specification);
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
		Yaml yaml = new Yaml();
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
