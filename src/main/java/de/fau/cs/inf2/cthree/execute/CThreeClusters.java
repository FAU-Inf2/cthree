/*
 * Copyright (c) 2015 Programming Systems Group, CS Department, FAU
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */

package de.fau.cs.inf2.cthree.execute;

import de.fau.cs.inf2.cthree.common.Utils;
import de.fau.cs.inf2.cthree.data.Cluster;
import de.fau.cs.inf2.cthree.data.DataSet;
import de.fau.cs.inf2.cthree.data.CodeChange;
import de.fau.cs.inf2.cthree.data.Algorithm;
import de.fau.cs.inf2.cthree.io.DataReader;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

public final class CThreeClusters {

  private CThreeClusters() {
  }

  private static final void printUsage() {
    System.err.println("[i] USAGE: ./cthree <file name>");
  }

  private static final String formatMembers(final CodeChange[] members) {
    final StringBuilder builder = new StringBuilder();

    builder.append("[");

    boolean first = true;
    for (final CodeChange member : members) {
      if (!first) {
        builder.append(", ");
      }
      first = false;

      builder.append(member.id);
    }

    builder.append("]");

    return builder.toString();
  }

  private static final void printCluster(final Cluster cluster) {
    System.out.format("CLUSTER: %s\n", cluster.id);
    System.out.format("  * detected by: %s\n", Algorithm.format(cluster.detectedBy));
    System.out.format("  * members: %s\n", formatMembers(cluster.members));
  }

  private static final void printDataSet(final DataSet dataSet) {
    for (final Cluster cluster : dataSet) {
      printCluster(cluster);
    }
  }

  private static final Map<Algorithm, Integer> getNumberOfClusters(final DataSet dataSet) {
    final Map<Algorithm, Integer> counts = new HashMap<Algorithm, Integer>();

    for (final Algorithm algorithm : Algorithm.values()) {
      counts.put(algorithm, 0);
    }

    for (final Cluster cluster: dataSet) {
      for (final Algorithm detectedBy : cluster.detectedBy) {
        counts.put(detectedBy, counts.get(detectedBy) + 1);
      }
    }

    return counts;
  }

  private static final Map<Algorithm, Integer> getNumberOfCodeChanges(final DataSet dataSet) {
    final Map<Algorithm, Integer> counts = new HashMap<Algorithm, Integer>();

    for (final Algorithm algorithm : Algorithm.values()) {
      counts.put(algorithm, 0);
    }

    for (final Cluster cluster: dataSet) {
      final int count = cluster.members.length;

      for (final Algorithm detectedBy : cluster.detectedBy) {
        counts.put(detectedBy, counts.get(detectedBy) + count);
      }
    }

    return counts;
  }

  public static final void main(final String[] args) {
    if (args.length != 1 || args[0].equals("-h")) {
      printUsage();
      System.exit(1);
    }

    final File inputFile;
    {
      final String inputFileName = args[0];
      inputFile = new File(inputFileName);

      Utils.ensureFileExists(inputFile);
    }

    final DataSet dataSet;
    {
      dataSet = DataReader.getInstance().readFile(inputFile);

      if (dataSet == null) {
        Utils.die(String.format("unable to read input file '%s'", inputFile.getPath()));
      }
    }

    final Map<Algorithm, Integer> countsClusters = getNumberOfClusters(dataSet);
    final Map<Algorithm, Integer> countsCodeChanges = getNumberOfCodeChanges(dataSet);

    System.out.println("number of clusters:");
    for (final Algorithm algorithm : Algorithm.values()) {
      System.out.format("\t%s: %d\n", algorithm.toString(), countsClusters.get(algorithm));
    }

    System.out.println("number of code changes:");
    for (final Algorithm algorithm : Algorithm.values()) {
      System.out.format("\t%s: %d\n", algorithm.toString(), countsCodeChanges.get(algorithm));
    }

    //printDataSet(dataSet);
  }

}
