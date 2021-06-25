package(default_visibility = ["//visibility:public"])

java_binary(
    name = "bin",
    srcs = glob(["*.java"]),
    main_class = "tools.osgi.bundle.OsgiUtil",
    deps = ["@maven//:biz_aQute_bnd_biz_aQute_bndlib", "@maven//:org_slf4j_slf4j_api"],
)
