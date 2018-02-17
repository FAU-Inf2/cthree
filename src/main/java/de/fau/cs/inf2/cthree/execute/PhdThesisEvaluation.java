/*
 * Copyright (c) 2015 Programming Systems Group, CS Department, FAU
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package de.fau.cs.inf2.cthree.execute;

import de.fau.cs.inf2.cthree.common.Utils;
import de.fau.cs.inf2.cthree.data.Cluster;
import de.fau.cs.inf2.cthree.data.DataSet;
import de.fau.cs.inf2.cthree.data.CodeChange;
import de.fau.cs.inf2.cthree.data.Algorithm;
import de.fau.cs.inf2.cthree.io.DataReader;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashSet;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PhdThesisEvaluation {

  private static String[][][] groups = {
      { { "77644" },
          { "2219be33a5e53110d26153b9b761bd7a", "438070cdfb4af35597a933152c446e4e",
              "438070cdfb4af35597a933152c446e4e", "34bdb6c29264026f056d746e4b513b9a" } },
      { { "82429" },
          { "6a0f1c695deb39b2bcec99a2ade86d73", "60fbb4d926ba94565f7e1e8b9a24ba27",
              "aecc2344a786c1771ddbb7a9ef9ef667", "975d644a692378a3469a9d5db5c4341d",
              "efedd176357ab18507c882a7b92b9551", "8a605201cefafc8f97c63ab0eb8c7020",
              "deda425938a202d1691dfc2661f9b00c", "8b7edf6bec1f8971949eec0ce9f13346",
              "a58b82d4fd42914a1ea773902a4c8801", "952c1a1303a96c20a49e162a53101d3c",
              "8e04cccdb227cf7b8a142afb94e9bfe2", "43c48c3ccac8484345aa37c37d8ee2da",
              "23c40d354e8ee0bba244b05440982fe9", "6670177973121cc15529d1174fd6c29f",
              "54b4132bfcb1f628f5734c005678bfd3", "e2cb7468f096ba0a730e7cb5bd3db98b" } },
      { { "114007" },
          { "8881ed01156d220a6231d68fa02dd324", "3d4c2e11159fb027786dd40d52e0e259",
              "8b9d0f0bc1407a5805780ddc7a36624c", "a9e871b4750f719d769d864a81e24d39" } },
      { { "139329_1" },
          { "b1ed75a2f77bb03a8805ce07268cc8b5", "d8393f7d77e8e39197011a14efa8e325",
              "d88ce30b3961d85fa632fd87a8c7a112", "435c6c2e8b18e8e12c86d4241d393f67",
              "f5ae357ef75aa8c1996626a32399df57", "ca56b2a6a7bb5f37734272891112f0bb" } },
      { { "142947_1" },
          { "dfc2eee3d6d48dfb38c80bf8620b8186", "798e99004c4ad01e07d724f432241247",
              "7eb53af82f64e58ba35ffb2a8add70c2", "37c24460183ba36c56c2fbe01b9923be",
              "363021511578ea5cea2788580e0cdd99", "ecc76b597fe211fa399f77ce2bbe3af7",
              "988d76606d463c1fa83b1c5ab0665e5c", "a87bd6b44e911a0cea2c18434386e5a9",
              "42cf0dc1b09d8dcb50e47d27ba6d4e5d", "a25d568fa25c71b48b71751799deb3a8",
              "01237c42a6dc00ec1988b6e4df08bc73", "01b5052a73c82f892dafc4981a6998fd" } },
      { { "91937" },
          { "4d17d67894099caffe78f6bebdcf9240", "a26d756e4fdba750a38ac1d9fb95d44f",
              "c27fe3a4ec65787980ae9f0766c84151" } },
      { { "103863" },
          { "aa605ad23dee4307b62423c9392e1cea", "16be6c3505d667bcbfbafa9a4baaec52",
              "c24cd28b084a250eab29d0fbd70039ac", "9d71014d5289df96d7ac024a1a0a6719",
              "f22296af2bd4bcb7a22f0f7799cc5c9d", "055496c6c92bc85237a9fd1da736581b",
              "141347f08491a97dc787ac9908212362" } },
      { { "129314" },
          { "99022f3b3c0d18c37229af45dd9ed0c4", "f88985d2eb7e7ecf132d3742bd511544",
              "37939f3f0c955e95b5854843ee114098", "98c36e3ada6a00150d74be2226fa7a67" } },
      { { "134091" },
          { "7d61cd41afe733881c46a0d2668c87b0", "cef0db73a399b1914ad62667dddc2be0",
              "df6018a928068c3cc9e7152adfc9f89a", "da3b2c35cd9351da521cad4d1d3f4d36" } },
      { { "139329_2" },
          { "2af9e2e868e257c15289f6176b715b56", "edebc48987254b70c151cb9faad5ccad",
              "4b6aeb1b0fe44e4b3dacc78c097c968f" } },
      { { "139329_3" }, { "2894064c7d97283212e104931b4af559", "f14ec2e4b59f07159d19f5016a9d474c",
          "225143ad6762326fbfdb0fc8d70a063b" } },

      { { "142947_2" },
          { "6ada29d77953ab4812f42972f3cb8af5", "0e790203c3c4dd75e69b9f0140eb3c05",
              "cb73d23c6822e7cb411af1af51c7fab3", "a205165781010dc9bdd973abaa0ee600",
              "813014ad09778562850c6e495b64c153", "00c495c28774d613c7b07b2baba8c00b",
              "bb683ee54158ac5345210c8cc816484c", "c2ba51b847902300aec8ecd9848e485d",
              "d39432f3016c04cbdf296feb1b2795fd" } },
      { { "76182" },
          { "f3f57decb8b2ec2821183980c420a424", "52b070c0c3f05139478d6ff0a70bbe6d",
              "9d2949a58f429f843db67380f2f36636", "8b52e104286a62bbc773d970b4462587",
              "f59de5ef7d7afb0d622d5751ad522fc1", "f5287de7fefbc90cf40d848a75c32628" } },
      { { "77194" },
          { "24bbecc32da18833e1217645b45f1485", "86877308add979cb916d7aeebaa60dfb",
              "7487c6222f62527d4afd13672d2a173b" } },
      { { "86079_1" },
          { "787a0317e1a4a252a839c28889a05ec8", "f191d30bdf175ec5a28508c6cbd91d9b",
              "15e6a2a6f4f260586eba8ec0ff1d0ab3" } },
      { { "95409" },
          { "f3f615434e60b7154ca3cf599ef2a4b0", "7fa1ca3ea81f107ce6c80d94370a2a18",
              "4429110aa31b41bb735aff6df96a6f19", "0011a80c539260a5b97625d60fa91fb0",
              "2f9e56a101373119e392439f50b0abc3", "4b482f645115394548384c9c3d51dfa8",
              "6f0d0889d12f51d57bf7c2e99ada68df", "cfc9dce99786b92b66a574148342f22a",
              "9af007235d3489db0fa26edcd81515eb" } },
      { { "97981" },
          { "f17871ff615dfb52a62f74dc95989707", "61cbbbfd8ff41e0faf0cf9e6df87e37f",
              "61213e1d33be6df09651589941b1e8eb", "89c72f5250d7bf261892d4e14a35badc" } },
      { { "76391" },
          { "91ba23341e2b8170b088d3c495ae209a", "d633a5811af620ef9eb35aa8dceaeb1c",
              "113c78ecbf4f7de1e9be9cb14918575a", "64b684aac3fadcdac6f7988443e47f63",
              "97359d1f70cf99912f8b8f5bf83a51e6", "f5c5f1975e599ef503eb3eca53077499" } },
      { { "89785" },
          { "0c1167592a08867eebb1243936ed7056", "abd11a5e3885523702b6c6e494f5e58b",
              "d2856e652d7a3f40203630053aa00678", "519c31b9348e6f4e90c278b665c38a55",
              "dc726bdd228b406be08aea3385b4d297" } },
      { { "79107" },
          { "6163731106df272c8694c28edfa21751", "b789e73145cde035a1547f804b4c440b",
              "7bd8ddb4d7290d6640b7e213591eef0d", "42a0282a3d4875ce3a2d0c455507943c",
              "c477c8769f1bc45dacb08ac61745a889", "88334e1d359304d3db98e229db6a6da7",
              "e3ccf759fa2bbef053b6a20adca3083a", "a46ec5596b8618a6f9138da080feab71",
              "2f8ef6d6d1405000acb93fcb3ea4fe43", "60cd2c2d473c4149b06a0c920ca9e1a6" } },
      { { "86079_2" },
          { "4f0d95964b4788e870d780c728cd8763", "994f370afa6a3db9325028573466b41a",
              "12c5a42fd4061ef7a4451f757e26b4ff" } },
      { { "95116" },
          { "bd0abdddbcf77c4a01d95aeb1645887f", "2df091ca67c7ea781d124da39cc5e370",
              "be020405db6f10ef43a3f6a2e09e6fb5", "2e9fb8cef5eb2f04622e423de9a387cd",
              "5e3b2715b021a2217a88cfec0df91d75" } },
      { { "98198" },
          { "1594c47d340c9ce46b1f3707cdf3d9b7", "d575c0201ab41f98613eb841117799bd",
              "2c712e6c916f981c4c3f91c6fc8318d4", "a09c6f29d1ae78bf6f362270ab8c5006",
              "d8a2ba24403db54b04ecc9f689655250", "49885cfc0e72d45dedfd9565cdedc55f",
              "8a7d1655e9cb5f1b42f5ca371de1769f", "59bfb550d3377219b9363ff6be8faf41",
              "3138e6f9a55300d28a2c49e4243b61f2", "a942be0836709c71c5fad636ffc15bba",
              "0fba188f6128f7bd8f6fd684dc2bf13e", "674189c6a33feeef0ccc77ff4bdabb0e",
              "87130bd669e96b2a276ee2680a9a6796", "250098d8c1699e8fea54ff002bbc8b38",
              "e82be26b5083599db67cf513c561fdf0" } },
      { { "74139" },
          { "f24b186dce86f10b9451707d0feb1a46", "3567f6602b0d028a53b2922aa2be5ca3",
              "b8457a67275bd61d2117848aa29d441c", "af971705d64cf24f9828a655c4e068dd",
              "2ebb37c209caa9f667c241f2d2374936" } }, };

  private PhdThesisEvaluation() {}

  private static final void printUsage() {
    System.err.println("[i] USAGE: ./cthree <eclipse jdt filename> <eclipse swt filename");
  }

  /**
   * Gets the id hash.
   *
   * @param repository the repository
   * @param commitId1 the commit id1
   * @param commitId2 the commit id2
   * @param fileName the file name
   * @param methodNumber1 the method number1
   * @param methodNumber2 the method number2
   * @return the id hash
   */
  public static final String getIdHash(final String repository, final String commitId1,
      final String commitId2, final String fileName, final int methodNumber1,
      final int methodNumber2) {
    final StringBuilder builder = new StringBuilder();
    builder.append(repository);
    builder.append("_");
    builder.append(commitId1);
    builder.append("_");
    builder.append(commitId2);
    builder.append("_");
    builder.append(fileName);
    builder.append("_");
    builder.append(methodNumber1);
    builder.append("_");
    builder.append(methodNumber2);

    final byte[] bytesOfMessage;
    final StringBuffer sb = new StringBuffer();

    try {
      bytesOfMessage = builder.toString().getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("MD5");
      final byte[] thedigest = md.digest(bytesOfMessage);

      for (int i = 0; i < thedigest.length; ++i) {
        sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1, 3));
      }

    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return sb.toString();
  }

  public static final void main(final String[] args) {
    if (args.length != 2 || args[0].equals("-h")) {
      printUsage();
      System.exit(1);
    }
    final File inputJdtFile;
    {
      final String inputFileName = args[0];
      inputJdtFile = new File(inputFileName);

      Utils.ensureFileExists(inputJdtFile);
    }

    final File inputSwtFile;
    {
      final String inputFileName = args[1];
      inputSwtFile = new File(inputFileName);

      Utils.ensureFileExists(inputSwtFile);
    }

    final DataSet dataSetJdt;
    {
      dataSetJdt = DataReader.getInstance().readFile(inputJdtFile);

      if (dataSetJdt == null) {
        Utils.die(String.format("unable to read input file '%s'", inputJdtFile.getPath()));
      }
    }

    final DataSet dataSetSwt;
    {
      dataSetSwt = DataReader.getInstance().readFile(inputSwtFile);

      if (dataSetSwt == null) {
        Utils.die(String.format("unable to read input file '%s'", inputSwtFile.getPath()));
      }
    }
    printEvaluationTable(dataSetJdt, dataSetSwt);
  }

  private static void printEvaluationTable(final DataSet dataSetJdt, final DataSet dataSetSwt) {
    @SuppressWarnings("unchecked")
    HashSet<Cluster>[][] changes = new HashSet[groups.length][4];
    for (int i = 0; i < groups.length; i++) {
      for (int j = 0; j < 4; j++) {
        changes[i][j] = new HashSet<>();
      }
    }
    for (Cluster cluster : dataSetJdt) {
      for (CodeChange change : cluster.members) {
        for (int i = 0; i < 2; i++) {
          for (String id : groups[i][1]) {
            if (change.id.equals(id)) {
              for (Algorithm algorithm : cluster.detectedBy) {
                int algorithmPos = -1;
                switch (algorithm) {
                  case DIFF_HIERARCHICAL:
                    algorithmPos = 0;
                    break;
                  case DIFF_DBSCAN:
                    algorithmPos = 1;
                    break;
                  case AST_HIERARCHICAL:
                    algorithmPos = 2;
                    break;
                  case AST_DBSCAN:
                    algorithmPos = 3;
                    break;
                }
                changes[i][algorithmPos].add(cluster);
              }
            }
          }
        }
      }
    }
    for (Cluster cluster : dataSetSwt) {
      for (CodeChange change : cluster.members) {
        for (int i = 2; i < groups.length; i++) {
          for (String id : groups[i][1]) {
            if (change.id.equals(id)) {
              for (Algorithm algorithm : cluster.detectedBy) {
                int algorithmPos = -1;
                switch (algorithm) {
                  case DIFF_HIERARCHICAL:
                    algorithmPos = 0;
                    break;
                  case DIFF_DBSCAN:
                    algorithmPos = 1;
                    break;
                  case AST_HIERARCHICAL:
                    algorithmPos = 2;
                    break;
                  case AST_DBSCAN:
                    algorithmPos = 3;
                    break;
                }
                changes[i][algorithmPos].add(cluster);
              }
            }
          }
        }
      }
    }
    System.out.print(
        "                       ||          LH                  ||          LD                  ||          TH                  ||          TD\n");
    System.out.print(
        " Id | Bugzilla Id |  m || #Changes | Groups            || #Changes | Groups            || #Changes | Groups"
            + "            || #Changes |       Groups\n");
    System.out.println(
        "-----------------------------------------------------------------------------------"
            + "-----------------------------------------------------------------------------------");
    AtomicInteger[] memberCounts = new AtomicInteger[4];
    AtomicInteger[] clusterCounts = new AtomicInteger[4];
    for (int i = 0; i < 4; i++) {
      memberCounts[i] = new AtomicInteger(0);
      clusterCounts[i] = new AtomicInteger(0);
    }
    for (int i = 0; i < groups.length; i++) {
      HashSet<String> idStrings = new HashSet<>();
      for (int j = 0; j < groups[i][1].length; j++) {
        idStrings.add(groups[i][1][j]);
      }
      System.out.format(" %2d |", i + 1);
      System.out.format(" %11s |", groups[i][0][0]);
      System.out.format(" %2d ||", groups[i][1].length);
      for (int j = 0; j < 4; j++) {
        HashSet<Cluster> clusterSet = changes[i][j];
        printAlgorithmColumns(idStrings, clusterSet, memberCounts[j], clusterCounts[j]);
      }
      System.out.println();
    }
    System.out.println(
        "-----------------------------------------------------------------------------------"
            + "-----------------------------------------------------------------------------------");
    System.out.format(
        "    |             |    || %8d | %6d            || %8d | %6d            || %8d | %6d"
            + "            || %8d |       %6d\n",
        memberCounts[0].get(), clusterCounts[0].get(), memberCounts[1].get(),
        clusterCounts[1].get(), memberCounts[2].get(), clusterCounts[2].get(),
        memberCounts[3].get(), clusterCounts[3].get());
  }

  private static void printAlgorithmColumns(HashSet<String> idStrings, HashSet<Cluster> clusters,
      AtomicInteger memberCount, AtomicInteger clusterCount) {
    int sum = 0;
    String groups = "";
    for (Cluster c : clusters) {
      boolean cleanCluster = true;
      for (CodeChange change : c.members) {
        if (!idStrings.contains(change.id)) {
          cleanCluster = false;
          break;
        }
      }
      if (cleanCluster) {
        sum += c.members.length;
        groups += c.members.length + ";";
        memberCount.addAndGet(c.members.length);
        clusterCount.incrementAndGet();
      }
    }
    System.out.format(" %2d       |", sum);
    System.out.format(" %17s ||", groups);
  }

}
