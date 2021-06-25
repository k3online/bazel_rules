workspace(name = "bazel-rules")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.1"
RULES_JVM_EXTERNAL_SHA = "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
       "junit:junit:4.12",
       "com.google.guava:guava:28.0-jre",
       "biz.aQute.bnd:biz.aQute.bndlib:3.4.0",
       "org.apache.felix:org.apache.felix.scr.bnd:1.9.6",
       "org.slf4j:slf4j-api:1.7.24",
        "com.google.code.gson:gson:2.8.7",
        "org.apache.commons:commons-lang3:3.12.0",
   ],
    version_conflict_policy = "pinned",
    fetch_sources = True,
    repositories = [
        "https://repo1.maven.org/maven2",
   ],
)