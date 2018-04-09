xquery version "1.0-ml";

module namespace t = "http://marklogic.com/rest-api/transform/snippet";
import module namespace search =
  "http://marklogic.com/appservices/search"
  at "/MarkLogic/appservices/search/search.xqy";


declare function t:transform(
   $result as node(),
   $ctsquery as schema-element(cts:query),
   $options as element(search:transform-results)?
) as element(search:snippet) {
    element search:snippet {
        attribute format { "json" },

    }
};
