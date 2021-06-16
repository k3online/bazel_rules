
def _pkg_jar_impl(ctx):
  manifestProps=''

  for k,v in ctx.attr.manifestProps.items():
    manifestProps += "%s=%s\n" % (k,v)

  args = [ctx.outputs.output_jar.path, manifestProps]

  # convert to depset with files
  jar_set = depset([ j.files.to_list()[0] for j in ctx.attr.input_jars ])

  for src in jar_set.to_list():
    args.append(src.path)

  ctx.actions.run(
      inputs=jar_set,
      outputs=[ctx.outputs.output_jar],
      arguments=args,
      progress_message="assembling jar artifact: %s" % ctx.attr.name,
      executable=ctx.executable._jar_exec
  )
  # return a java provider. useful info here: https://blog.bazel.build/2017/03/07/java-sandwich.html
  deps =[]
  if java_common.provider in ctx.attr.input_jars:
    deps.append(ctx.attr.input_jars[java_common.provider])
  deps_provider = java_common.merge(deps)
  return struct(
    providers = [deps_provider]
  )

pkg_jar = rule(
    attrs = {
        "input_jars": attr.label_list(allow_files=True),
        "manifestProps": attr.string_dict(),
        "_jar_exec": attr.label(
            default = Label("//tools/java/pkg:jar"),
            executable = True,
            cfg = "target",
        ),
    },
    fragments = ["java"],
    outputs = {
        "output_jar": "%{name}.jar",
    },
    implementation = _pkg_jar_impl,
)
