
package(default_visibility = ["//visibility:public"])

java_binary(
    name = "bin",
    main_class = "tools.osgi.bundle.OsgiUtil",
    runtime_deps = ["bin_lib"],
)

java_library(
    name = "bin_lib",
    srcs = glob(["OsgiUtil.java"]),
    deps = ["@maven//:biz_aQute_bnd_biz_aQute_bndlib", "@maven//:org_slf4j_slf4j_api"],
)