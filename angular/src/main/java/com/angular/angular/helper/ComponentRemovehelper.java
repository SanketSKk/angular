package com.angular.angular.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ComponentRemovehelper {

	public static void deleteDirectory(Path directory) throws IOException {
		Files.walk(directory).map(Path::toFile).sorted((o1, o2) -> -o1.compareTo(o2)).forEach(File::delete);
	}
	
}
