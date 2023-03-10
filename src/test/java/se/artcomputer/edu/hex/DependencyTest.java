package se.artcomputer.edu.hex;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DependencyTest {

    public static final String PACKAGE_ROOT = DependencyTest.class.getPackageName();
    public static final String DOMAIN = PACKAGE_ROOT + ".domain";
    public static final String OUT_ADAPTER = PACKAGE_ROOT + ".out.adapter";
    public static final String OUT_PORT = PACKAGE_ROOT + ".out.port";
    public static final String IN_PORT = PACKAGE_ROOT + ".in.port";
    private static final String IN_ADAPTER = PACKAGE_ROOT + ".in.adapter";
    private static final String CONFIG = PACKAGE_ROOT + ".config";
    private static Map<String, Set<String>> collectedDependencies;

    @BeforeAll
    static void beforeAll() throws IOException {
        collectedDependencies = new HashMap<>();
        collectDependencies(new File("src/main/java"), collectedDependencies);
        dumpDependencies();
    }

    private static void dumpDependencies() {
        collectedDependencies
                .keySet()
                .stream()
                .sorted()
                .forEach(key -> System.out.println(key + ": " + collectedDependencies.get(key)));
    }

    @Test
    void domain_must_not_depend_on_other_packages_in_the_system() {
        assertDependencies(DOMAIN, p -> p.startsWith(DOMAIN));
    }

    @Test
    void config_must_only_depend_on_port_out() {
        assertDependencies(CONFIG, p -> (p.startsWith(OUT_PORT) || p.startsWith(CONFIG)));
    }

    @Test
    void out_adapter_must_only_depend_on_domain_and_port_out() {
        assertDependencies(OUT_ADAPTER, p -> p.startsWith(DOMAIN) || p.startsWith(OUT_PORT));
    }

    @Test
    void in_adapter_must_only_depend_on_domain_and_port_in() {
        assertDependencies(IN_ADAPTER, p -> p.startsWith(DOMAIN) || p.startsWith(IN_PORT));
    }

    @Test
    void out_port_depends_only_on_domain() {
        assertDependencies(OUT_PORT, p -> p.startsWith(DOMAIN) || p.startsWith(OUT_PORT));
    }

    @Test
    void in_port_depends_only_on_domain() {
        assertDependencies(IN_PORT, p -> p.startsWith(DOMAIN) || p.startsWith(CONFIG));
    }

    private static void assertDependencies(String packagePrefix, Predicate<String> acceptedDependencies) {
        for (String currentPackage : getPackagesStartingWith(packagePrefix)) {
            Set<String> illicitInPackage = collectedDependencies.get(currentPackage).stream()
                    .filter(acceptedDependencies.negate())
                    .collect(Collectors.toSet());
            assertEquals(0, illicitInPackage.size(), "Package " + currentPackage + " depends on " + illicitInPackage);
        }
    }

    private static Set<String> getPackagesStartingWith(String packagePrefix) {
        Set<String> packages = collectedDependencies.keySet().stream().filter(k -> k.startsWith(packagePrefix)).collect(Collectors.toSet());
        assertTrue(packages.size() > 0, "No packages found with prefix: " + packagePrefix);
        return packages;
    }

    private static void collectDependencies(File current, Map<String, Set<String>> collectedDependencies) throws IOException {
        if (current.isFile()) {
            if (current.getPath().endsWith("java")) {
                String currentPackage = getJavaPackageOfFile(current);
                Set<String> soFar = collectedDependencies.getOrDefault(currentPackage, new HashSet<>());
                soFar.addAll(dependenciesOfFile(current));
                collectedDependencies.put(currentPackage, soFar);
            }
        }
        if (current.isDirectory()) {
            for (File f : Objects.requireNonNull(current.listFiles())) {
                collectDependencies(f, collectedDependencies);
            }
        }
    }

    private static Set<String> dependenciesOfFile(File current) throws IOException {
        try (Stream<String> lines = Files.lines(current.toPath())) {
            return lines
                    .filter(s -> s.startsWith("import " + PACKAGE_ROOT))
                    .map(DependencyTest::extractDependency)
                    .collect(Collectors.toSet());
        }
    }

    private static String extractDependency(String importStatement) {
        return importStatement.substring("import ".length(), importStatement.lastIndexOf('.'));
    }

    private static String getJavaPackageOfFile(File current) throws IOException {
        try (Stream<String> lines = Files.lines(current.toPath())) {
            return lines
                    .filter(s -> s.startsWith("package " + PACKAGE_ROOT))
                    .findFirst()
                    .map(s -> s.substring("package ".length(), s.indexOf(';')))
                    .orElse("");
        }
    }
}