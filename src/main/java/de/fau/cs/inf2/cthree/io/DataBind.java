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

import de.fau.cs.inf2.cthree.data.DataSet;
import de.fau.cs.inf2.cthree.data.Cluster;
import de.fau.cs.inf2.cthree.data.CodeChange;
import de.fau.cs.inf2.cthree.data.Algorithm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;

public final class DataBind {

  @JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class")
  private static abstract class MixInDataSet {
    @JsonProperty("clusters")
    private List<Cluster> clusters;

    public MixInDataSet(
            @JsonProperty("clusters")
              final List<Cluster> clusters) {
    }
  }

  @JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class")
  private static abstract class MixInCluster {
    @JsonProperty("id")
    private String id;

    @JsonProperty("detectedBy")
    private List<Algorithm> detectedBy;

    @JsonProperty("members")
    private CodeChange[] members;

    public MixInCluster(
            @JsonProperty("id")
              final String id,
            @JsonProperty("detectedBy")
              final List<Algorithm> detectedBy,
            @JsonProperty("members")
              final CodeChange[] members) {
    }
  }

  @JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class")
  private static abstract class MixInCodeChange {
    @JsonProperty("id")
    private String id;

    @JsonProperty("repository")
    private String repository;
    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("commitBeforeChange")
    private String commitBeforeChange;
    @JsonProperty("commitAfterChange")
    private String commitAfterChange;

    @JsonProperty("methodNumberBeforeChange")
    private int methodNumberBeforeChange;
    @JsonProperty("methodNumberAfterChange")
    private int methodNumberAfterChange;

    @JsonProperty("signatureBeforeChange")
    private String signatureBeforeChange;
    @JsonProperty("signatureAfterChange")
    private String signatureAfterChange;

    @JsonProperty("diff")
    private String[] diff;

    public MixInCodeChange(
            @JsonProperty("id")
              final String id,
            @JsonProperty("repository")
              final String repository,
            @JsonProperty("fileName")
              final String fileName,
            @JsonProperty("commitBeforeChange")
              final String commitBeforeChange,
            @JsonProperty("commitAfterChange")
              final String commitAfterChange,
            @JsonProperty("methodNumberBeforeChange")
              final int methodNumberBeforeChange,
            @JsonProperty("methodNumberAfterChange")
              final int methodNumberAfterChange,
            @JsonProperty("signatureBeforeChange")
              final String signatureBeforeChange,
            @JsonProperty("signatureAfterChange")
              final String signatureAfterChange,
            @JsonProperty("diff")
              final String[] diff) {
    }
  }

  private DataBind() {
  }

  public static final ObjectMapper createMapper() {
    final ObjectMapper mapper = new ObjectMapper();

    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.setVisibilityChecker(mapper.getVisibilityChecker().with(JsonAutoDetect.Visibility.NONE));
    mapper.setSerializationInclusion(Include.NON_NULL);

    mapper.addMixIn(DataSet.class, MixInDataSet.class);
    mapper.addMixIn(Cluster.class, MixInCluster.class);
    mapper.addMixIn(CodeChange.class, MixInCodeChange.class);

    return mapper;
  }

}
