xquery version "1.0-ml";

module namespace transform = "http://marklogic.com/rest-api/transform/transaction";
import module namespace t = "http://marklogic.com/rest-api/transform/transaction-lib" at "/ext/sample-project/lib/transaction-lib.xqy";

declare function transform:transform(
        $content as map:map,
        $context as map:map
) as map:map*
{
    let $doc := map:get($content, "value")
    return if (fn:empty($doc/element()))
                then $content
            else (
                map:put($content, "value",
                    document {
                        t:router($doc/element())
                    }
                ),
                $content
        )
};