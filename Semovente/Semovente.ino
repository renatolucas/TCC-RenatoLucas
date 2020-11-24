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

#include "Semovente.h"
#include <TinyGPS.h>
#include <ESP8266WiFi.h>
#include <user_interface.h>
#include <LoRa.h>

SoftwareSerial serialGPS(RX, TX);
TinyGPS gps;
Pacote pacote;

unsigned long idade;
bool recebido = false;
static unsigned long delayPrint;
char cIn;
byte reason;
int contadorHdop;

void setup() {
  desabilitaWiFi();
  iniciaSerial();
  serialGPS.begin(VELGPS);
  Serial.println("Serial e GPS  iniciados.");
  
  if(iniciaLoRa()){
    Serial.println("LoRa iniciado.");
    //LoRa.dumpRegisters(Serial);
  }else Serial.println("Erro ao iniciar LoRa");

  pacote.id = 6;
  contadorHdop = 0;
    
}

void loop() {

  while(serialGPS.available()){
    cIn = serialGPS.read();
    recebido = (gps.encode(cIn) || recebido);
  }


  if ( (recebido) && ((millis() - delayPrint) > 1000) ){
    delayPrint = millis();

    gps.f_get_position(&pacote.latitude, &pacote.longitude, &idade);
    pacote.precisao = (gps.hdop());

    if ( verificaDadosGPS(pacote.latitude, pacote.longitude, idade)){

      if((pacote.precisao <= 1.5) || (contadorHdop == 3)){
        
        enviaDados(pacote);
        Serial.println("Dados enviados:");
        Serial.println(pacote.id);
        Serial.println(pacote.latitude, 6);
        Serial.println(pacote.longitude, 6);
        Serial.println(pacote.precisao);
        Serial.println("--------------------");

        contadorHdop = 0;
        
        LoRa.end();
        ESP.deepSleep(20 * 1e6);

      }else{
        contadorHdop++;
        Serial.println("Precisão ruim.");
      }

    }else Serial.print("Erro ao enviar dados.");  //if verifica dados

  }//if recebido..

}//loop
