<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SDK Iframe</title>
  </head>
  <body>
    <h1>PayNow SDK</h1>

    <div class="container" style="max-width: 400px; margin: auto;">
      <div id="root"></div>
      <button id="btn">Checkout</button>
      <button id="localEnBtn">set en</button>
      <button id="localZhBtn">set zhTw</button>
    </div>
    <script src="https://paynow-public.s3.ap-northeast-1.amazonaws.com/sdk/v1/index.js"></script>
    <script>
      PayNow.createPayment({
        clientKey: "[[${clientKey}]]",
        secretKey: "[[${paymentIntentSecret}]]",
        env: 'sandbox',
        appearance: {
          // theme: 'dark',
          variables: {
            fontFamily: 'monospace',
            colorPrimary: '#241B60',
            colorBorder: '#150D2E57',
            colorPlaceholder: '#470727',
            borderRadius: '20px',
            colorDefault: '#00C57A',
            colorDanger: 'pink',
          }
        },
        locale: 'zh_tw'
      })

      PayNow.mount('#root')
      PayNow.on('mounted', () => {
        console.log('mounted')
      })
      PayNow.on('update', (state) => {
        console.log('update field', state)
      })
      PayNow.on('paymentMethodSelected', (paymentMethod) => {
        console.log('paymentMethodSelected', paymentMethod)
      })
      PayNow.on('localeUpdated', (locale) => {
        console.log('localeUpdated', locale)
      })

      // on #btn click
      btn.addEventListener('click', evt => {
        PayNow.checkout().then(res => {
          console.log('✅ checkout', res)
        })
      })

      // on #localBtn click
      localEnBtn.addEventListener('click', evt => {
        PayNow.updateLocale('en')
      })
      localZhBtn.addEventListener('click', evt => {
        PayNow.updateLocale('zh_tw')
      })
    </script>
  </body>
</html>
 