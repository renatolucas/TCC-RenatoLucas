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


package br.com.guacom.geofencing;

import br.com.guacom.geofencing.infrastructure.GeofencingBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeofencingBotService {

    //Registrando um bean para que o Spring boot possa gerenciar a injeção de dependência transformando essa instância em um Singleton.
    @Bean("geofencingBot")
    public GeofencingBot geofencingBot() {
        return new GeofencingBot("phone_here", "O semovente {0} está fora da geofencing.");
    }
    
}
