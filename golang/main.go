package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
	"path"
	"text/template"
)

const (
	baseUrl          = "https://sandbox.paynow.com.tw/"
	iframePrivateKey = "e671f2fbeeb44d11916bb65882136ed6"
	iframePublicKey  = "05cbeebb15cd471f92fac835df0924cb"

	functionalPrivateKey = "2e8175dd16504019ba0cc15690e74fca"
	functionalPublicKey  = "1d0c7a7a99924d2e90d8fe896e6c6f31"
)

func initPaymentIntent(privateKey string) (Rep, error) {
	client := &http.Client{}
	var reading Rep

	reqBody := `{"amount": "100","currency": "TWD"}`

	req, err := http.NewRequest("POST", baseUrl+"api/v1/payment-intents", bytes.NewReader([]byte(reqBody)))

	if err != nil {
		log.Println(err)
		return reading, err
	}

	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Authorization", "Bearer "+privateKey)

	resp, err := client.Do(req)
	if err != nil {
		return reading, err
	}

	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)

	if err != nil {
		return reading, err
	}

	err = json.Unmarshal(body, &reading)

	if err != nil {
		return reading, err
	}

	return reading, nil
}

// iframe 嵌入
func iframe(w http.ResponseWriter, r *http.Request) {

	reading, err := initPaymentIntent(iframePrivateKey)

	if err != nil {
		log.Println(err)
		return
	}

	t := template.Must(template.ParseFiles(path.Join("templates", "iframe.html")))
	t.Execute(w, struct {
		ClientKey           string
		PaymentIntentSecret string
	}{
		iframePublicKey,
		reading.Result.Secret,
	})
}

// functional 功能
func functional(w http.ResponseWriter, r *http.Request) {

	reading, err := initPaymentIntent(functionalPrivateKey)

	if err != nil {
		log.Println(err)
		return
	}

	t := template.Must(template.ParseFiles(path.Join("templates", "functional.html")))
	t.Execute(w, struct {
		ClientKey           string
		PaymentIntentSecret string
	}{
		functionalPublicKey,
		reading.Result.Secret,
	})
}

func main() {
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		w.Write([]byte(fmt.Sprintf(`hello world`)))
	})

	http.HandleFunc("/iframe", iframe)
	http.HandleFunc("/functional", functional)

	log.Println("Server start at 8080")

	if err := http.ListenAndServe(":8080", nil); err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}

type Rep struct {
	Status    int64       `json:"status"`
	Type      string      `json:"type"`
	Message   string      `json:"message"`
	Result    Result      `json:"result"`
	RequestID string      `json:"requestId"`
	Paginate  interface{} `json:"paginate"`
}

type Result struct {
	ID                      string      `json:"id"`
	Secret                  string      `json:"secret"`
	Module                  string      `json:"module"`
	AllowPaymentMethodTypes []string    `json:"allowPaymentMethodTypes"`
	Amount                  int64       `json:"amount"`
	Currency                string      `json:"currency"`
	Description             interface{} `json:"description"`
	Status                  string      `json:"status"`
	CreatedAt               string      `json:"createdAt"`
	Payment                 interface{} `json:"payment"`
	Meta                    Meta        `json:"meta"`
}

type Meta struct {
	AllowInstallments []AllowInstallment `json:"allowInstallments"`
}

type AllowInstallment struct {
	Installments int64 `json:"installments"`
	Rate         int64 `json:"rate"`
	Extra        bool  `json:"extra"`
	Enabled      bool  `json:"enabled"`
}
