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

package de.fau.cs.inf2.cthree.io;

import de.fau.cs.inf2.cthree.data.Cluster;
import de.fau.cs.inf2.cthree.data.DataSet;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public final class DataReader {

  private static DataReader instance = null;

  private final ObjectMapper mapper;

  private DataReader(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public static final DataReader getInstance() {
    if (DataReader.instance == null) {
      final ObjectMapper mapper = DataBind.createMapper();
      DataReader.instance = new DataReader(mapper);
    }

    return DataReader.instance;
  }

  public final DataSet readFile(final File file) {
    try {
      return this.mapper.readValue(file, DataSet.class);
    } catch (final Throwable throwable) {
      System.err.print("[!] ");
      System.err.println(throwable.getMessage());
      return null;
    }
  }

}
