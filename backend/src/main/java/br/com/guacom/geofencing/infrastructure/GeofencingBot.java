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



package br.com.guacom.geofencing.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

//Classe responsável por receber o id do chat ao qual a mensagem será enviada, bem como o texto e enviar a mensagem
//por meio de uma requisição HTTP(POST)

@Data
@Builder
@JsonIgnoreProperties(value = {"TELEGRAM_RESOURCE", "chatId", "ok"}, ignoreUnknown = true)
public class GeofencingBot implements Serializable {
    private static String TELEGRAM_RESOURCE = "https://api.telegram.org/bot1007465108:AAGZl-yNO5bCP6moyhoOHP0DoymELQpg5Ig/sendMessage?chat_id={0}&text={1}";
    private String chatId;

    @JsonInclude
    private String text;

    public GeofencingBot(String chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public GeofencingBot() {
    }

    public boolean send(String semovente) {
        try {
            format(semovente);
            HttpClient.POST(TELEGRAM_RESOURCE);
            return true;
        } catch (RuntimeException rte) {
            return false;
        }
    }

    private void format(String semovente) {
        this.text = this.text.replace("{0}", semovente);
        GeofencingBot.TELEGRAM_RESOURCE = GeofencingBot.TELEGRAM_RESOURCE.replace("{0}", chatId).replace("{1}", text);
    }
}
//"1007465108:AAGZl-yNO5bCP6moyhoOHP0DoymELQpg5Ig";