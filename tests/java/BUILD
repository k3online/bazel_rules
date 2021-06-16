load("//tools/java:jar.bzl", "pkg_jar")

pkg_jar(
    name = "pkgJar",
    input_jars = ["@maven//:com_google_guava_guava", "@maven//:junit_junit", ":foo_lib"],
    manifestProps = {"Bundle-Version": "2.5", "Export-Package": "b"},
)

java_library(
    name = "foo_lib",
    srcs = ["Foo.java"],
)