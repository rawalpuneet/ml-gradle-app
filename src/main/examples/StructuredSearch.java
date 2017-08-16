package main.examples;

/*
 * Copyright 2012-2017 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

    import java.io.File;
    import java.io.IOException;
    import java.io.InputStream;

    import com.marklogic.client.DatabaseClient;
    import com.marklogic.client.DatabaseClientFactory;
    import com.marklogic.client.DatabaseClientFactory.Authentication;
    import com.marklogic.client.FailedRequestException;
    import com.marklogic.client.ForbiddenUserException;
    import com.marklogic.client.ResourceNotFoundException;
    import com.marklogic.client.ResourceNotResendableException;
    import com.marklogic.client.admin.QueryOptionsManager;
    import com.marklogic.client.document.XMLDocumentManager;
    import com.marklogic.client.io.InputStreamHandle;
    import com.marklogic.client.io.SearchHandle;
    import com.marklogic.client.io.StringHandle;
    import com.marklogic.client.query.*;

/**
 * StructuredSearch illustrates searching for documents and iterating over results
 * with structured criteria referencing a constraint defined by options.
 */
public class StructuredSearch {
  static final private String OPTIONS_NAME = "all";



  public static void main(String[] args)
      throws IOException, ResourceNotFoundException, ForbiddenUserException, FailedRequestException, ResourceNotResendableException
  {
    run();
  }

  public static void run()
      throws IOException, ResourceNotFoundException, ForbiddenUserException, FailedRequestException, ResourceNotResendableException
  {
    System.out.println("example: "+StructuredSearch.class.getName());
    search(Config.host, Config.port, Config.user, Config.password, Config.authType);

  }


  public static void search(String host, int port, String user, String password, Authentication authType)
      throws IOException, ResourceNotFoundException, ForbiddenUserException, FailedRequestException
  {
    // connect the client
    DatabaseClient client = DatabaseClientFactory.newClient(host, port, user, password, authType);

    // create a manager for searching
    QueryManager queryMgr = client.newQueryManager();

    // create a query builder for the query options
    StructuredQueryBuilder qb = new StructuredQueryBuilder(OPTIONS_NAME);

    String rawXMLQuery =
        "<cts:element-value-query xmlns:cts=\"http://marklogic.com/cts\">"+
            "<cts:element>CoverageCd</cts:element>"+
            "<cts:text xml:lang=\"en\">PD</cts:text>"+
            "</cts:element-value-query>";

    StringHandle rh = new StringHandle(rawXMLQuery);
    RawStructuredQueryDefinition qd =
        queryMgr.newRawStructuredQueryDefinition(rh);

    // create a handle for the search results
    SearchHandle resultsHandle = new SearchHandle();

    // run the search
    queryMgr.search(qd, resultsHandle);

    System.out.println("Matched "+resultsHandle.getTotalResults()+
        " documents with structured query\n");

    // iterate over the result documents
    MatchDocumentSummary[] docSummaries = resultsHandle.getMatchResults();
    System.out.println("Listing "+docSummaries.length+" documents:\n");
    for (MatchDocumentSummary docSummary: docSummaries) {
      String uri = docSummary.getUri();
      int score = docSummary.getScore();

      // iterate over the match locations within a result document
      MatchLocation[] locations = docSummary.getMatchLocations();
      System.out.println("Matched "+locations.length+" locations in "+uri+" with "+score+" score:");
      for (MatchLocation location: locations) {

        // iterate over the snippets at a match location
        for (MatchSnippet snippet : location.getSnippets()) {
          boolean isHighlighted = snippet.isHighlighted();

          if (isHighlighted)
            System.out.print("[");
          System.out.print(snippet.getText());
          if (isHighlighted)
            System.out.print("]");
        }
        System.out.println();
      }
    }

    // release the client
    client.release();
  }


}
