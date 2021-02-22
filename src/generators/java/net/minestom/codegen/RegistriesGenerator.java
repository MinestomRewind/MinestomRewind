package net.minestom.codegen;

import com.squareup.javapoet.*;
import net.minestom.server.entity.EntityType;
import net.minestom.server.fluids.Fluid;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Enchantment;
import net.minestom.server.item.Material;
import net.minestom.server.particle.Particle;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.potion.PotionType;
import net.minestom.server.sound.Sound;
import net.minestom.server.stat.StatisticType;
import net.minestom.server.utils.NamespaceID;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.*;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static net.minestom.codegen.MinestomEnumGenerator.DEFAULT_TARGET_PATH;

/**
 * Generates the Registries class, which contains methods to get items/blocks/biomes/etc. from a NamespaceID
 */
public class RegistriesGenerator implements CodeGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistriesGenerator.class);

    private static final ImmutablePair<String, String>[] registries = new ImmutablePair[]{
            new ImmutablePair<>(Block.class.getCanonicalName(), "AIR"),
            new ImmutablePair<>(Material.class.getCanonicalName(), "AIR"),
            new ImmutablePair<>(Enchantment.class.getCanonicalName(), null),
            new ImmutablePair<>(EntityType.class.getCanonicalName(), "PIG"),
            new ImmutablePair<>(Particle.class.getCanonicalName(), null),
            new ImmutablePair<>(PotionType.class.getCanonicalName(), null),
            new ImmutablePair<>(PotionEffect.class.getCanonicalName(), null),
            new ImmutablePair<>(Sound.class.getCanonicalName(), null),
            new ImmutablePair<>(StatisticType.class.getCanonicalName(), null),
            new ImmutablePair<>(Fluid.class.getCanonicalName(), "EMPTY"),
    };

    @Override
    public List<JavaFile> generate() throws IOException {
        TypeSpec.Builder registriesClass = TypeSpec.classBuilder(ClassName.get("net.minestom.server.registry", "Registries"))
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .addJavadoc("AUTOGENERATED");

        FieldSpec[] fields = new FieldSpec[registries.length];

        // Hashmaps
        for (int i = 0; i < registries.length; i++) {
            ClassName type = ClassName.bestGuess(registries[i].left);
            String simpleType = type.simpleName();

            FieldSpec field = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(HashMap.class), ClassName.get(NamespaceID.class), type), CodeGenerator.decapitalize(simpleType)+"s")
                    .addModifiers(Modifier.FINAL, Modifier.STATIC, Modifier.PUBLIC)
                    .addJavadoc("Should only be used for internal code, please use the get* methods.")
                    .addAnnotation(Deprecated.class)
                    .initializer("new HashMap<>()")
                    .build();
            fields[i] = field;
            registriesClass.addField(field);
        }

        // accessor methods
        for (int i = 0; i < registries.length; i++) {
            ClassName type = ClassName.bestGuess(registries[i].left);
            String simpleType = type.simpleName();
            String defaultValue = registries[i].right;

            // Example:
/*
            /** Returns 'AIR' if none match
            public static Block getBlock(String id) {
                return getBlock(NamespaceID.from(id));
            }

            /** Returns 'AIR' if none match
            public static Block getBlock(NamespaceID id) {
                return blocks.getOrDefault(id, AIR);
            }
*/
            StringBuilder comment = new StringBuilder("Returns the corresponding ");
            comment.append(simpleType).append(" matching the given id. Returns ");
            if (defaultValue != null) {
                comment.append('\'').append(defaultValue).append('\'');
            } else {
                comment.append("null");
            }
            comment.append(" if none match.");

            ParameterSpec namespaceIDParam = ParameterSpec.builder(ClassName.get(NamespaceID.class), "id")
                    .build();

            CodeBlock.Builder code = CodeBlock.builder();
            Class<? extends Annotation> annotation;
            if(defaultValue != null) {
                annotation = NotNull.class;
                code.addStatement("return $N.getOrDefault($N, $T.$N)", fields[i], namespaceIDParam, type, defaultValue);
            } else {
                annotation = Nullable.class;
                code.addStatement("return $N.get($N)", fields[i], namespaceIDParam);
            }

            // string variant
            ParameterSpec idParam = ParameterSpec.builder(ClassName.get(String.class), "id")
                    .build();
            MethodSpec idMethod = MethodSpec.methodBuilder("get"+simpleType)
                    .returns(type)
                    .addAnnotation(annotation)
                    .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                    .addParameter(idParam)
                    .addStatement("return get$N(NamespaceID.from($N))", simpleType, idParam)
                    .addJavadoc(comment.toString())
                    .build();
            registriesClass.addMethod(idMethod);

            // NamespaceID variant
            registriesClass.addMethod(MethodSpec.methodBuilder("get"+simpleType)
                    .returns(type)
                    .addAnnotation(annotation)
                    .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                    .addParameter(namespaceIDParam)
                    .addCode(code.build())
                    .addJavadoc(comment.toString())
                    .build());
        }

        JavaFile file = JavaFile.builder("net.minestom.server.registry", registriesClass.build())
                .addFileComment("AUTOGENERATED by "+getClass().getCanonicalName())
                .indent("    ")
                .skipJavaLangImports(true)
                .build();
        return Collections.singletonList(file);
    }

    public static void main(String[] args) throws IOException {
        // copy-pasted from BlockEnumGenerator, to stay consistent in the order of arguments
        String targetVersion;
        if (args.length < 1) {
            System.err.println("Usage: <MC version> [target folder]");
            return;
        }

        targetVersion = args[0];

        String targetPart = DEFAULT_TARGET_PATH;
        if (args.length >= 2) {
            targetPart = args[1];
        }

        File targetFolder = new File(targetPart);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        new RegistriesGenerator().generateTo(targetFolder);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
