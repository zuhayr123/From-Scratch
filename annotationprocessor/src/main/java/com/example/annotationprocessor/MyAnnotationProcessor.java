package com.example.annotationprocessor;

import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.example.annotationprocessor.GenerateClass")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class MyAnnotationProcessor extends AbstractProcessor {

    private ProcessingEnvironment mProcessingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessingEnvironment = processingEnvironment;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            roundEnv.getElementsAnnotatedWith(annotation).forEach(element -> {
                mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing started ********");
                GenerateClass generateClassAnnotation = element.getAnnotation(GenerateClass.class);
                String className = generateClassAnnotation.name();
                generateClass(className);
            });
        }
        return true;
    }

    private void generateClass(String className) {
        try {
            String relativeClassName = "com.example.annotationprocessor."+className;
            mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generate class called ******");
            JavaFileObject jfo = mProcessingEnvironment.getFiler().createSourceFile(relativeClassName);
            try (Writer writer = jfo.openWriter()) {
                writer.write("package com.example.annotationprocessor;\n\n");
                writer.write("public class " + className + " {\n\n");
                writer.write("    public void print() {\n");
                writer.write("        System.out.println(\"Hello from " + className + "!\");\n");
                writer.write("    }\n");
                writer.write("}\n");
            }
        } catch (Exception e) {
            mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, "Unable to write ******" + e.getMessage());
            e.printStackTrace();
        }
    }
}