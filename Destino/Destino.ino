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

#include <SPI.h>
#include <LoRa.h>
#include "Destino.h"

Pacote pacote;
int rssi;
float snr;
unsigned long freqErr;

int packetSize;

httpReturn respostaHttp; 

void setup() {
  
  iniciaSerial();
  Serial.println("Serial inicializado");

  if(!iniciaLoRa()){
    Serial.println("Erro ao iniciar LoRa.");
    while(1);
  }
  
  Serial.println("LoRa iniciado.");

  conectaWiFi();
  Serial.println("Conexão Wi-Fi iniciada.");

}

void loop() {

  packetSize = LoRa.parsePacket();

  if(packetSize){
    
    pacote = retornaDados();

    rssi = LoRa.packetRssi();
    snr = LoRa.packetSnr();
    freqErr = LoRa.packetFrequencyError();
   // Serial.println("------------------------------------");

   Serial.println(pacote.id);
   Serial.println(pacote.latitude, 6);
   Serial.println(pacote.longitude, 6);
   Serial.println(pacote.precisao);

   rssi = LoRa.packetRssi();
   snr = LoRa.packetSnr();
   freqErr = LoRa.packetFrequencyError();

   enviaRequisicaoHTTP(pacote, rssi, snr, freqErr);

  }//if(packetSize)

}//void loop()
