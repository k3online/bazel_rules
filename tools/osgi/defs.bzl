
def _osgi_bundle_impl(ctx):
  instructions=''

  # join instructions to key=value strings separated by newline
  for k,v in ctx.attr.instructions.items():
    instructions += "%s=%s\n" % (k,v)

  # convert to depset with files
  jar_set = ctx.file.input_jar

  args = [ctx.file.input_jar.path, ctx.outputs.output_jar.path, instructions]

  ctx.actions.run(
      inputs=depset([ctx.file.input_jar]),
      outputs=[ctx.outputs.output_jar],
      arguments=args,
      progress_message="assembling bundle artifact: %s" % ctx.attr.name,
      executable=ctx.executable._bundle_exec
  )

  # use input provider
  provider = ctx.attr.input_jar[java_common.provider]
  return struct(
     providers = [provider]
  )

osgi_bundle = rule(
    attrs = {
        "input_jar": attr.label(allow_single_file=True),
        "instructions": attr.string_dict(),
        "_bundle_exec": attr.label(
            default = Label("//tools/osgi/bundle:bin"),
            executable = True,
            cfg = "target",
        ),
    },
    fragments = ["java"],
    outputs = {
        "output_jar": "%{name}.jar",
    },
    implementation = _osgi_bundle_impl,
)
