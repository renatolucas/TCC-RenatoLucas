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


package br.com.guacom.geofencing.controller;

import br.com.guacom.geofencing.Geofencing;
import br.com.guacom.geofencing.GeofencingRepository;
import br.com.guacom.geofencing.Point;
import br.com.guacom.geofencing.Polygon;
import br.com.guacom.geofencing.infrastructure.GeofencingBot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//import javax.transaction.Transactional;

//Endpoint que receberá a requisição POST para efetuar o cadastro da Geofencing e verificar se os valores fornecidos estão dentro da mesma.

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/geofencing")
public class GeofencingController {
    private final GeofencingRepository geofencingRepository;

    @Resource(name = "geofencingBot")
    private GeofencingBot geofencingBot;
    private static Polygon polygon;

    static {
        polygon = new Polygon();
    }

    @PostMapping
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> index(@RequestBody @Valid Geofencing geofencing, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        geofencing = geofencingRepository.save(geofencing);

        if (!polygon.checkInside(new Point(geofencing.getLongitude(), geofencing.getLatitude()))) {
            geofencingBot.send(String.valueOf(geofencing.idSemovente));
        }

        URI location = uriComponentsBuilder.path("/api/geofencing/{idGeofencing}")
                .buildAndExpand(geofencing.getId())
                .toUri();
        return ResponseEntity.created(location).body(geofencing);
    }
}