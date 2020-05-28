all: run

clean:
	rm -f out/Main.jar out/ConvexHullParallel.jar

out/Main.jar: out/parcs.jar src/Main.java src/ConvexHullParallel.java src/ConvexHull.java
	@javac -cp out/parcs.jar src/Main.java src/ConvexHullParallel.java src/ConvexHull.java
	@jar cf out/Main.jar -C src Main.class -C src ConvexHullParallel.class -C src ConvexHull.class
	@rm -f src/Main.class src/ConvexHullParallel.class src/ConvexHull.class

out/ConvexHullParallel.jar: out/parcs.jar src/ConvexHullParallel.java src/ConvexHull.java
	@javac -cp out/parcs.jar src/ConvexHullParallel.java src/ConvexHull.java
	@jar cf out/ConvexHullParallel.jar -C src ConvexHullParallel.class -C src ConvexHull.class
	@rm -f src/ConvexHullParallel.class src/ConvexHull.class

build: out/Main.jar out/ConvexHullParallel.jar

run: out/Main.jar out/ConvexHullParallel.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main