package tools.java.pkg;

import java.io.*;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Builds jar file
 * Input args: <dest.jar> <input jars> <manifest properties string>
 */
public class JarUtil {
    public static void main(String[] args) throws IOException {
        System.out.println("Assembling jar with args ... "+ Arrays.toString(args));

        String[] sources = new String[args.length-2];

        for(int i=2; i< args.length; i++)
            sources[i-2] = args[i];

        new JarUtil().addToJar(args[0], args[1], sources);
    }

    public void addToJar(String destJarPath, String manifestProperties, String...sources) throws IOException {
        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        for(String prop: manifestProperties.split("\n")) {
            String[] data = prop.split("=");
            attributes.put(new Attributes.Name(data[0]), data[1]);
        }

        JarOutputStream target = new JarOutputStream(new FileOutputStream(destJarPath), manifest);
        add(target, sources);
        target.close();
    }

    private void add(JarOutputStream target, String[] sources) throws IOException {
        for(String src: sources) {
            File source = new File(src);
            BufferedInputStream in = null;
            try {
                JarEntry entry = new JarEntry(source.getName());
                entry.setTime(source.lastModified());
                target.putNextEntry(entry);
                in = new BufferedInputStream(new FileInputStream(source));

                byte[] buffer = new byte[1024];
                while (true)
                {
                    int count = in.read(buffer);
                    if (count == -1)
                        break;
                    target.write(buffer, 0, count);
                }
                target.closeEntry();
            }
            finally
            {
                if (in != null)
                    in.close();
            }
        }
    }
}


