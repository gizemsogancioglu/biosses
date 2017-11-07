var invocation = new XMLHttpRequest();
//var url = "http://localhost:8080/sses-1.0-SNAPSHOT/services/SSESService";
//var url = "http://localhost:8080/services/SSESService";
var url = "http://localhost:8080/sses/services/SSESService";


function sendStringRequest() {
    var sentence1 = document.getElementById("s1").value;
    var sentence2 = document.getElementById("s2").value;
    var methodType = document.getElementById("method").value;

//        console.log(sentence1);
//        console.log(sentence2);
    console.log(methodType);
    var requestBodyForStringFunction = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
        + "xmlns:ser=\"http://services/\">" + "<soapenv:Header/> <soapenv:Body> <ser:calculateSimilarityScoreForGivenPair> " +
        "<arg0>" + sentence1 + "</arg0> " + "<arg1>" + sentence2 + "</arg1> <arg2>" + methodType + "</arg2> " +
        "</ser:calculateSimilarityScoreForGivenPair> </soapenv:Body> </soapenv:Envelope>";

    return requestBodyForStringFunction;
}

function sendCustomerSentencesToServerSide() {

    var requestBody = sendStringRequest();
    if (invocation) {
        invocation.open("POST", url, true);
        invocation.setRequestHeader("Content-type", "text/xml");
        invocation.send(requestBody);
        console.log("--------------");
        console.log(requestBody);
        console.log("--------------");

        invocation.onreadystatechange = function () {
            console.log(invocation.readyState);
            console.log(invocation.status);
            if (invocation.readyState == 4) {
                if (invocation.status == 200) {
                    outputComingMsg();
                }
            }
        };
    }

    else {
        console.log("requestler uygun değil..")
    }
}


function outputComingMsg() {
    var response = invocation.responseXML.documentElement;
    console.log(response);
    var result = response.getElementsByTagName("return")[0].childNodes[0].nodeValue;

    console.log(result);
    document.getElementById("result").value = result;
}
