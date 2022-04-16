import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllLines;

public class Language {
    private String name;
    private List<String> texts;
    private Perceptron p;

    public Language(String name) {
        this.name = name;
        texts = new ArrayList<>();
        p = new Perceptron();
    }

    void addText(String newText, String languageName) {
        texts.add(newText);
        if (languageName.equals(name)) {
            p.learn(newText, 1);
        } else {
            p.learn(newText, 0);
        }
    }

    boolean test(String testText){
        return p.test(testText);
    }

    String getName() {
        return name;
    }

    static List<Language> Dir(String dirName) {
        List<Language> languages = new ArrayList<>();
        try {
            Stream<Path> streamPath= Files.walk(new File(dirName).toPath());
            for (Path p: streamPath.filter(Files::isDirectory).collect(Collectors.toList())) {
                if (!p.toString().equals("training")) {
                    languages.add(new Language(p.getFileName().toString()));
                }
            }
            Stream<Path> streamPath1= Files.walk(new File(dirName).toPath());
            for (Path p: streamPath1.filter(Files::isDirectory).collect(Collectors.toList())) {
                if (!p.toString().equals("training")) {
                    String languageName = p.getFileName().toString();
                    try {
                        Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                try {
                                    List<String> lines = readAllLines(file, Charset.forName("UTF-8"));
                                    StringBuilder sb = new StringBuilder();
                                    for (String s : lines) {
                                        sb.append(s);
                                    }
                                    for (Language l : languages) {
                                        l.addText(sb.toString(), languageName);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return FileVisitResult.CONTINUE;
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return languages;
    }
}
