using Microsoft.AspNetCore.Mvc;

namespace PaymentDemo.Controllers
{
    public class PaymentController : Controller
    {
        const string baseUrl = "https://sandbox.paynow.com.tw";
        const string iframePrivateKey = "e671f2fbeeb44d11916bb65882136ed6";
        const string iframePublicKey = "05cbeebb15cd471f92fac835df0924cb";
        const string functionalPrivateKey = "2e8175dd16504019ba0cc15690e74fca";
        const string functionalPublicKey = "1d0c7a7a99924d2e90d8fe896e6c6f31";

        private readonly ILogger<PaymentController> _logger;

        public PaymentController(ILogger<PaymentController> logger)
        {
            _logger = logger;
        }

        [HttpPost]
        public IActionResult Init([FromBody] string privateKey)
        {

            var client = new HttpClient();

            client.BaseAddress = new Uri($"{baseUrl}/api/v1/payment-intent");

            return View();
        }
    }
}