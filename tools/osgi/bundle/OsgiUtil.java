package tools.osgi.bundle;

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.FileResource;
import aQute.bnd.osgi.Jar;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OsgiUtil {

    public static void main(String[] args) {
        if (args.length != 3) {
            error("Usage: OsgiUtil <input jar> <output jar> <instructions>");
        }

        String inputJar = args[0];
        String outputJar = args[1];
        String instructionStr = args[2];

        try {
            Builder builder = new Builder();
            builder.setJar(new Jar(new File(inputJar)));

            Map<String, String> instructionMap = toMap(instructionStr);
            String embedDepStr = instructionMap.get("Embed-Dependency");
            String bundleClassPath = ".", embeddedArtifacts="";

            for(String embedSource: embedDepStr.split(",")) {
                String[] data = embedSource.split(";");
                String fileName = data[0];
                bundleClassPath+=","+fileName;
                embeddedArtifacts+=fileName+",";
            }

            instructionMap.put("Bundle-ClassPath", bundleClassPath);
            instructionMap.put("Embedded-Artifacts", embeddedArtifacts);
            instructionMap.put("Embed-Dependency", embeddedArtifacts);


            instructionMap.forEach((k, v) -> builder.setProperty(k,v));

            Jar outJar = builder.build();

            updateJarWithEmbededDependencies(embedDepStr, outJar);

            final File outFile = new File(outputJar);
            if(!outFile.exists()) {
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
            }

            outJar.write(outFile);
        } catch (Exception e) {
            error("could not assemble osgi jar "+e.getLocalizedMessage());
        }
    }

    private static void updateJarWithEmbededDependencies(String embedDepStr, Jar outJar) throws IOException {
        for(String embedSource: embedDepStr.split(",")) {
            String[] data = embedSource.split(";");

            String fileName = data[0];
            String filePath = data[1];
            outJar.putResource(fileName, new FileResource(new File(filePath)));
        }
    }

    private static Map<String,String> toMap(String instructionStr) {
        final Map<String,String> instructionMap = new HashMap<>();

        for (String s : instructionStr.split("\n")) {
            final String[] data = s.split("=");
            if(data.length < 2) {
                error("instructions should key=value separated by newlines");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < data.length; i++) {
                    sb.append(data[i]);
                }

                instructionMap.put(data[0], sb.toString());
            }
        }

        if(!instructionMap.containsKey("Bundle-SymbolicName")) {
            error("Bundle-SymbolicName header missing");
        }

        return instructionMap;
    }

    static void error(String err) {
        throw new RuntimeException(err);
    }
}



