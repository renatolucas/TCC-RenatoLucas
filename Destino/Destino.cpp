/**********************************************************************
This file is part of TCC - Renato Lucas Silva.

My work is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

TCC - Renato Lucas Silva is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <https://www.gnu.org/licenses/>
***********************************************************************/

#include <LoRa.h>
#include <TinyGPS.h>
#include "Destino.h"
#include <Arduino.h>
#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>

bool iniciaLoRa(){

	LoRa.setPins(SS, RESET, DIO0);
	LoRa.setTxPower(DB);

	int retorno = LoRa.begin(FREQUENCY);

	if (retorno == 1) {
		return true;
	}else return false;
}

Pacote retornaDados(){
  
  uint8_t *buff;
  Pacote pacote;
  
  buff = (uint8_t *)malloc(sizeof(Pacote));
  
  while(LoRa.available()){
      LoRa.readBytes((uint8_t *)&buff, sizeof(Pacote));
  }
  
  memcpy(&pacote, &buff, sizeof(Pacote));
  return pacote;
  
}

void iniciaSerial(){
  
  Serial.begin(VELSERIAL);
  delay(1000);
  while (!Serial);
  delay(1000);
}

void conectaWiFi(){
  
  WiFi.begin(LOGIN, PASSWD);

  while (WiFi.status() != WL_CONNECTED) {  //Wait for the WiFI connection completion
    delay(500);
  }

}

void enviaRequisicaoHTTP(Pacote pacote, int rssi, float snr, unsigned long freqErr){
  
  HTTPClient http;

  if(http.begin(BACKEND)){
      http.addHeader("Content-Type", "application/json");

      const size_t capacity = JSON_OBJECT_SIZE(7) + 70;
      DynamicJsonDocument doc(capacity);

      doc["latitude"] = String(pacote.latitude,6);
      doc["longitude"] = String(pacote.longitude, 6);
      doc["precisao"] = pacote.precisao;
      doc["idSemovente"] = pacote.id;
      doc["rssi"] = rssi;
      doc["snr"] = snr;
      doc["freqErr"] = freqErr;

      serializeJson(doc, Serial);
      int httpCode = http.POST(doc.as<String>());
      String payload = http.getString();
      
      Serial.println("------------------------------------------------");
      Serial.println(httpCode);
      
      // httpCode will be negative on error
      if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
      Serial.printf("[HTTP] POST... code: %d\n", httpCode);

        // file found at server
        if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
          String payload = http.getString();
          Serial.println(payload);
        }
      } else {
        Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }

       http.end();
  }else{
    Serial.println("Erro ao enviar dados");
  }

    delay(5000);
}
