"use strict";

async function make_request() {
    const response = await fetch("http://localhost:8080/product/all", {method: "GET"});
    const json = await response.json();
    console.log(json)
}

make_request()
