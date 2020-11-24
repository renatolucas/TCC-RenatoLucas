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
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Geofencing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @Range(min = -90, max = 90, message = "O campo Latitude está fora do Geofencing.")
    @NotNull(message = "O campo Latitude é obrigatório.")
    @Column(nullable = false)
    private Double latitude;

    @Range(min = -180, max = 180, message = "O campo Longitude está fora do Geofencing.")
    @NotNull(message = "O campo Longitude é obrigatório.")
    @Column(nullable = false)
    private Double longitude;

    @NotNull(message = "O campo Semovente é obrigatório.")
    @Column(nullable = false)
    public Long idSemovente;

    @NotNull(message = "O campo precisão é obrigatório.")
    @Column(nullable = false)
    private Long precisao;

    @NotNull(message = "O campo rssi é obrigatório.")
    @Column(nullable = false)
    private Integer rssi;

    @NotNull(message = "O campo snr é obrigatório.")
    @Column(nullable = false)
    private Float snr;

    @NotNull(message = "O campo freqErr é obrigatório.")
    @Column(nullable = false)
    private Long freqErr;
}