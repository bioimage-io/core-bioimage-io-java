package io.bioimage.specification.howto;

import io.bioimage.specification.CustomSpecification;
import io.bioimage.specification.io.SpecificationReader;
import io.bioimage.specification.io.SpecificationWriter;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class E03_CustomSpecification {

	@Test
	public void run() throws IOException {

		// create custom specification
		CustomSpecification specification = new CustomSpecification();

		// set custom variable
		specification.setSecret("birds are dinosaurs");

		Path file = Files.createTempFile("myspec", ".yaml");
		SpecificationWriter.write(specification, file);

		// read the specification again from disk
		specification = new CustomSpecification();
		SpecificationReader.read(file, specification);

		// access custom variable
		System.out.println(specification.getSecret());

		file.toFile().delete();
	}

	public static void main(String... args) throws IOException {
		new E03_CustomSpecification().run();
	}

}
