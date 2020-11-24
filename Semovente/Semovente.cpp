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
#include "Semovente.h"
#include <Arduino.h>
#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>

bool iniciaLoRa(){

	LoRa.setPins(SS, RESET, DIO0);
	LoRa.setTxPower(DB);

	int retorno = LoRa.begin(FREQUENCY);

	if (retorno == 1) {
		return true;
	}else return false;
}

bool verificaDadosGPS(float latitude, float longitude, unsigned long idade ){

  if ( (latitude != TinyGPS::GPS_INVALID_F_ANGLE) && (longitude != TinyGPS::GPS_INVALID_F_ANGLE) ){
    if(idade != TinyGPS::GPS_INVALID_AGE){
      return true;
    }else return false;
  }else return false;


}

void enviaDados(Pacote pacote){

 while (LoRa.beginPacket() == 0) {
    delay(100);
  }
  
	LoRa.beginPacket();
	LoRa.write((uint8_t*)&pacote, sizeof(pacote));
	LoRa.endPacket(true);
  delay(500);
}

void iniciaSerial(){
  Serial.begin(VELSERIAL);
  delay(1000);
  while (!Serial);
  delay(1000);
}

void iniciaGPS(SoftwareSerial serialGPS){
  serialGPS.begin(VELGPS);
  delay(1000);
  while (!serialGPS);
  delay(1000);
}

void desabilitaWiFi(){
  WiFi.mode(WIFI_OFF);//15mA de economia.
  WiFi.forceSleepBegin();
  delay(10);
}
