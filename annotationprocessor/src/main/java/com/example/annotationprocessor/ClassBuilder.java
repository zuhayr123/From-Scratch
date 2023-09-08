package com.example.annotationprocessor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.example.annotationprocessor.Builder")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ClassBuilder extends AbstractProcessor {

    private ProcessingEnvironment mProcessingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessingEnvironment = processingEnvironment;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, "Builder Processing started ********");
        for (Element element : roundEnv.getElementsAnnotatedWith(Builder.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                try {
                    generateBuilder(typeElement);
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating builder for " + typeElement.getSimpleName() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void generateBuilder(TypeElement typeElement) throws IOException {
        String className = typeElement.getSimpleName().toString();
        String builderClassName = className + "Builder";
        PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
        String packageName = packageElement.getQualifiedName().toString();

        TypeSpec.Builder builderClass = TypeSpec.classBuilder(builderClassName)
                .addModifiers(Modifier.PUBLIC);

        for (Element enclosed : typeElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) enclosed;
                String fieldName = variableElement.getSimpleName().toString();
                TypeMirror fieldType = variableElement.asType();

                // Generate setter method for each field
                MethodSpec setter = MethodSpec.methodBuilder("set" + capitalize(fieldName))
                        .addModifiers(Modifier.PUBLIC)
                        .returns(ClassName.get(packageName, builderClassName))
                        .addParameter(TypeName.get(fieldType), fieldName)
                        .addStatement("this.$N = $N", fieldName, fieldName)
                        .addStatement("return this")
                        .build();

                builderClass.addMethod(setter);
                builderClass.addField(TypeName.get(fieldType), fieldName, Modifier.PRIVATE);
            }
        }

        // Generate build() method
        MethodSpec buildMethod = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(packageName, className))
                .addStatement("return new $N(this.age, this.name)", className)
                .build();

        builderClass.addMethod(buildMethod);

        JavaFile javaFile = JavaFile.builder(packageName, builderClass.build()).build();
        javaFile.writeTo(processingEnv.getFiler());
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
