load("//tools/osgi:defs.bzl", "osgi_bundle")

osgi_bundle(
    name = "test_bundle",
    input_jar = ":osgi_lib",
    embed_dependency = ["@maven//:com_google_code_gson_gson","@maven//:org_apache_commons_commons_lang3",],
    instructions = {"Bundle-SymbolicName": "myBundleName", "Bundle-Version": "2.5", "Export-Package": "b"},
)

java_library(
    name = "osgi_lib",
    srcs = glob(["*.java"]),
    deps=["@maven//:biz_aQute_bnd_biz_aQute_bndlib"],
)