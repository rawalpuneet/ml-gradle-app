xquery version "1.0-ml";

module namespace t = "http://marklogic.com/rest-api/transform/transaction-lib";

declare function t:router($doc as element())
{
    let $type := $doc//type/text()
    return if($type = "xml") then
        t:parse-xml($doc)
    else if($type = "carriage") then
        t:parse-carriage($doc)
    else $doc
};

declare function t:parse-xml($doc as element())
{
    element envelope {
        element headers {

        },
        element {fn:name($doc)} {
            for $d in $doc/element()
            let $name := fn:name($d)

            return if ($name = "inputMessage") then (
                element {"inputMessage"} {
                    try {
                        attribute result {"xml"},
                        xdmp:unquote($d/node())
                    } catch ($e) {
                        attribute result {"text"},
                        $doc/inputMessage/node()
                    }
                }
            ) else $d
        }
    }
};

declare function t:parse-carriage($doc as element())
{
    element envelope {
        element headers {

            element inputExtract {
                t:carriage($doc/inputMessage/node())
            }
        },

        $doc
    }
};

declare function t:carriage($text as node())
{
    for $d in fn:tokenize($text,"\n")
    let $toks := fn:tokenize($d,":")
    let $name := fn:normalize-space($toks[1])
    let $value := fn:normalize-space($toks[2])
    return if( fn:string-length($name) gt 1) then (
        element {$name} {
            $value
        }
    ) else ()
};
