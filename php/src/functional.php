<?php
require_once('./base.php');

$response = init(functionalPrivateKey);
?>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SDK Functional</title>
  </head>
  <body>
    <h1>PayNow SDK</h1>

    <div class="container" style="max-width: 400px; margin: auto;">
      <div id="root"></div>
      <button id="btn">Checkout</button>
    </div>
    <script src="https://paynow-public.s3.ap-northeast-1.amazonaws.com/sdk/v1/index.js"></script>
    <script>
      PayNow.createPayment({
        clientKey: "<?php echo functionalPublicKey ?>",
        secretKey: "<?php echo $response["result"]["secret"] ?>",
        env: 'sandbox',
        locale: 'zh_tw'
      })

      PayNow.mount('#root')

      // on #btn click
      btn.addEventListener('click', evt => {
        PayNow.checkout({
            "paymentMethodType": "CreditCard",
            "paymentMethodData": {
                "card": {
                    "cardNumber": "4242424242424242",
                    "expirationMonth": "02",
                    "expirationYear": "24",
                    "csc": "424"
                },
                "billTo": {
                    "firstName": "eric",
                    "lastName": "liao",
                    "email": "service@paynow.com.tw",
                    "phoneCode": "886",
                    "phoneNumber": "0912123123",
                    "country": "TW",
                    "address1": ""
                }
            }
        }).then(res => {
          console.log('✅ checkout', res)
        })
      })
    </script>
  </body>
</html>
 