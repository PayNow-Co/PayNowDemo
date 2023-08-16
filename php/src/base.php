<?php
require_once '../vendor/autoload.php';

use GuzzleHttp\Client;

const baseUrl          = "https://sandbox.paynow.com.tw/";
const iframePrivateKey = "e671f2fbeeb44d11916bb65882136ed6";
const iframePublicKey  = "05cbeebb15cd471f92fac835df0924cb";
const functionalPrivateKey = "2e8175dd16504019ba0cc15690e74fca";
const functionalPublicKey  = "1d0c7a7a99924d2e90d8fe896e6c6f31";

function init($privateKey)
{
    $client = new Client([
        'base_uri' => baseUrl,
    ]);

    $response = $client->request('POST', 'api/v1/payment-intents', [
        'headers' => [
            'Authorization' => "Bearer {$privateKey}",
        ],
        'json' => [
            'amount' => '100',
            'currency' => 'TWD',
        ]
    ]);

    $body = $response->getBody()->getContents();

    return json_decode($body, true);
}