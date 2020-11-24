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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Classe de domínio - Responsável por validar se os valores fornecidos estão dentro da Geofencing.

@Builder
@AllArgsConstructor
public class Polygon {
    @Builder.Default
    @Getter
    private List<Point> points = new ArrayList<>();

    public Polygon() {
        if (Objects.isNull(points)) {
            points = new ArrayList<>();
        }
        //Pontos que formam a Geofencing.
        points.add(new Point(-45.42808, -20.46991));
        points.add(new Point(-45.42796, -20.46777));
        points.add(new Point(-45.42578, -20.46784));
        points.add(new Point(-45.42595, -20.47038));
 
    }
    public boolean checkInside(Point coord) {
        int i, j;
        boolean isInside = false;
        //create an array of coordinates from the region boundary list
        Point[] verts = (Point[]) this.getPoints().toArray(new Point[this.getPoints().size()]);
        int sides = verts.length;

        for (i = 0, j = sides - 1; i < sides; j = i++) {
            //verifying if your coordinate is inside your region
            if (
                    (
                            (
                                    (verts[i].getLongitude() <= coord.getLongitude()) && (coord.getLongitude() < verts[j].getLongitude())
                            ) || (
                                    (verts[j].getLongitude() <= coord.getLongitude()) && (coord.getLongitude() < verts[i].getLongitude())
                            )
                    ) &&
                            (coord.getLatitude() < (verts[j].getLatitude() - verts[i].getLatitude()) * (coord.getLongitude() - verts[i].getLongitude()) / (verts[j].getLongitude() - verts[i].getLongitude()) + verts[i].getLatitude())
            ) {
                isInside = !isInside;
            }
        }
        return isInside;
    }
}