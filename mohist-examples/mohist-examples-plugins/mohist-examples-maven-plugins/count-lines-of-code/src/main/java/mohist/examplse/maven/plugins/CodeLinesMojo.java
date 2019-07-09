package mohist.examplse.maven.plugins;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.io.LineProcessor;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * mvn mohist.examples.maven.plugins:count-lines-of-code:1.0-SNAPSHOT:count-lines-of-code
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/7/9
 **/
@Mojo(name = "count-lines-of-code", defaultPhase = LifecyclePhase.VERIFY)
public class CodeLinesMojo extends AbstractMojo {

    private static final List<String> DEFAULT_FILES = Arrays.asList("java", "xml", "properties", "yml");

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File baseDir;

    @Parameter(defaultValue = "${project.build.sourceDirectory}", readonly = true)
    private File srcDir;

    @Parameter(defaultValue = "${project.build.testSourceDirectory}", readonly = true)
    private File testSrcDir;

    @Parameter(defaultValue = "${project.build.resources}", readonly = true)
    private List<Resource> resources;

    @Parameter(defaultValue = "${project.build.testResources}", readonly = true)
    private List<Resource> testResoures;

    @Parameter(defaultValue = "${count-lines-of-code.file.includes}", readonly = true)
    private Set<String> includes = new HashSet<>();

    private Log logger = getLog();
    private CustomLineProcessor clp = new CustomLineProcessor();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (includes.isEmpty()) {
            logger.debug("includes/count-lines-of-code.file.includes is empty!");
            includes.addAll(DEFAULT_FILES);
        }
        logger.info("includes: " + includes);

        try {
            long lines = 0;
            lines += countDir(srcDir);
            lines += countDir(testSrcDir);

            for (Resource resource : resources) {
                lines += countDir(new File(resource.getDirectory()));
            }

            for (Resource resource : testResoures) {
                lines += countDir(new File(resource.getDirectory()));
            }

            logger.info("code total lines: " + lines);

        } catch (IOException e) {
            logger.error("error: ", e);
            throw new MojoFailureException("execute failure: ", e);
        }
    }

    private long countDir(File directory) throws IOException {
        long lines = 0;
        if (directory.exists()) {
            Set<File> files = new HashSet<>();
            collectFiles(files, directory);

            for (File file : files) {
                FileReader reader = new FileReader(file);
                lines += CharStreams.readLines(reader, clp);
                reader.close();
            }

            String path = directory.getAbsolutePath().substring(baseDir.getAbsolutePath().length());

            logger.info(String.format("path: %s, file count: %d, total lines: %d", path, files.size(), lines));
            logger.info("\t-> files: " + files.toString());
        }

        return lines;
    }

    private void collectFiles(Set<File> files, File file) {
        if (file.isFile()) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if (index != -1 && includes.contains(fileName.substring(index + 1))) {
                files.add(file);
            }
        } else {
            File[] subFiles = file.listFiles();
            for (int i = 0; subFiles != null && i < subFiles.length; i ++) {
                collectFiles(files, subFiles[i]);
            }
        }
    }

    private class CustomLineProcessor implements LineProcessor<Long> {

        private long line = 0;

        @Override
        public boolean processLine(String fileLine) throws IOException {
            if (!Strings.isNullOrEmpty(fileLine)) {
                ++ this.line;
            }
            return true;
        }

        @Override
        public Long getResult() {
            long result = this.line;
            this.line = 0;
            return result;
        }
    }

}
