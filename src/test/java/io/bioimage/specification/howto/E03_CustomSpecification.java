package io.bioimage.specification.howto;

import io.bioimage.specification.CustomSpecification;
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
		specification.write(file);

		// read the specification again from disk
		specification = new CustomSpecification();
		specification.read(file);

		// access custom variable
		System.out.println(specification.getSecret());

		file.toFile().delete();
	}

	public static void main(String... args) throws IOException {
		new E03_CustomSpecification().run();
	}

}
