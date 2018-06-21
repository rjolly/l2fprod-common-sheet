mkdir("build");
mkdir("build/classes");

javac("src", "build/classes");
copy("res", "build/classes");

mkdir("dist");
jar("dist/l2fprod-common-sheet.jar", "build/classes");

publish("dist")
