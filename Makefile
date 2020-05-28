all: run

clean:
	rm -f out/Main.jar out/ConvexHullParallel.jar

out/Main.jar: out/parcs.jar src/Main.java src/ConvexHullParallel.java src/ConvexHull.java src/MyPoint.java
	@javac -cp out/parcs.jar src/Main.java src/ConvexHullParallel.java src/ConvexHull.java src/MyPoint.java
	@jar cf out/Main.jar -C src Main.class -C src ConvexHullParallel.class -C src ConvexHull.class -C src MyPoint.class
	@rm -f src/Main.class src/ConvexHullParallel.class src/ConvexHull.class src/MyPoint.class

out/ConvexHullParallel.jar: out/parcs.jar src/ConvexHullParallel.java src/ConvexHull.java src/MyPoint.java
	@javac -cp out/parcs.jar src/ConvexHullParallel.java src/ConvexHull.java src/MyPoint.java
	@jar cf out/ConvexHullParallel.jar -C src ConvexHullParallel.class -C src ConvexHull.class -C src MyPoint.class
	@rm -f src/ConvexHullParallel.class src/ConvexHull.class src/MyPoint.class

build: out/Main.jar out/ConvexHullParallel.jar

run: out/Main.jar out/ConvexHullParallel.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main