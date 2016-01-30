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

package de.fau.cs.inf2.cthree.data;

public final class CodeChange {

  public final String id;

  public final String repository;
  public final String fileName;

  public final String commitBeforeChange;
  public final String commitAfterChange;

  public final int methodNumberBeforeChange;
  public final int methodNumberAfterChange;

  public final String signatureBeforeChange;
  public final String signatureAfterChange;

  public final String[] diff;

  public CodeChange(
            final String id,
            final String repository,
            final String fileName,
            final String commitBeforeChange,
            final String commitAfterChange,
            final int methodNumberBeforeChange,
            final int methodNumberAfterChange,
            final String signatureBeforeChange,
            final String signatureAfterChange,
            final String[] diff) {
    this.id = id;
    this.repository = repository;
    this.fileName = fileName;
    this.commitBeforeChange = commitBeforeChange;
    this.commitAfterChange = commitAfterChange;
    this.methodNumberBeforeChange = methodNumberBeforeChange;
    this.methodNumberAfterChange = methodNumberAfterChange;
    this.signatureBeforeChange = signatureBeforeChange;
    this.signatureAfterChange = signatureAfterChange;
    this.diff = diff;
  }

}
