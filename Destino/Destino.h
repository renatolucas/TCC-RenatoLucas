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


#ifndef SEMOVENTE_H
#define SEMOVENTE_H

#include <SoftwareSerial.h>
#include <SPI.h>

//Pinos LoRa
#define SS 15
#define RESET 5
#define	DIO0 4

//Frequência LoRa
#define FREQUENCY 915E6

//Decibéis
#define DB 15

//Pinos GPS
#define RX 0
#define TX 2

//velocidade GPS
#define VELGPS 9600

//velocidade Serial
#define VELSERIAL 115200

//Conexão Wi-Fi
#define LOGIN "Casa - Wireless"
#define PASSWD "99558456"

//endereço webService
#define BACKEND "http://10.0.0.105:8080/api/geofencing" 

typedef struct Pacote{
	int id;
	float latitude;
	float longitude;
	unsigned long precisao;	
}Pacote;

typedef struct httpReturn{
  String returnCode;
  String payload;
}httpReturn;

bool iniciaLoRa();
Pacote retornaDados();
void iniciaSerial();
void conectaWiFi();
void enviaRequisicaoHTTP(Pacote pacote, int rssi, float snr, unsigned long freqErr);


#endif
