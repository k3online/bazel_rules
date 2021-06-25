
def _osgi_bundle_impl(ctx):
  instructions=''

  # join instructions to key=value strings separated by newline
  for k,v in ctx.attr.instructions.items():
    instructions += "%s=%s\n" % (k,v)

  embed_deps = depset([ j.files.to_list()[0] for j in ctx.attr.embed_dependency ])

  embed_arg=''
  for j in ctx.attr.embed_dependency:
      print(j.files.to_list()[0].basename)
      print(j.files.to_list()[0].path)
      embed_arg += "%s;%s," % (j.files.to_list()[0].basename,j.files.to_list()[0].path)

  if(embed_arg):
      instructions += "Embed-Dependency=%s\n" % (embed_arg)

#  print(embed_deps)
#  print(embed_deps.to_list()[0].basename)
#  print(embed_deps.to_list()[0].path)
 # for j in ctx.attr.embed_dependency:
 #     print(j[JavaInfo])

  # convert to depset with files
  jar_set = ctx.file.input_jar

  args = [ctx.file.input_jar.path, ctx.outputs.output_jar.path, instructions]

  ctx.actions.run(
      inputs=depset([ctx.file.input_jar]+embed_deps.to_list()),
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
        "embed_dependency": attr.label_list(allow_files=True),
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
