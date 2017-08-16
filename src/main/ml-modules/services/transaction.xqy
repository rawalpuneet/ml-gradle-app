xquery version "1.0-ml";

module namespace ext = "http://marklogic.com/rest-api/resource/transaction";


import module namespace t = "http://marklogic.com/rest-api/transform/transaction-lib" at "/ext/sample-project/lib/transaction-lib.xqy";

declare namespace roxy = "http://marklogic.com/roxy";
declare namespace rapi = "http://marklogic.com/rest-api";


(:
 : To add parameters to the functions, specify them in the params annotations.
 : Example
 :   declare %roxy:params("uri=xs:string", "priority=xs:int") ext:get(...)
 : This means that the get function will take two parameters, a string and an int.
 :)



(:
 :)

declare %rapi:transaction-mode("update")
function ext:post(
        $context as map:map,
        $params  as map:map,
        $input   as document-node()*
) as document-node()?
{

    let $uri := "/data/doc" || $input/node()/@id || ".xml"
    let $doc := t:router($input/node())

    return (xdmp:document-insert($uri, $doc, xdmp:default-permissions(), "sample-import"), document{"Data Ingested"} )

};

