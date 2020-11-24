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

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Point implements Comparable<Point> {
    private double longitude;
    private double latitude;

    @Override
    public int compareTo(@NotNull Point o) {
        return Double.compare(o.getLongitude(), this.getLongitude());
    }
}

@Builder
@AllArgsConstructor
@NoArgsConstructor
class Line {
    @Getter
    private Point from;

    @Getter
    private Point to;
}