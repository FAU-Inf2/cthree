# cthree

This repository contains the evaluation results of the research paper "P. Kreutzer, G.  Dotzler, M. Ring, B. M. Eskofier, M. Philippsen: Automatic Clustering of Code Changes", as well as a simple Java tool to read in the data.

It also contains the tree differencing evaluation of the PhD thesis "G. Dotzler: Learning Code Transformations from Repositories, Friedrich-Alexander University Erlangen-Nürnberg, 2018".	https://doi.org/10.25593/978-3-96147-142-3

Archived copy:

[![DOI](https://zenodo.org/badge/50670376.svg)](https://zenodo.org/badge/latestdoi/50670376)

## Data Format

For each analyzed repository (see below), there is a compressed JSON file that contains the detected clusters of code changes. Use [lrzip](https://github.com/ckolivas/lrzip) to uncompress the archives. Warning: the uncompressed archives are huge!

Each code change is described by its relevant properties, including its file name, the method signatures of the changed method, and the human-readable diff. Each cluster contains a field `detectedBy` that describes which of the four combinations (see research paper) detected this group.

This repository also contains a sample Java application that shows how to read and process the JSON files. The code relies on the [Jackson](https://github.com/FasterXML/jackson) library to parse the JSON files (see `build.gradle`).


## Structure

This repository is structured as follows:

- `data/ant.json.lrz`:
  - data set for the [Ant repository](https://git-wip-us.apache.org/repos/asf/ant.git)
  - Ant is licensed under the Apache License v2, see `LICENSE.apache`
  - uncompressed size: 95M
- `data/checkstyle.json.lrz`:
  - data set for the [Checkstyle repository](https://github.com/checkstyle/checkstyle.git)
  - Checkstyle is licensed under the GNU LGPL v2.1, see `LICENSE.lgpl`
  - uncompressed size: 53M
- `data/cobertura.json.lrz`:
  - data set for the [Cobertura repository](https://github.com/cobertura/cobertura.git)
  - Cobertura is licensed under the GNU GPL v2, see `LICENSE.gpl`
  - uncompressed size: 9M 
- `data/drjava.json.lrz`:
  - data set for the [DrJava repository](http://git.code.sf.net/p/drjava/git_repo)
  - DrJava is licensed under the BSD license, see `LICENSE.drjava.bsd`
  - uncompressed size: 150M
- `data/eclipsejdt.json.lrz`:
  - data set for the [EclipseJDTCore repository](http://git.eclipse.org/c/jdt/eclipse.jdt.core.git)
  - Eclipse JDT Core is licensed under the Eclipse Public License v1.0, see `LICENSE.epl`
  - uncompressed size: 399M
- `data/eclipseswt.json.lrz`:
  - data set for the [EclipseSWT repository](http://git.eclipse.org/c/platform/eclipse.platform.swt.git)
  - Eclipse SWT is licensed under the Eclipse Public License v1.0, see `LICENSE.epl`
  - uncompressed size: 271M
- `data/fitlibrary.json.lrz`:
  - data set for the [Fitlibrary repository](http://sourceforge.net/projects/fitlibrary/)
  - Fitlibrary is licensed under the GNU GPL v2, see `LICENSE.gpl`
  - uncompressed size: 12M
- `data/jgrapht.json.lrz`:
  - data set for the [JGraphT repository](https://github.com/jgrapht/jgrapht)
  - JGraphT is licensed under the GNU LGPL v2.1, see `LICENSE.lgpl`
  - uncompressed size: 9M
- `data/junit.json.lrz`:
  - data set for the [JUnit repository](https://github.com/junit-team/junit.git)
  - JUnit is licensed under the Eclipse Public License v1.0, see `LICENSE.epl`
  - uncompressed size: 13M
  - **note**: there is also the uncompressed version in the repository (`data/junit.json`)
- `src/java/main`:
  - sample Java application to read the JSON files (see above)


## Building and Running the Example Application

To build the example application type

```
./gradlew build
```

This creates the application jar `build/libs/cthree-all-1.0.jar`. One way to run it (at least on a Unix system), is gradle:

```
./gradlew executeLaseEvaluation -PappArgs="['JSON_FILE_PATH']" 
```
To print the phd thesis evaluation use:
```
./gradlew executeLaseEvaluation -PappArgs="['PATH_TO_ECLIPSE_JDT_JSON','PATH_TO_ECLIPSE_SWT_JSON' ]" 
```

## License

- the source code of the Java interface in `src/java/main` is licensed under the MIT license (see `LICENSE.mit`)
- `data` is licensed under the CC0 license (see `LICENSE.cc0`).
  - the code snippets cotained in each data file fall under the license of the respective project (see above)
